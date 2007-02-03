package net.sf.easymol.ui.comp2d.comm.bluetooth;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.ServiceRecord;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import net.sf.easymol.comm.bluetooth.windows.ImageTester;
import net.sf.easymol.comm.bluetooth.windows.SPPServer;
import net.sf.easymol.main.EasyMolMainWindow;

public class BluetoothPanel extends JPanel {
	JList peerlist;
	JPanel upperpane;
	JPanel lowerpane;
	JButton search, connect, listen;
	EasyMolMainWindow win;
	public BluetoothPanel(EasyMolMainWindow win)
	{
		
		this.setLayout(new GridLayout(2,1));
		this.win = win;
		upperpane = new JPanel();
		lowerpane = new JPanel();
		search = new JButton("Search");
		search.addActionListener(new searchListener());
		connect = new JButton("Connect");
		connect.addActionListener(new connectListener());
		listen = new JButton("Listen");
		listen.addActionListener(new listenListener());
		String[] initial = {"NONE"};
		peerlist = new JList(initial);
		peerlist.setSize(100,100);
		upperpane.add(peerlist);
		
		lowerpane.add(search);
		lowerpane.add(connect);
		lowerpane.add(listen);
		this.add(upperpane);
		this.add(lowerpane);
	}
	class searchListener extends AbstractAction
	{

		public void actionPerformed(ActionEvent arg0) {
			try {
			ServiceRecord rec;
			ImageTester test = new ImageTester();
			rec = test.findImageServer();
			/*if (rec != null)
			{*/
			String[] peers = {rec.getHostDevice().getBluetoothAddress()};
			peerlist.setListData(peers);
			//}
			/*String[] peers = { "NONE" };
			peerlist.setListData(peers);*/
			
				ImageTester.main(null);
			
			}
			catch (BluetoothStateException bse)
			{
				
			}
		}
		
	}
	class listenListener extends AbstractAction
	{

		public void actionPerformed(ActionEvent arg0) {			
				SPPServer serv = new SPPServer();
				serv.run_server(win);
				System.out.println(serv.getMolecule());
		}
		
	}
	class connectListener extends AbstractAction
	{

		public void actionPerformed(ActionEvent arg0) {
			//if (!((String)peerlist.getSelectedValue()).equals("NONE"))
			//{
				ImageTester.main(null);
			//}
			
		}
		
	}
}
