package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import net.sf.easymol.main.EasyMolMainWindow;
import net.sf.easymol.ui.general.IconProvider;

public class ShowBluetoothAction extends AbstractAction {
	EasyMolMainWindow win;
	public ShowBluetoothAction(EasyMolMainWindow win)
	{
		this.win = win;
		 putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
         "mini-bluetooth"));
		putValue(Action.NAME, "Bluetooth");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B,
                ActionEvent.CTRL_MASK));
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		win.showBluetoothWindow(win);
	}

}
