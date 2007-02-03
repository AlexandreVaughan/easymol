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
import net.sf.easymol.ui.general.IconProvider;


/**
 * @author avaughan
 */
public class NewMoleculeAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private EasyMolMainWindow mainWin = null;

    public NewMoleculeAction(EasyMolMainWindow main) {
        mainWin = main;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                "insert"));
        putValue(Action.NAME, "New Molecule");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        mainWin.createNewMolecule();
    }

}
