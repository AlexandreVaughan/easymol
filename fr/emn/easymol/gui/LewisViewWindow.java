/*
 * 04/18/2002
 *
 * LewisViewWindow.java - The Lewis View representation of a molecule
 * Copyright (C) 2002 Alexandre Vaughan
 * avaughan@altern.org
 * 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
   package fr.emn.easymol.gui;

   import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.JScrollPane;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.core.MoleculeNode;
import fr.emn.easymol.geometry.Atom2D;
import fr.emn.easymol.geometry.Link2D;

/** The Lewis View of a Molecule.<BR><BR>
  * This component is a JPanel containing Atom2D components (the
  * atoms of the molecule) and a list of all the links of the molecule.
  * Synchronization with EasyMol core data structure is implemented. 
**/
   public class LewisViewWindow extends JScrollPane {
   
      private LinkedList links; // the list of links for this molecule
      private Molecule currentMolecule; // the molecule itself
      private int hash; // the hash code of the first atom to parse
      private Atom2D lastSelected=null;
      private Atom2D beforeLastSelected=null;
      private Atom2D rootAtom=null;
   
   /** Builds a new Lewis View Window
     * @param m the molecule to parse
     * @param h the hash code of the first element
   **/
      public LewisViewWindow(Molecule m, int h) {
         currentMolecule=m;
         hash=h;
         links=new LinkedList();
         if (m.getAtom(h) != null) {
            parseMolecule(null,h,0,0,0);
         }
         setLayout(null);
         setBackground(Color.white);
      }
   
   /** Refresh this LewisViewWindow
     * @param m the new molecule to parse
     * @param h the hash code of the new first element
   **/
      public void refresh2D(Molecule m, int h) {
         links=new LinkedList();
         Component[] toRemove = getComponents();
         for (int i=0; i< toRemove.length; i++) {
            remove(toRemove[i]);
         }
         currentMolecule=m;
         hash=h;
         if (m.getAtom(h) != null) {
            parseMolecule(null,h,0,0,0);
         }
         repaint();
      }
   
   /** Gets the last selected Atom2D in the window
     * @return the last selected atom
   **/
      public Atom2D getLastSelected() {
         return lastSelected;
      }
   
   /** Sets the last selected Atom2D in the window
     * @param a2d the last selected atom
   **/
      public void setLastSelected(Atom2D a2d) {
         lastSelected=a2d;
         repaint();
      }
   
   /** Gets the before last selected Atom2D in the window
     * @return the beforelast selected atom
   **/
      public Atom2D getBeforeLastSelected() {
         return beforeLastSelected;
      }
   
   /** Sets the before last selected Atom2D in the window
   **/
      public void setBeforeLastSelected() {
         beforeLastSelected=getLastSelected();
         repaint();
      }
   
   /** Gets the root Atom of the Lewis View 
     * @return the root atom
   **/
      public Atom2D getRootAtom() {
         return rootAtom;
      }
   
   /** Adds an atom to the window
     * @param type the type of the atom
     * @see Atom
   **/
      public int addAtom(int type) {
         int h = currentMolecule.addAtom(type);
         Atom2D a2d = new Atom2D(currentMolecule, h);
         a2d.setLocation(2*Atom2D.GAPX,2*Atom2D.GAPY);
         a2d.setSize(a2d.getMinimumSize());
         this.add(a2d);
         setBeforeLastSelected();
         setLastSelected(a2d);
         repaint();
         return h;
      }
   
   
   /** Adds a link to the window 
     * @param a1 the first linked atom
     * @param a2 the second linked atom
     * @param type the type of the link
     * @return the result of the operation
   **/
      public boolean addLink(Atom2D a1, Atom2D a2, int type) {
         if (a1.equals(a2)) 
            return false;
         int h1 = a1.getHash();
         int h2 = a2.getHash();
         boolean b = false;
         b=currentMolecule.addLink(h1,h2,type);
         if(b) {
            Link2D l =new Link2D(a1,a2,type);
            links.add(l);
         }
         else {
            currentMolecule.removeLink(h1,h2);
         }
         repaint();
         return b;	      
      }
   
   /** Removes an atom to the window (not applied if the atom is
     *  the hashFirst)
     * @param a2d the atom to remove
     * @return the result of the operation
   **/
      public boolean removeAtom(Atom2D a2d) {
         boolean b = false;
         if (a2d != null) {
            int h = a2d.getHash();
            if (h == hash) {
               return b;
            }
            b = currentMolecule.removeAtom(h);
            removeLinks(a2d);
            remove(a2d);
            if (getBeforeLastSelected().equals(getLastSelected())) {
            
               setLastSelected(getRootAtom());
            }
            else {
               setLastSelected(getBeforeLastSelected());
            }
            repaint();
         }
         return b;
      }
   
   /** Removes all the link2D containing the atom2D
     * given in parameter. 
     * @param a2d the atom
   **/
      public void removeLinks(Atom2D a2d) {
         LinkedList links2 = new LinkedList();
         for(int i=0; i < links.size(); i++) {
            Link2D current = (Link2D) links.get(i);
            if ((current.getAtom1()).equals(a2d) || (current.getAtom2()).equals(a2d)) {
               links2.add(current);
            }
         }
         for (int i=0; i <links2.size(); i++) {
            links.remove(links2.get(i));
         } 
         repaint();
      }
   
   /** Removes the selected link
     * @param a1 the first atom2d of the link
     * @param a2 the second atom2d of the link
     * @return the result of the operation
   **/
      public boolean removeLink(Atom2D a1, Atom2D a2) {
         boolean b = false;
         if (a1.equals(a2)) 
            return false;
         int h1 = a1.getHash();
         int h2 = a2.getHash();
         b = currentMolecule.removeLink(h1, h2);
         for(int i=0; i < links.size(); i++) {
            Link2D current = (Link2D) links.get(i);
            if (((current.getAtom1()).equals(a1) && (current.getAtom2()).equals(a2)) || ((current.getAtom1()).equals(a2) && (current.getAtom2()).equals(a1))) {
               links.remove(current);
            }
         }
         repaint();
         return b;
      }
   
   // parse the molecule to render it in the 2D view
      private void parseMolecule(Atom2D parent, int h, int linkType, int position, int fatherPosition) {
      	// Setting the atom2d
         int offset=1;
         final int GAPX = Atom2D.GAPX;
         final int GAPY = Atom2D.GAPY;
         MoleculeNode parentAtom = null;
         Atom2D current = new Atom2D(currentMolecule,h);
         MoleculeNode currentAtom = (MoleculeNode) currentMolecule.getAtom(h);
         offset =(currentAtom.getType() != Atom.C) ? 1 : 2;	
         current.setLocation(7*GAPX,7*GAPY);
         current.setSize(current.getMinimumSize());
         if (h==hash) {
            rootAtom=current;
            setLastSelected(current);
            setBeforeLastSelected();
         }
      	//Setting the position
         if (parent != null) {
            parentAtom =(MoleculeNode) currentMolecule.getAtom(parent.getHash());
            switch(fatherPosition) {
               case 0 :
                  switch(position) {
                     case 1 :
                        current.setLocation((int) parent.getLocation().getX()+offset*GAPX, (int) parent.getLocation().getY());
                        break;
                     case 2 : 
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()+offset*GAPY);
                        break;
                     case 3 :
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()-offset*GAPY);
                        break;
                     case 4 :
                        current.setLocation((int) parent.getLocation().getX()-offset*GAPX, (int) parent.getLocation().getY());
                        break;
                  }
                  break;
               case 1 :
                  switch(position) {
                     case 1 :
                        current.setLocation((int) parent.getLocation().getX()+offset*GAPX, (int) parent.getLocation().getY());
                        break;
                     case 2 : 
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()+offset*GAPY);
                        break;
                     case 3 :
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()-offset*GAPY);
                        break;
                  }
                  break;
               case 2 :
                  switch(position) {
                     case 1 :
                        current.setLocation((int) parent.getLocation().getX()+offset*GAPX, (int) parent.getLocation().getY());
                        break;
                     case 2 : 
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()+offset*GAPY);
                        break;
                     case 3 :
                        current.setLocation((int) parent.getLocation().getX()-offset*GAPX, (int) parent.getLocation().getY());
                        break;
                  }
                  break;
               case 3 :
                  switch(position) {
                     case 1 :
                        current.setLocation((int) parent.getLocation().getX()+offset*GAPX, (int) parent.getLocation().getY());
                        break;
                     case 2 :
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()-offset*GAPY);
                        break;
                     case 3 :
                        current.setLocation((int) parent.getLocation().getX()-offset*GAPX, (int) parent.getLocation().getY());
                        break;
                  }
                  break;
               case 4 :
                  switch(position) {
                     case 1 : 
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()+offset*GAPY);
                        break;
                     case 2 :
                        current.setLocation((int) parent.getLocation().getX(), (int) parent.getLocation().getY()-offset*GAPY);
                        break;
                     case 3 :
                        current.setLocation((int) parent.getLocation().getX()-offset*GAPX, (int) parent.getLocation().getY());
                        break;
                  }
                  break;
               default :
                  break;
            }
         
            Link2D l= new Link2D(current, parent, linkType);
            links.add(l);
         }
         this.add(current);
         int currentPosition=0;
         for (int i =0; i < currentAtom.getLinks().size(); i++) { // We browse all links
            if ((MoleculeNode)currentAtom.getLinks().get(i) != parentAtom) { // if the link is not the parent 
               if (currentAtom.linkType(i)==1 || i == currentAtom.getLinks().indexOf(currentAtom.getLinks().get(i))) { // if it is a single link or the first occurence of a multiple link
                  currentPosition++;
                  parseMolecule(current, ((MoleculeNode) currentAtom.getLinks().get(i)).hashCode(), currentAtom.linkType(i), currentPosition, position);
               }
            
            }
         }
      }
   
   /** Paints the Lewis View
     * @param g the Graphics component used
   **/
      public void paint(Graphics g) {
         super.paint(g);
         g.setColor(Color.black);
         for (int i=0; i < links.size(); i++) {
            Link2D l = (Link2D) links.get(i);
            Atom2D a1 = l.getAtom1();
            Atom2D a2 = l.getAtom2();
            int type = l.getLinkType();
            for (int j=0; j<type; j++) {
               g.drawLine((int) a1.getLocation().getX()+5*j+(int)a2.getSize().getWidth()/2,
                  (int) a1.getLocation().getY()+(int)a2.getSize().getHeight()/2,
                  (int) a2.getLocation().getX()+5*j+(int)a2.getSize().getWidth()/2,
                  (int) a2.getLocation().getY()+(int)a2.getSize().getHeight()/2);
            }
         }
         for (int i=Atom2D.GAPX; i < getWidth()-Atom2D.GAPX; i+=Atom2D.GAPX) {
            for (int j=Atom2D.GAPY; j < getHeight()-Atom2D.GAPY; j+=Atom2D.GAPY) {
               g.drawLine(i,j,i+2,j);
               g.drawLine(i,j,i,j+2);
               g.drawLine(i,j,i-2,j);
               g.drawLine(i,j,i,j-2);
            }
         }
      }
   
      private Image offScreenBuffer;
   
   /** Updates the Lewis View using double Buffering to avoid flashing
     * @param g the graphics component used
   **/
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
   }