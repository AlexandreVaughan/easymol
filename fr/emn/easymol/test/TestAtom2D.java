   package fr.emn.easymol.test;

   import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.JFrame;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.geometry.Atom2D;
import fr.emn.easymol.geometry.Link2D;

   public class TestAtom2D extends JFrame{
      LinkedList links = new LinkedList();	
   
      public  TestAtom2D() {
         Molecule m = new Molecule("Water");
         int h1 = m.addAtom(Atom.H);
         int h2 = m.addAtom(Atom.H);
         int o = m.addAtom(Atom.O);
         m.addLink(h1,o);
         m.addLink(h2,o);
         Atom2D h1View = new Atom2D(m, h1);
         Atom2D h2View = new Atom2D(m, h2);
         Atom2D o2View = new Atom2D(m, o);
         links.add(new Link2D(h1View, o2View));
         links.add(new Link2D(h2View, o2View));
         getContentPane().setLayout(null);
         getContentPane().add(h1View);
         h1View.setLocation(150,100);
         h1View.setSize(h1View.getPreferredSize());
         getContentPane().add(h2View);
         h2View.setLocation(50,100);
         h2View.setSize(h2View.getPreferredSize());
         getContentPane().add(o2View);
         o2View.setLocation(100,50);
         o2View.setSize(o2View.getPreferredSize());
         setSize(400,400);
         setVisible(true);
      }
   
      public void paint(Graphics g) {
         super.paint(g);
         g.setColor(Color.black);
         for (int i=0; i < links.size(); i++) {
            Link2D l = (Link2D) links.get(i);
            Atom2D a1 = l.getAtom1();
            Atom2D a2 = l.getAtom2();
            int type = l.getLinkType();
            for (int j=0; j<type; j++) {
               g.drawLine((int) a1.getLocation().getX()+j+(int)a2.getSize().getWidth()/2,
                  (int) a1.getLocation().getY()+Atom2D.getCapWidth()+(int)a1.getSize().getHeight()/2,
                  (int) a2.getLocation().getX()+j+(int)a2.getSize().getWidth()/2,
                  (int) a2.getLocation().getY()+Atom2D.getCapWidth()+(int)a2.getSize().getHeight()/2);
            }
         }
         super.repaint();
      }
   
   
      Image offScreenBuffer;
   
      public void update(Graphics g)
      {
         Graphics gr; 
      // Will hold the graphics context from the offScreenBuffer.
      // We need to make sure we keep our offscreen buffer the same size
      // as the graphics context we're working with.
         if (offScreenBuffer==null ||
         (! (offScreenBuffer.getWidth(this) == this.getSize().width
         && offScreenBuffer.getHeight(this) == this.getSize().height)))
         {
            offScreenBuffer = this.createImage(getSize().width, getSize().height);
         }
      
      // We need to use our buffer Image as a Graphics object:
         gr = offScreenBuffer.getGraphics();
      
         paint(gr); // Passes our off-screen buffer to our paint method, which,
               // unsuspecting, paints on it just as it would on the Graphics
               // passed by the browser or applet viewer.
         g.drawImage(offScreenBuffer, 0, 0, this);
               // And now we transfer the info in the buffer onto the
               // graphics context we got from the browser in one smooth motion.
      }
      public static void main(String[] args) {
         TestAtom2D test = new TestAtom2D();
      }
   }


