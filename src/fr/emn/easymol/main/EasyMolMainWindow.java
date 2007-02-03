/*
 * Created on 1 nov. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;

import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.ui.actions.Arrange2DViewAction;
import fr.emn.easymol.ui.actions.CloseMoleculeAction;
import fr.emn.easymol.ui.actions.Display3DViewAction;
import fr.emn.easymol.ui.actions.ImportXMLAction;
import fr.emn.easymol.ui.actions.NewMoleculeAction;
import fr.emn.easymol.ui.actions.SaveMoleculeAction;
import fr.emn.easymol.ui.actions.SaveMoleculeAsAction;
import fr.emn.easymol.ui.components.EasyMolTheme;
import fr.emn.easymol.ui.components.IconProvider;
import fr.emn.easymol.ui.components.Molecule2DPane;
import fr.emn.easymol.xml.MoleculetoXML;
import fr.emn.easymol.xml.XMLtoMolecule;
/**
 * @author avaughan
 *  
 */
public class EasyMolMainWindow extends JFrame {
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
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(BorderLayout.CENTER, mainPane);
        this.getContentPane().add(BorderLayout.NORTH, toolBar);
        this.setJMenuBar(menu);
        this.setSize(800, 600);
        this.setTitle("EasyMol");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // TODO : add 'save ?' dialog
		createNewMolecule();
        // TODO : create an 'edit molecule property' dialog and menu
    }

    public void addMolecule2DPane(Molecule2DPane pane) {
        mainPane.addTab(pane.getMolecule().getName(), IconProvider.getInstance().getIconByName("copy"), pane);
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
		// FIXME : save on close
    	removeMolecule2DPane(getCurrentPane());
	}

	public void saveCurrentPane() {
		System.out.println("saveCurrentPane()");
		MoleculetoXML molxml = new MoleculetoXML(this.getCurrentPane().getMolecule()); 
	}

	public void saveCurrentPaneAs() {
		System.out.println("saveCurrentPaneAs()");
		JFileChooser chooser = new JFileChooser("xml");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		XMLFileFilter filter = new XMLFileFilter();
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
    		MoleculetoXML molxml = new MoleculetoXML(this.getCurrentPane().getMolecule(),chooser.getSelectedFile().getAbsolutePath());
    	}
	}
	
	private class XMLFileFilter extends FileFilter
	{
		private class Utils 
		{
			public final static String xml = "xml";
		
			public Utils(){}
			public String getExtension(File f) {
		        String ext = null;
		        String s = f.getName();
		        int i = s.lastIndexOf('.');

		        if (i > 0 &&  i < s.length() - 1) {
		            ext = s.substring(i+1).toLowerCase();
		        }
		        return ext;
		    }
		}
			/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
		 */
		private final String description = "*.xml XML-files";
		public boolean accept(File arg0) {
			if (arg0.isDirectory()) {
				return true;
			}
			Utils u = new Utils();
			String extension = u.getExtension(arg0);
		    if (extension != null) {
			if (extension.equals(Utils.xml)){
			return true;
			} else {
				return false;
			}
			
		}
		    return false;
		}
		/* (non-Javadoc)
		 * @see javax.swing.filechooser.FileFilter#getDescription()
		 */
		public String getDescription() {			
			return description;
		}
}
	public void importXML()
	{
//		System.out.println("Import XML -- beginning");
		JFileChooser chooser = new JFileChooser("xml");
		XMLFileFilter filter = new XMLFileFilter();
	    //filter.addExtension("jpg");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
		XMLtoMolecule xml = new XMLtoMolecule(new java.io.File(chooser.getSelectedFile().getAbsolutePath()));
		Molecule2DPane m2d = new Molecule2DPane(xml.getMolecule());
		addMolecule2DPane(m2d);
		mainPane.setSelectedComponent(m2d);
    	}
	}
	public void exportXML()
	{
		
	}
    public Molecule2DPane getCurrentPane() {
        return (Molecule2DPane) mainPane.getSelectedComponent();
    }

	public void createNewMolecule() {
		Molecule m = molBuilder.createNewMolecule();
        Molecule2DPane m2d = new Molecule2DPane(m);
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
        file.add(new SaveMoleculeAction(this));
        file.add(new SaveMoleculeAsAction(this));
        file.add(new ImportXMLAction(this));
        //file.add(new ExportXMLAction(this));
        file.addSeparator();
        file.add(new CloseMoleculeAction(this));
        file.addSeparator();
        file.add(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                // TODO : add 'save ?' dialog
                System.exit(0);

            }
        });

        view.add(new Display3DViewAction(this));
        view.add(new Arrange2DViewAction(this));

    }

    private void createToolBar() {
        toolBar.setFloatable(false);
        toolBar.add(new NewMoleculeAction(this));
        toolBar.add(new CloseMoleculeAction(this));
        toolBar.addSeparator();
        toolBar.add(new Display3DViewAction(this));
        toolBar.add(new Arrange2DViewAction(this));

    }

    public static void main(String[] args) {
        MetalLookAndFeel.setCurrentTheme(new EasyMolTheme());
        JFrame.setDefaultLookAndFeelDecorated(true);
        EasyMolMainWindow mainWin = new EasyMolMainWindow();
        mainWin.setVisible(true);
    }

}
