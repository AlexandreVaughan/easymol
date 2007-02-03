/*
 * Created on 1 nov. 2004
 *
 */
package net.sf.easymol.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import net.sf.easymol.core.Molecule;
import net.sf.easymol.io.xml.NewMoleculetoXML;
import net.sf.easymol.io.xml.xslt.HTMLexporter;
import net.sf.easymol.ui.actions.Arrange2DViewAction;
import net.sf.easymol.ui.actions.CloseMoleculeAction;
import net.sf.easymol.ui.actions.DisplayVSEPRAction;
import net.sf.easymol.ui.actions.HTMLExportAction;
import net.sf.easymol.ui.actions.LoadMoleculeAction;
import net.sf.easymol.ui.actions.NewMoleculeAction;
import net.sf.easymol.ui.actions.SaveMoleculeAction;
import net.sf.easymol.ui.actions.SaveMoleculeAsAction;
import net.sf.easymol.ui.actions.ShowBluetoothAction;
import net.sf.easymol.ui.comp2d.Molecule2DPane;
import net.sf.easymol.ui.comp2d.comm.bluetooth.BluetoothFrame;
import net.sf.easymol.ui.general.IEasyMolPaneHolder;
import net.sf.easymol.ui.general.IconProvider;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;


/**
 * @author avaughan
 *  
 */
public class EasyMolMainWindow extends JFrame implements IEasyMolPaneHolder {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JTabbedPane mainPane = null;

    private JMenuBar menu = null;

    private JToolBar toolBar = null;

    private MoleculeBuilder molBuilder = null;

    public EasyMolMainWindow() {
        super();
        menu = new JMenuBar();
        toolBar = new JToolBar();
        mainPane = new JTabbedPane();
        molBuilder = new MoleculeBuilder();
        createMenu();
        createToolBar();
//        mainPane.putClientProperty(Options.EMBEDDED_TABS_KEY,
//                Boolean.TRUE);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(BorderLayout.CENTER, mainPane);
        this.getContentPane().add(BorderLayout.NORTH, toolBar);
        this.setJMenuBar(menu);
        this.setSize(800, 600);
        this.setTitle("EasyMol");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                while (getCurrentPane() != null)
                    closeCurrentPane();
                System.exit(0);
            }
        });
        createNewMolecule();
    }

    public void addMolecule2DPane(Molecule2DPane pane) {
        mainPane.addTab(pane.getMolecule().getName(), IconProvider
                .getInstance().getIconByName("copy"), pane);
    }

    public void removeMolecule2DPane(Molecule2DPane pane) {
        int index = -1;
        Component[] panes = mainPane.getComponents();
        for (int i = 0; i < panes.length; i++) {
            if (panes[i].equals(pane)) {
                index = i;
                break;
            }
        }
        if (index != -1)
            mainPane.removeTabAt(index);
    }

    public void closeCurrentPane() {
        if (getCurrentPane().isModified()) {
            int save = JOptionPane.showConfirmDialog(this, "Molecule "
                    + getCurrentPane().getMolecule().getName()
                    + " has been modified.\nSave it ?");
            switch (save) {
            case JOptionPane.YES_OPTION:
                saveCurrentPane(false);
                removeMolecule2DPane(getCurrentPane());
                break;
            case JOptionPane.NO_OPTION:
                removeMolecule2DPane(getCurrentPane());
                break;
            }
            return;
        }
        removeMolecule2DPane(getCurrentPane());
    }

    public void setCurrentPaneName(String name) {
        mainPane.setTitleAt(mainPane.getSelectedIndex(), name);
    }

    public void saveCurrentPane(boolean askFileName) {
        //System.out.println("saveCurrentPaneAs()");
        if (this.getCurrentPane().getMoleculeFileName().length() == 0
                || askFileName) {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            MoleculeFileFilter filter = new MoleculeFileFilter();
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
                if ((new EasyMolMainWindow.MoleculeFileFilter()).accept(chooser
                        .getSelectedFile()))
                    this.getCurrentPane().setMoleculeFileName(
                            chooser.getSelectedFile().getAbsolutePath());
                else
                    this.getCurrentPane().setMoleculeFileName(
                            chooser.getSelectedFile().getAbsolutePath()
                                    + ".xml");

            else
                return;
        }
        new NewMoleculetoXML(this.getCurrentPane()
                .getMolecule(), this.getCurrentPane().getMoleculeFileName());
        getCurrentPane().setModified(false);
        setCurrentPaneName(getCurrentPane().getMolecule().getName());
    }
    public void showBluetoothWindow(EasyMolMainWindow win)
    {
    	BluetoothFrame frame = new BluetoothFrame(win);
    	frame.setVisible(true);
    }
    public void exportHTMLCurrentPane(boolean askFileName)
    {
    	if (this.getCurrentPane().getMoleculeFileName().length() == 0
                || askFileName) {
    		JFileChooser chooser = new JFileChooser(".");
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            MoleculeFileFilter filter = new MoleculeFileFilter();
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
                if ((new EasyMolMainWindow.MoleculeFileFilter()).accept(chooser
                        .getSelectedFile()))
                    this.getCurrentPane().setMoleculeFileName(
                            chooser.getSelectedFile().getAbsolutePath());
                else
                    this.getCurrentPane().setMoleculeFileName(
                            chooser.getSelectedFile().getAbsolutePath()
                                    + ".html");

            else
                return;
    	}
    	new HTMLexporter(this.getCurrentPane().getMolecule(), this.getCurrentPane().getMoleculeFileName());
    	getCurrentPane().setModified(false);
        setCurrentPaneName(getCurrentPane().getMolecule().getName());
    }
    private class MoleculeFileFilter extends FileFilter {
        private class Utils {
            public final static String XML = "xml";
            public final static String PDB = "pdb";
            public final static String HTML = "html";

            public Utils() {
            }

            public String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                return ext;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
         */
        private final String description = "*.xml,*.pdb, *.html  Molecule files";

        public boolean accept(File arg0) {
            if (arg0.isDirectory()) {
                return true;
            }
            Utils u = new Utils();
            String extension = u.getExtension(arg0);
            if (extension != null) {
                if (extension.equalsIgnoreCase(Utils.XML) || extension.equalsIgnoreCase(Utils.PDB) || extension.equalsIgnoreCase(Utils.HTML)) {
                    return true;
                } else {
                    return false;
                }

            }
            return false;
        }
        
        public String getType(File f) {
            Utils u = new Utils();
            return u.getExtension(f);
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.filechooser.FileFilter#getDescription()
         */
        public String getDescription() {
            return description;
        }
    }

    public void loadInNewPane() {
        JFileChooser chooser = new JFileChooser(".");
        MoleculeFileFilter filter = new MoleculeFileFilter();
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Molecule mol = molBuilder.createMoleculeFromFile(chooser
                    .getSelectedFile().getAbsolutePath(), new MoleculeFileFilter().getType(chooser.getSelectedFile()));
            if (mol == null)
                return;
            Molecule2DPane m2d = new Molecule2DPane(mol);
            m2d.setHolder(this);
            addMolecule2DPane(m2d);
            mainPane.setSelectedComponent(m2d);
            m2d
                    .setMoleculeFileName(chooser.getSelectedFile()
                            .getAbsolutePath());
        }
    }


    public Molecule2DPane getCurrentPane() {
        return (Molecule2DPane) mainPane.getSelectedComponent();
    }

    public void createNewMolecule() {
        Molecule m = molBuilder.createNewMolecule();
        Molecule2DPane m2d = new Molecule2DPane(m);
        m2d.setHolder(this);
        addMolecule2DPane(m2d);
        mainPane.setSelectedComponent(m2d);
    }

    private void createMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menu.add(file);
        JMenu view = new JMenu("View");
        view.setMnemonic(KeyEvent.VK_V);
        menu.add(view);

        file.add(new NewMoleculeAction(this));
        file.addSeparator();
        file.add(new LoadMoleculeAction(this));
        file.add(new SaveMoleculeAction(this));
        file.add(new SaveMoleculeAsAction(this));
        file.add(new HTMLExportAction(this));
        file.addSeparator();
        file.add(new CloseMoleculeAction(this));
        file.addSeparator();
        file.add(new AbstractAction("Exit") {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                while (getCurrentPane() != null)
                    closeCurrentPane();
                System.exit(0);

            }
        });
        JMenu communications = new JMenu("Communications");
        communications.setMnemonic(KeyEvent.VK_C);
        menu.add(communications);
        communications.add(new ShowBluetoothAction(this));

        view.add(new DisplayVSEPRAction(this));
        view.add(new Arrange2DViewAction(this));
        
        menu.putClientProperty(Options.HEADER_STYLE_KEY,
                         HeaderStyle.BOTH);
        menu.setBorderPainted(false);

    }

    private void createToolBar() {
        toolBar.setFloatable(false);
        toolBar.add(new NewMoleculeAction(this));
        toolBar.add(new CloseMoleculeAction(this));
        toolBar.addSeparator();
        toolBar.add(new LoadMoleculeAction(this));
        toolBar.add(new SaveMoleculeAction(this));
        toolBar.addSeparator();
        toolBar.add(new DisplayVSEPRAction(this));
        toolBar.add(new Arrange2DViewAction(this));
        
        toolBar.putClientProperty("JToolBar.isRollover",
                Boolean.TRUE);
        toolBar.putClientProperty(Options.HEADER_STYLE_KEY,
                HeaderStyle.BOTH);
        toolBar.setBorderPainted(false);


    }

    public static void main(String[] args) {
        PlasticLookAndFeel laf = new PlasticXPLookAndFeel();
        PlasticLookAndFeel.setMyCurrentTheme(new ExperienceBlue());
        try {
            UIManager.setLookAndFeel(laf);
            Options.setPopupDropShadowEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //MetalLookAndFeel.setCurrentTheme(new EasyMolTheme());
        //JFrame.setDefaultLookAndFeelDecorated(true);
        EasyMolMainWindow mainWin = new EasyMolMainWindow();
        mainWin.setVisible(true);
    }

}
