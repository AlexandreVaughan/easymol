/*
 * TODO Document
 */
package fr.emn.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import fr.emn.easymol.main.EasyMolMainWindow;

/**
 * @author stefano borini - munehiro
 */
public class SaveMoleculeAsAction extends AbstractAction {

    private EasyMolMainWindow mainWin = null;

    public SaveMoleculeAsAction(EasyMolMainWindow main) {
        mainWin = main;
        //putValue(Action.SMALL_ICON, IconProvider.instance().getIconByName("saveas"));
        putValue(Action.NAME, "Save as...");
        //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A,
        //        ActionEvent.CTRL_MASK));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
		mainWin.saveCurrentPaneAs();
    }

}
