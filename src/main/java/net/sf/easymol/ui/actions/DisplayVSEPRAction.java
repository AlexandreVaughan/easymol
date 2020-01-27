/*
 * Created on 1 nov. 2004
 *
 */
package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import net.sf.easymol.main.EasyMolMainWindow;
import net.sf.easymol.main.Molecule3DWindow;
import net.sf.easymol.ui.comp3d.vsepr.VSEPRMolecule3DPane;
import net.sf.easymol.ui.general.IconProvider;


/**
 * @author avaughan
 */
public class DisplayVSEPRAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EasyMolMainWindow mainWin = null;

    private Molecule3DWindow currentView = null;

    public DisplayVSEPRAction(EasyMolMainWindow mainWin) {
        this.mainWin = mainWin;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                "render"));
        putValue(Action.NAME, "Display VSEPR");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R,
                ActionEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        // FIXME move to mainwindow ? - mune
        if (currentView != null) {
            currentView.dispose();
        }
        if (mainWin.getCurrentPane() == null)
            return;
        VSEPRMolecule3DPane pane = new VSEPRMolecule3DPane(mainWin.getCurrentPane()
                .getMolecule());
        //mainWin.getCurrentPane().getMolecule().clearObservers();
        mainWin.getCurrentPane().getMolecule().addObserver(pane);
        currentView = new Molecule3DWindow(pane);
        currentView.setVisible(true);

    }

}
