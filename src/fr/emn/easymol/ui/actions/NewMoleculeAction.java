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
public class NewMoleculeAction extends AbstractAction {

    private EasyMolMainWindow mainWin = null;

    public NewMoleculeAction(EasyMolMainWindow main) {
        mainWin = main;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName("insert"));
        putValue(Action.NAME, "New Molecule");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        mainWin.createNewMolecule();
    }

}
