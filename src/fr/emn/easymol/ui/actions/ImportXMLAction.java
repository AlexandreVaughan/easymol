/*
 * Created on 15.6.2005
 *
 * 
 */
package fr.emn.easymol.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import fr.emn.easymol.main.EasyMolMainWindow;

/**
 * @author Aleksi Suomalainen
 *
 * 
 */
public class ImportXMLAction extends AbstractAction {
	private EasyMolMainWindow mainWin = null;
	
	
	/**
	 * @param mainWin
	 */
	public ImportXMLAction(EasyMolMainWindow mainWin) {
		
		this.mainWin = mainWin;
		//putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName("importxml"));
		putValue(Action.NAME, "Import XML-file...");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X,
                ActionEvent.ALT_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		mainWin.importXML();
	}

}
