package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import net.sf.easymol.main.EasyMolMainWindow;
import net.sf.easymol.ui.general.IconProvider;

public class HTMLExportAction extends AbstractAction {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private EasyMolMainWindow mainWin = null;
    public HTMLExportAction(EasyMolMainWindow main)
    {
    	mainWin = main;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                "save_all"));
        putValue(Action.NAME, "Export as HTML");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H,
                ActionEvent.CTRL_MASK));
    }
	public void actionPerformed(ActionEvent arg0) {
		mainWin.exportHTMLCurrentPane(true);
	}

}
