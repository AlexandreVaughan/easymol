 package net.sf.easymol.comm.bluetooth.windows;
 //import javax.microedition.lcdui.*;
 import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import net.sf.easymol.core.Molecule;
import net.sf.easymol.io.xml.NewXMLtoMolecule;
import net.sf.easymol.main.EasyMolMainWindow;
import net.sf.easymol.ui.comp2d.Molecule2DPane;
 //import net.benhui.btgallery.*;
 //import net.benhui.btgallery.spp_gui.*;
 
 /**
  *
  * <p>Title: Example Serial Port Profile Server.</p>
  * <p>Description: This example server only handle one SPP server connection
  * and wait for a client to connect. Once a client connection accept, it read
  * one string off the connection stream, display this string on screen, then
  * echo the string back to client.
  * Then it waits for the next client connection again.</p>
  * <p>Description: Important area: run() </p>
 *
  * @author Ben Hui (www.benhui.net)
  * @version 1.0
  *
  * LICENSE:
  * This code is licensed under GPL. (See http://www.gnu.org/copyleft/gpl.html)
  */
 public class SPPServer implements Runnable
 {
   // Bluetooth singleton object
   LocalDevice device;
   DiscoveryAgent agent;
   Molecule mol;
   // SPP_Server specific service UUID
   // note: this UUID must be a string of 32 char
   // do not use the 0x???? constructor because it won't
   // work. not sure if it is a N6600 bug or not
   public final static UUID uuid = new UUID("0ECEF8B070D211DAA97C000FB07A0B78", false);
 
   //
   // major service class as SERVICE_TELEPHONY
   private final static int SERVICE_TELEPHONY = 0x1000;
 
 
   // control flag for run loop
   // set true to exit loop
   public boolean done = false;
 
   // our BT server connection
   public StreamConnectionNotifier server;
   //private StreamConnection con = null; 
   private EasyMolMainWindow win;
   public SPPServer()
   {
   }
 
   public void run_server(EasyMolMainWindow window)
   {
     try
     {
       //
       // initialize the JABWT stack
       device = LocalDevice.getLocalDevice(); // obtain reference to singleton
       device.setDiscoverable(DiscoveryAgent.GIAC); // set Discover mode to LIAC
       if (window != null)
       {
       this.win = window;
       }
       // start a thread to serve the server connection.
       // for simplicity of this demo, we only start one server thread
       // see run() for the task of this thread
       Thread t = new Thread( this );
       t.start();
 
     } catch ( BluetoothStateException e )
     {
       e.printStackTrace();
     }
 
   }
 
   public void run()
   {
     // human friendly name of this service
     String appName = "SPPServerExample";
 
 
     // connection to remote device
     StreamConnection con = null;
     try
     {
       String url = "btspp://localhost:" + uuid.toString() +";name="+ appName+";authenticate=false;encrypt=false;";
       log("server url: " + url );
 
       // Create a server connection object, using a
       // Serial Port Profile URL syntax and our specific UUID
       // and set the service name to BlueChatApp
          
       server = (StreamConnectionNotifier) Connector.open(url);
       ServiceRecord rec = device.getRecord(server);
       // Retrieve the service record template
       
       System.out.println("OLD:"+rec.getAttributeValue(0x0004));
       setChannelId(rec,14);
       //server = (StreamConnectionNotifier)Connector.open(rec.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false));
       //server.setServiceRecord(rec);
       
       // set ServiceRecrod ServiceAvailability (0x0008) attribute to indicate our service is available
       // 0xFF indicate fully available status
       // This operation is optional
       rec.setAttributeValue( 0x0008, new DataElement( DataElement.U_INT_1, 0xFF ) );
       System.out.println("NEW:"+rec.getAttributeValue(0x0004));
       
       // Print the service record, which already contains
       // some default values
       //Util.printServiceRecord( rec );
 
       // Set the Major Service Classes flag in Bluetooth stack.
       // We choose Telephony Service
       //rec.setDeviceServiceClasses( SERVICE_TELEPHONY  );
 
 
 
     } catch (Exception e)
     {
       e.printStackTrace();
       log(e.getClass().getName()+" "+e.getMessage());
     }
 
     while( !done)
     {
       try {
         ///////////////////////////////
         log("local service waiting for client connection...");
 
         //
         // start accepting client connection.
         // This method will block until a client
         // connected
         con = server.acceptAndOpen();
         
         log("accepted a client connection. reading it...");
         //
         // retrieve the remote device object
         RemoteDevice rdev = RemoteDevice.getRemoteDevice( con );
         System.out.println(rdev.getBluetoothAddress());
         // obtain an input stream to the remote service
         InputStream is = con.openInputStream();
         OutputStream os = con.openOutputStream();
         ObjectInputStream ois = new ObjectInputStream(is);
 		ObjectOutputStream oos = new ObjectOutputStream(os);
 		DataOutputStream os2 = null;
 		InputStream is2 = null;
         // Use the values to generate a key pair
 	    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
         AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator
 		.getInstance("DH");
         paramGen.init(512,SecureRandom.getInstance("SHA1PRNG"));
 AlgorithmParameters params = paramGen.generateParameters();
 	
         DHParameterSpec dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);
         keyGen.initialize(dhSpec);
         KeyPair keypair = keyGen.generateKeyPair();
 	        // Get the generated public and private keys
 	        PrivateKey privateKey = keypair.getPrivate();
 	        PublicKey publicKey = keypair.getPublic();
 	        PublicKey rempub = null;
 	        
 	        // Send the public key bytes to the other party...
 	        byte[] ourpublicKeyBytes = publicKey.getEncoded();
 	        byte[] theirpublicKeyBytes = null;
 	        //System.out.println("Our: "+toHexString(ourpublicKeyBytes));
 	        //ByteBuffer buf = ByteBuffer.wrap(publicKeyBytes);
 	        // Retrieve the public key bytes of the other party
 	        //rempub = (PublicKey)ois.readObject();
 	        
 	        //rempub = ((PublicKey)ois.readObject());
 	        theirpublicKeyBytes = ((PublicKey)ois.readObject()).getEncoded();
 	        System.out.println("The: "+toHexString(theirpublicKeyBytes));
 	        //ois.close();
 	        /*if (ourpublicKeyBytes.length-theirpublicKeyBytes.length>0)
 	        {
 	        System.out.println(ourpublicKeyBytes.length-theirpublicKeyBytes.length);
 	        theirpublicKeyBytes = ByteBuffer.wrap(theirpublicKeyBytes).put(theirpublicKeyBytes.length+1,(byte)1).array();
 	        }*/
 	        //System.out.println("The: "+toHexString(theirpublicKeyBytes));
 	        //System.out.println(rempub.getFormat());
 	        //System.out.println(rempub.getAlgorithm());
 	        // Convert the public key bytes into a PublicKey object
 		
 	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(theirpublicKeyBytes);
 	        KeyFactory keyFact = KeyFactory.getInstance("DH");
 	        rempub = keyFact.generatePublic(x509KeySpec);
 	        DHParameterSpec paramspec = ((DHPublicKey) rempub).getParams();
 	        keyGen.initialize(paramspec);
 	        keypair = keyGen.genKeyPair();
 	        publicKey = keypair.getPublic();
 	        privateKey = keypair.getPrivate();
 	        System.out.println("Our: "+toHexString(publicKey.getEncoded()));
 	        oos.writeObject(publicKey);
 	        
 	        oos.close();
 	        os.close();
 	        Thread.sleep(1000);
 	       String provider = "SUN";
 	        // Prepare to generate the secret key with the private key and public key of the other party
 	        KeyAgreement ka = KeyAgreement.getInstance("DH");	        
 	        ka.init(privateKey);
 	        ka.doPhase(rempub, true);
 	    
 	        // Specify the type of key to generate;
 	        // see e458 Listing All Available Symmetric Key Generators
 	        String algorithm = "TripleDES";
 	        
 	        // Generate the secret key
 	        SecretKey secretkey = ka.generateSecret(algorithm);
 	        System.out.println(toHexString(secretkey.getEncoded()));
 	        SecretKeySpec skeyspec = new SecretKeySpec(secretkey.getEncoded(),algorithm);
 	        // Use the secret key to encrypt/decrypt data;
 	        // see e462 Encrypting a String with DES
 	        Cipher c = Cipher.getInstance(algorithm);
 	        c.init(Cipher.DECRYPT_MODE,skeyspec);
 		//GZIPInputStream gzin = new GZIPInputStream(is);
 		long ttim = System.currentTimeMillis();
 		is2 = con.openInputStream();
 		os2 = con.openDataOutputStream();
 		//CipherInputStream cis = new CipherInputStream(is2,c);
 		ois = new ObjectInputStream(is2);
 		SealedObject so = (SealedObject)ois.readObject();
 		org.jdom.Document mol = (org.jdom.Document)so.getObject(secretkey);
 		ois.close();
 		NewXMLtoMolecule mol2 = new NewXMLtoMolecule(mol);
 		System.out.println("Time taken:"+(System.currentTimeMillis()-ttim));
 		if (win != null)
 		{
 		this.win.addMolecule2DPane(new Molecule2DPane(mol2.getMolecule()));
 		}
 		System.out.println(mol2.getMolecule());
 		//oos.writeObject(new String("PERKELE"));
 		 
 		 
 		con.close();
 		}
 		catch (Exception e)
 		{
 			e.printStackTrace();
 		}
 		
 		
         log("echo back to client");
         
         // close current connection, wait for the next one
         try{
        	 
         con.close();
     	}
         catch(Exception e)
         {
        	 
         }
         
       
         done = true;
     } // while
     
     try {
    	
     Thread.currentThread().interrupt();
    	 
     } catch (Throwable t)
     {}
    
   }
 
   /**
    * An utility function to display a log message
    * @param s String
    */
   public void log( String s )
   {
     System.out.println( s );
   }
   public static void main (String args[])
   {
	   SPPServer server = new SPPServer();
	   server.run_server(null);
   }
   private void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}
   public void setChannelId(ServiceRecord r,int chan)
   {
	    DataElement testEl = new DataElement (DataElement.DATSEQ);
		DataElement testEl1 =  new DataElement (DataElement.DATSEQ);
		testEl1.addElement(new DataElement (DataElement.UUID, new UUID(0x0100)));
		testEl.addElement(testEl1);
		DataElement testEl2 = new DataElement (DataElement.DATSEQ);
		testEl2.addElement(new DataElement (DataElement.UUID, new UUID(0x0003)));
		testEl2.addElement(new DataElement (DataElement.U_INT_1, chan));
		testEl.addElement(testEl2);
		r.setAttributeValue(0x0004, testEl);	   
	}
   public synchronized Molecule getMolecule()
   {
   	return mol;
   }
	/*
	 * Converts a byte array to hex string
	 */
   
	private String toHexString(byte[] block) {
		StringBuffer buf = new StringBuffer();

		int len = block.length;

		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
			if (i < len - 1) {
				buf.append(":");
			}
		}
		return buf.toString();
	}
 }