   package fr.emn.easymol.test;

	import java.io.*;
   import fr.emn.easymol.xml.*;
   import fr.emn.easymol.core.*;

   public class TestReading {
      public static void main(String[] argv) {
         try {
            MoleculeXMLReader mxr = new MoleculeXMLReader("xml/ethanol.xml");
            Molecule m = mxr.getMolecule();
            int h = mxr.getHashFirst();
            System.out.println(m.getName());
            System.out.println(m.getAtom(h).linkView());
            System.out.println(h);
         }
            catch (IOException ioe) {
               System.out.println("IO error");
            }
      }
   }


