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
public class CloseMoleculeAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private EasyMolMainWindow mainWin = null;

    public CloseMoleculeAction(EasyMolMainWindow main) {
        mainWin = main;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                "delete"));
        putValue(Action.NAME, "Close Molecule");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
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
