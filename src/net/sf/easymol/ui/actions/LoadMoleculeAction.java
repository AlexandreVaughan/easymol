/*
 * Created on 15.6.2005
 *
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
 * @author Aleksi Suomalainen
 * 
 *  
 */
public class LoadMoleculeAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private EasyMolMainWindow mainWin = null;

    /**
     * @param mainWin
     */
    public LoadMoleculeAction(EasyMolMainWindow mainWin) {

        this.mainWin = mainWin;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                "stock_open"));
        putValue(Action.NAME, "Open...");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {

        mainWin.loadInNewPane();
    }

}
