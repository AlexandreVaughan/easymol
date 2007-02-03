/*
 * Created on 17.6.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.emn.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import fr.emn.easymol.main.EasyMolMainWindow;

/**
 * @author Aleksi Suomalainen
 * @deprecated
 * 
 */
public class ExportXMLAction extends AbstractAction {
	EasyMolMainWindow main = null;
	
	/**
	 * @param main
	 */
	public ExportXMLAction(EasyMolMainWindow main) {
		super();
		this.main = main;
		putValue(Action.NAME, "Export XML...");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		main.saveCurrentPane();
		// TODO Auto-generated method stub

	}
}
