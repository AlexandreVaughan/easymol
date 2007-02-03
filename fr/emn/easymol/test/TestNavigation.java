   package fr.emn.easymol.test;

   import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.gui.NavigationWindow;
import fr.emn.easymol.xml.MoleculeXMLWriter;

   public class TestNavigation {
      public static void main (String[] argv) {
         Molecule m = new Molecule("Water");
         int h1 = m.addAtom(Atom.H);
         int h2 = m.addAtom(Atom.H);
         int o = m.addAtom(Atom.O);
         m.addLink(h1,o);
         m.addLink(h2,o);
      
         Molecule m2 = new Molecule("Carbon Dioxyde");
         int o1 = m2.addAtom(Atom.O);
         int o2 = m2.addAtom(Atom.O);
         int c = m2.addAtom(Atom.C);
         m2.addLink(o1,c,2);
         m2.addLink(o2,c,2);
      
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
         m3.addLink(c01,o01);
         m3.addLink(c01,c02);
         m3.addLink(c01,h01);
         m3.addLink(c01,h02);
         m3.addLink(c02,h03);
         m3.addLink(c02,h04);
         m3.addLink(c02,h05);
         m3.addLink(o01,h06);
      
         Molecule m4 = new Molecule("Methan");
         int h11 = m4.addAtom(Atom.H);
         int h12 = m4.addAtom(Atom.H);
         int h13 = m4.addAtom(Atom.H);
         int h14 = m4.addAtom(Atom.H);
         int c11 = m4.addAtom(Atom.C);
         m4.addLink(c11,h11);
         m4.addLink(c11,h12);
         m4.addLink(c11,h13);
         m4.addLink(c11,h14);
      
         Molecule m5 = new Molecule("Ammoniac");
         int h21 = m5.addAtom(Atom.H);
         int h22 = m5.addAtom(Atom.H);
         int h23 = m5.addAtom(Atom.H);
         int n21 = m5.addAtom(Atom.N);
         m5.addLink(h21,n21);
         m5.addLink(h22,n21);
         m5.addLink(h23,n21);
      
         Molecule m6 = new Molecule("Propan");
         int h31 = m6.addAtom(Atom.H);
         int h32 = m6.addAtom(Atom.H);
         int h33 = m6.addAtom(Atom.H);
         int h34 = m6.addAtom(Atom.H);
         int h35 = m6.addAtom(Atom.H);
         int h36 = m6.addAtom(Atom.H);
         int h37 = m6.addAtom(Atom.H);
         int h38 = m6.addAtom(Atom.H);
         int c31 = m6.addAtom(Atom.C);
         int c32 = m6.addAtom(Atom.C);
         int c33 = m6.addAtom(Atom.C);
         m6.addLink(c31,h31);
         m6.addLink(c31,h32);
         m6.addLink(c31,h33);
         m6.addLink(c31,c32);
         m6.addLink(c32,h34);
         m6.addLink(c32,h35);
         m6.addLink(c32,c33);
         m6.addLink(c33,h36);
         m6.addLink(c33,h37);
         m6.addLink(c33,h38);
      
         Molecule m7 = new Molecule("Butan");
         int h41 = m7.addAtom(Atom.H);
         int h42 = m7.addAtom(Atom.H);
         int h43 = m7.addAtom(Atom.H);
         int h44 = m7.addAtom(Atom.H);
         int h45 = m7.addAtom(Atom.H);
         int h46 = m7.addAtom(Atom.H);
         int h47 = m7.addAtom(Atom.H);
         int h48 = m7.addAtom(Atom.H);
         int h49 = m7.addAtom(Atom.H);
         int h410 = m7.addAtom(Atom.H);
         int c41 = m7.addAtom(Atom.C);
         int c42 = m7.addAtom(Atom.C);
         int c43 = m7.addAtom(Atom.C);
         int c44 = m7.addAtom(Atom.C);
         m7.addLink(c41,h41);
         m7.addLink(c41,h42);
         m7.addLink(c41,h43);
         m7.addLink(c41,c42);
      
         m7.addLink(c42,h44);
         m7.addLink(c42,h45);
         m7.addLink(c42,c43);
      
         m7.addLink(c43,h46);
         m7.addLink(c43,h47);
         m7.addLink(c43,c44);
      
         m7.addLink(c44,h48);
         m7.addLink(c44,h49);
         m7.addLink(c44,h410);
      
         NavigationWindow n = new NavigationWindow(m6,c31);
         JButton closeWin = new JButton("Close");
         n.getContentPane().add("South", closeWin);
         closeWin.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     System.exit(0);
                  }});
      
         n.show();
         try {
            MoleculeXMLWriter mxw = new MoleculeXMLWriter(m,o,"xml/water.xml");
            mxw.writeMolecule();
            MoleculeXMLWriter mxw2 = new MoleculeXMLWriter(m2,c,"xml/co2.xml");
            mxw2.writeMolecule();
            MoleculeXMLWriter mxw3 = new MoleculeXMLWriter(m3,c01,"xml/ethanol.xml");
            mxw3.writeMolecule();
            MoleculeXMLWriter mxw4 = new MoleculeXMLWriter(m4,c11,"xml/methan.xml");
            mxw4.writeMolecule();
            MoleculeXMLWriter mxw5 = new MoleculeXMLWriter(m5,n21,"xml/ammoniac.xml");
            mxw5.writeMolecule();
            MoleculeXMLWriter mxw6 = new MoleculeXMLWriter(m6,c32,"xml/propan.xml");
            mxw6.writeMolecule();
            MoleculeXMLWriter mxw7 = new MoleculeXMLWriter(m7,c41,"xml/butan.xml");
            mxw7.writeMolecule();
         }
            catch (IOException ioe) {
               System.err.println("IO Exception");
            }
      
      }
   }