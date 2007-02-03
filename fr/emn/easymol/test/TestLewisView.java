   package fr.emn.easymol.test;

   import java.io.IOException;

import javax.swing.JFrame;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.gui.LewisViewWindow;
import fr.emn.easymol.xml.MoleculeXMLReader;

   public class TestLewisView extends JFrame{
      public TestLewisView() {      
      	MoleculeXMLReader mxr = null;
      	Molecule m4=null;
      	int h4=0;
         Molecule m = new Molecule("Water");
         int h1 = m.addAtom(Atom.H);
         int h2 = m.addAtom(Atom.H);
         int o = m.addAtom(Atom.O);
         m.addLink(o,h1);
         m.addLink(o,h2);
         Molecule m3 = new Molecule("Ethanol");
         int h01 = m3.addAtom(Atom.H);
         int h02 = m3.addAtom(Atom.H);
         int h03 = m3.addAtom(Atom.H);
         int c01 = m3.addAtom(Atom.C);
         int h04 = m3.addAtom(Atom.H);
         int h05 = m3.addAtom(Atom.H);
         int h06 = m3.addAtom(Atom.H);
         int c02 = m3.addAtom(Atom.C);
         int o01 = m3.addAtom(Atom.O);
         m3.addLink(c01,o01,2);
         m3.addLink(c01,c02);
         m3.addLink(c01,h01);
         m3.addLink(c01,h02);
         m3.addLink(c02,h03);
         m3.addLink(c02,h04);
         m3.addLink(c02,h05);
         m3.addLink(o01,h06);
      
         try {
            mxr = new MoleculeXMLReader("xml/ethylen.xml");
            m4 = mxr.getMolecule();
            h4 = mxr.getHashFirst();
            System.out.println(m4.getName());
            System.out.println(m4.getAtom(h4).linkView());
            System.out.println(h4);
         }
            catch (IOException ioe) {
               System.out.println("IO error");
            }
      
         LewisViewWindow lw = new LewisViewWindow(m4,h4);
         lw.setLocation(10,10);
         lw.setSize(400,400);
         setSize(400,400);
         getContentPane().setLayout(null);
         getContentPane().add(lw);
         setVisible(true);
      }
      public static void main(String [] argv) {
         TestLewisView test = new TestLewisView();
      }
   } 
