   package fr.emn.easymol.test;

   import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;


   public class TestCore {
   
      public static void main(String [] argv) {
         Molecule m = new Molecule("Water");
         int h1 = m.addAtom(Atom.H);
         int h2 = m.addAtom(Atom.H);
         int o = m.addAtom(Atom.O);
         m.addLink(h1,o);
         m.addLink(h2,o);
         System.out.println(m.getName());
         System.out.println(m.getAtom(h1).linkView());
         System.out.println(m.getAtom(h2).linkView());
         System.out.println(m.getAtom(o).linkView());
      }
   }

