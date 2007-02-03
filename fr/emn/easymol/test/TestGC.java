   package fr.emn.easymol.test;
   import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

   public class TestGC extends JFrame {
      public static void main(String [] args) {
      
      /** We get the GraphicsEnvironment (containings printers, screens and image buffers : in fact,
      everything that is printable in)**/
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      
      /** We get all the screens from this GraphicsEnvironment (note that GraphicsDeviceS can also be 
      printers) **/
         GraphicsDevice[] gs = ge.getScreenDevices();
      
         for (int j = 0; j < gs.length; j++) { 
         // We extract each GraphicsDevice from the array of GraphicsDevices
            GraphicsDevice gd = gs[j];
         
         /**For each GraphicsDevice extracted, we get all of the GraphicsConfiguration objects associated
         with it (the GraphicsConfiguration object describes the characteristics of a graphics destination 
         such as a printer or monitor.
         We also try the method which directly returns **/
            GraphicsConfiguration[] gc =gd.getConfigurations();
         
            for (int k = 0; k < gc.length; k++) { 
               System.out.println(gc[k]+ "\n");
               System.out.println(gc[k].getBounds()+ "\n");
            
               }
            }
      
      // For a normal use, we get the default GraphicsConfiguration using this: 
         GraphicsConfiguration def = (GraphicsEnvironment.getLocalGraphicsEnvironment()).getDefaultScreenDevice().getDefaultConfiguration();
      
         JFrame j = new JFrame("Essai",def);
         System.out.println(def);
         j.setSize(300,300);
         j.setDefaultCloseOperation(EXIT_ON_CLOSE);
         Rectangle bounds= def.getBounds();
         j.setLocation((int) bounds.getWidth()/2,(int) bounds.getHeight()/2);
         j.show();
      
      
      
      
      
         }
   
      }