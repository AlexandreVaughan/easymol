/*
 * 
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
 * @author stefano borini - munehiro
 */
public class SaveMoleculeAction extends AbstractAction {

    private EasyMolMainWindow mainWin = null;

    public SaveMoleculeAction(EasyMolMainWindow main) {
        mainWin = main;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName("save_all"));
        putValue(Action.NAME, "Save");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
		mainWin.saveCurrentPane();
    }

}
