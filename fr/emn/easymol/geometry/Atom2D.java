/*
 * 04/18/2002
 *
 * Atom2D.java - The Atom 2D representation
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

   package fr.emn.easymol.geometry;

   import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.gui.LewisViewWindow;

/** The 2D atom representation. This class is a Java customized component
  * associated with an atom in a molecule. It can be used as a single button
  * and implements basic drag and drop behavior to move itself as well as
  * the magnetic grid behavior.
**/
   public class Atom2D extends JComponent {
      private static int capWidth = 20;          // The width of the Button's endcap
      private String label;                      // The Button's text
      private boolean pressed = false;           // true if the button is detented.
      private ActionListener actionListener;     // Post action events to listeners
      private Molecule molecule;                 // The current molecule
      private int hash;                          // the hash Code of the current atom
      private boolean mouseOver = false;         // the mouse over flag
    /** The x-gap for the atom 2D (used for Atom2D size) **/
      public static final int GAPX = 25;
    /** The y-gap for the atom 2D (used for Atom2D size) **/
      public static final int GAPY = 25;
   
   /**
     * Builds a new Atom2D with the specified atom
     * @param m the current molecule of the Atom
     * @param h the hashCode of the atom in the molecule
   **/
      public Atom2D(Molecule m, int h) {
         this.label = m.getAtom(h).toString();
         this.molecule=m;
         this.hash=h;
         enableEvents(AWTEvent.MOUSE_EVENT_MASK);
         setFont(new Font("Monospaced", Font.PLAIN, 18));
      }
   
   /** Returns the hash code of the atom in the current molecule
     * @return the hash code
   **/
      public int getHash() {
         return hash;
      }
   
   /** The caps width of the Atom2D
     * @return the cap width
   **/
      public static int getCapWidth() {
         return capWidth;
      }
   
   /**
     * gets the label of the atom2D
     * @see #setLabel(Molecule, int)
     * @return the Label
   **/
      public String getLabel() {
         return label;
      }
   
   /**
     * sets the label of the Atom2D using the Atom Symbol
     * @param m the current molecule
     * @param h the hashCode for the label
   **/ 
      public void setLabel(Molecule m, int h) {
         this.label = m.getAtom(h).toString();
         this.molecule=m;
         this.hash=h;
         invalidate();
         repaint();
      }
   
   /**
     * Paints the Atom2D
     * @param g the Graphics component used.
   **/
      public void paint(Graphics g) {
      
         LewisViewWindow lvw = (LewisViewWindow) getParent();
         int width = getSize().width-1;
         int height = getSize().height-1;
      
         Color interior;
         Color highlight1;
         Color highlight2;
         setBackground(Color.white);
      
      // Mouse is not over the atom2D
         if(!mouseOver) {
            if(lvw.getLastSelected().equals(this)) {
               setForeground(Color.blue);
            }
            else if (lvw.getBeforeLastSelected().equals(this)) {
               setForeground(new Color(200,0,255));
            }
            else if (lvw.getRootAtom().equals(this)) {
               setForeground(Color.black);
            }
            else {
               setForeground(Color.black);
            }
         }
         // Mouse is over the atom 2D
         else {
            if(lvw.getLastSelected().equals(this)) {
               setBackground(Color.blue);
               setForeground(Color.white);
            }
            else if (lvw.getBeforeLastSelected().equals(this)) {
               setBackground(new Color(200,0,255));
               setForeground(Color.white);
            }
            else if (lvw.getRootAtom().equals(this)) {
               setBackground(Color.green);
               setForeground(Color.black);
            }
            else {
               setBackground(Color.black);
               setForeground(Color.white);
            }
            interior = getBackground();
         
         // ***** determine what colors to use
            if(pressed) {
               highlight1 = interior.darker();
               highlight2 = interior.brighter();
            } 
            else {
               highlight1 = interior.brighter();
               highlight2 = interior.darker();
            }
         
         // ***** paint the interior of the button
            g.setColor(interior);
         // left cap
            g.fillArc(0, 0,                // start
               capWidth, height,           // size
               90, 180);                   // angle
         
         // right cap
            g.fillArc(width - capWidth, 0, // start
               capWidth, height,           // size
               270, 180);                  // angle
         
         // inner rectangle
            g.fillRect(capWidth/2, 0, width - capWidth, height);
         
         
         // ***** highlight the perimeter of the button
         // draw upper and lower highlight lines
            g.setColor(highlight1);
            g.drawLine(capWidth/2, 0, width - capWidth/2, 0);
            g.setColor(highlight2);
            g.drawLine(capWidth/2, height, width - capWidth/2, height);
         
         // upper arc left cap
            g.setColor(highlight1);
            g.drawArc(0, 0,                // start
               capWidth, height,           // size
               90, 180-40                  // angle
               );
         
         // lower arc left cap
            g.setColor(highlight2);
            g.drawArc(0, 0,                // start
               capWidth, height,           // size
               270-40, 40                  // angle
               );
         
         // upper arc right cap
            g.setColor(highlight1);
            g.drawArc(width - capWidth, 0, // start
               capWidth, height,           // size
               90-40, 40                   // angle
               );
         
             // lower arc right cap
            g.setColor(highlight2);
            g.drawArc(width - capWidth, 0, // start
               capWidth, height,           // size
               270, 180-40                 // angle
               );
         }
      // ***** draw the label centered in the button
         Font f = getFont();
         if(f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            g.setColor(getForeground());
            g.drawString(label,
               width/2 - fm.stringWidth(label)/2,
               height/2 + fm.getHeight()/2 - fm.getMaxDescent()
               );
         }
      
      }
   
   
   /**
     * The preferred size of the Atom2d. 
     * @return the preferred size
   **/
      public Dimension getPreferredSize() {
         Font f = getFont();
         if(f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            return new Dimension(fm.stringWidth(label) + capWidth*2,
               fm.getHeight()+10);
         } 
         else {
            return new Dimension(50, 25);
         }
      }
   
   /**
     * The minimum size of the Atom2d.
     * @return the minimum size 
   **/
      public Dimension getMinimumSize() {
         return new Dimension(GAPX, GAPY);
      }
   
   /**
     * Adds the specified action listener to receive action events
     * from this Atom2D.
     * @param listener the action listener
   **/
      public void addActionListener(ActionListener listener) {
         actionListener = AWTEventMulticaster.add(actionListener, listener);
         enableEvents(AWTEvent.MOUSE_EVENT_MASK);
      }
   
   /**
     * Removes the specified action listener so it no longer receives
     * action events from this Atom2D.
     * @param listener the action listener
   **/
      public void removeActionListener(ActionListener listener) {
         actionListener = AWTEventMulticaster.remove(actionListener, listener);
      }
   
   /**
     * Paints the button and sends an action event to all listeners.
     * Implements drag and drop behavior.
     * @param e The event catched
   **/
      public void processMouseEvent(MouseEvent e) {
         Graphics g;
         LewisViewWindow lvw = (LewisViewWindow) getParent();
         switch(e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
               // render myself inverted....
               pressed = true;
            
               // Repaint might flicker a bit. To avoid this, you can use
               // double buffering (see the Gauge example).
               setCursor(new Cursor(Cursor.HAND_CURSOR));
               lvw.setBeforeLastSelected();
               lvw.setLastSelected(this);
               lvw.repaint();
               repaint();
               break;
            case MouseEvent.MOUSE_RELEASED:
               if(actionListener != null) {
                  actionListener.actionPerformed(new ActionEvent(
                     this, ActionEvent.ACTION_PERFORMED, label));
               }
               // render myself normal again
               if(pressed == true) {
                  pressed = false;
               
               // Repaint might flicker a bit. To avoid this, you can use
               // double buffering (see the Gauge example).
               // Moves the Atom2D
                  if( (Math.abs(e.getX()) >= (getSize().width) ) || (Math.abs(e.getY()) >= (getSize().height))) {
                     int x = e.getX()+((int)getLocation().getX());
                     int y = e.getY()+((int)getLocation().getY());
                     x = x - x % GAPX;
                     y = y - y % GAPY;
                     this.setLocation(x , y );
                     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                  }
                  repaint(); // repaint the Atom2D
                  lvw.repaint(); // repaint the lewis view window
               
               }
               break;
         
            case MouseEvent.MOUSE_ENTERED:
               mouseOver=true;
               repaint();
               lvw.repaint();
               break;
            case MouseEvent.MOUSE_EXITED:
               if (pressed==false) {
                  mouseOver=false;
               }
               repaint();
               lvw.repaint();
               break;
         }
         super.processMouseEvent(e);
      }
   
   } 

