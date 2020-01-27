/*
 */
package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.sf.easymol.main.EasyMolMainWindow;


/**
 * @author stefano borini - munehiro
 */
public class SaveMoleculeAsAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private EasyMolMainWindow mainWin = null;

    public SaveMoleculeAsAction(EasyMolMainWindow main) {
        mainWin = main;
        putValue(Action.NAME, "Save as...");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
    }


    public void actionPerformed(ActionEvent e) {
        mainWin.saveCurrentPane(true);
    }

}
