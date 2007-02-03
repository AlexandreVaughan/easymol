package net.sf.easymol.comm.bluetooth.windows;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class EasyMolBTConnector implements DiscoveryListener {

	class Cancel extends Thread {
		EasyMolBTConnector client;

		Cancel(EasyMolBTConnector client) {
			this.client = client;
		}

		public void run() {
			try {
				Thread.sleep(400000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("cancelling");

			try {
				LocalDevice.getLocalDevice().getDiscoveryAgent().cancelInquiry(
						client);
			} catch (BluetoothStateException bse) {
				System.out.println("Got BluetoothStateException: " + bse);
			}

			System.out.println("cancelled");
		}
	}

	public static final UUID uuid = new UUID(
			"27012f0c68af4fbf8dbe6bbaf7ab651b", false);

	Vector devices;

	Vector records;

	public static void main(String[] args) {
		if (args.length == 1)
			new EasyMolBTConnector(args[0]);
		else
			System.out.println("syntax: ClientTest <message>");
	}

	public EasyMolBTConnector(String message) {
		devices = new Vector();
		(new Cancel(this)).start();

		synchronized (this) {
			try {
				LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(
						DiscoveryAgent.GIAC, this);
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (BluetoothStateException e) {
				e.printStackTrace();
			}
		}

		for (Enumeration enum_d = devices.elements(); enum_d.hasMoreElements();) {
			RemoteDevice d = (RemoteDevice) enum_d.nextElement();

			try {
				System.out.println(d.getFriendlyName(false));
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(d.getBluetoothAddress());
			System.out.println("PERKEKLE");
			synchronized (this) {
				records = new Vector();

				try {
					/*
					 * LocalDevice.getLocalDevice().getBluetoothAddress().send(LocalDevice.getLocalDevice().socket(true,false),1);
					 * System.out.println(LocalDevice.getLocalDevice().getBluetoothAddress().getsockchannel(LocalDevice.getLocalDevice().getBluetoothAddress().socket(true,false)));
					 */
					LocalDevice.getLocalDevice().getDiscoveryAgent();
					LocalDevice.getLocalDevice().getDiscoveryAgent()
							.searchServices(
									new int[] { 0x0100, 0x0101, 0x0102 },
									new UUID[] { uuid }, d, this);
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (BluetoothStateException e) {
					e.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

			}

			/*
			 * 
			 * BUGBUG: need to give the system time to sort itself out after
			 * doing a service attribute request
			 * 
			 */

			try {

				Thread.sleep(100);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			for (Enumeration enum_r = records.elements(); enum_r
					.hasMoreElements();) {

				ServiceRecord r = (ServiceRecord) enum_r.nextElement();

				System.out.println(r.getAttributeValue(0x0100));

				try {
					StreamConnection conn = (StreamConnection) Connector
							.open(r
									.getConnectionURL(
											ServiceRecord.AUTHENTICATE_NOENCRYPT,
											false));

					DataOutputStream dos = new DataOutputStream(conn
							.openOutputStream());

					dos.writeUTF(message);

					dos.close();
					conn.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		devices.addElement(btDevice);
		System.out.println(btDevice.getBluetoothAddress());
		System.out.println(cod);
	}

	public synchronized void inquiryCompleted(int discType) {
		System.out.println("inquiry completed: discType = " + discType);
		notifyAll();
	}

	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		for (int i = 0; i < servRecord.length; i++)
			records.addElement(servRecord[i]);
	}

	public synchronized void serviceSearchCompleted(int transID, int respCode) {
		System.out.println("service search completed: respCode = " + respCode);
		notifyAll();
	}

}
