package net.sf.easymol.ui.comp2d.comm.bluetooth;

import javax.swing.JFrame;

import net.sf.easymol.main.EasyMolMainWindow;

public class BluetoothFrame extends JFrame {
	BluetoothPanel panel;
	EasyMolMainWindow win;
	public BluetoothFrame(EasyMolMainWindow win)
	{
		super("Bluetooth");
		this.win = win;
		panel = new BluetoothPanel(win);
		this.getContentPane().add(panel);
		this.pack();
	}
	
}
