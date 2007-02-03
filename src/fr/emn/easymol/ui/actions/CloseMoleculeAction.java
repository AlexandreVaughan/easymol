/*
 * Created on 1 nov. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import fr.emn.easymol.main.EasyMolMainWindow;
import fr.emn.easymol.ui.components.IconProvider;

/**
 * @author avaughan
 */
public class CloseMoleculeAction extends AbstractAction {

    private EasyMolMainWindow mainWin = null;

    public CloseMoleculeAction(EasyMolMainWindow main) {
        mainWin = main;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName("delete"));
        putValue(Action.NAME, "Close Molecule");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
                ActionEvent.CTRL_MASK));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
		mainWin.closeCurrentPane();
    }

}
