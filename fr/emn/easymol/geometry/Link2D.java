/*
 * 04/18/2002
 *
 * Link2D.java - The 2D Bond representation
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


/** The class to define molecule links for 2D representation.
  * It takes two Atom2D and "links" them
**/
   public class Link2D {
      private Atom2D atom1; // the first atom
      private Atom2D atom2; // the second atom
      private int linkType; // the type of the link
   
   /** Builds a new Link2D
     * @param a1 the first atom of the link
     * @param a2 the second atom of the link
     * @param type the type of the link
   **/
      public Link2D(Atom2D a1, Atom2D a2, int type) {
         setAtom1(a1);
         setAtom2(a2);
         setLinkType(type);
         }
   
   /** Builds a new Link2D with type=1
     * @param a1 the first atom of the link
     * @param a2 the second atom of the link
   **/
      public Link2D (Atom2D a1, Atom2D a2) {
         this(a1, a2, 1);
         }
   
   /** Sets the first atom
     * @param a1 the atom
   **/
      public void setAtom1(Atom2D a1) {
         atom1=a1;
         }
   /** Sets the second atom
     * @param a1 the atom
   **/
      public void setAtom2(Atom2D a2) {
         atom2=a2;
         }
   
   /** Sets the type of the link
     * @param i the type of the link
   **/
      public void setLinkType(int i) {
         linkType = (i <= 3 && i >= 0) ? i : 0;
         }
   
   /** Gets the first atom
     * @return the first atom
   **/
      public Atom2D getAtom1() {
         return atom1;
         }
   
   /** Gets the second atom
     * @return the second atom
   **/
      public Atom2D getAtom2() {
         return atom2;
         }
   
   /** Gets the type of the link
     * @return the type of the link
   **/
      public int getLinkType() {
         return linkType;
         }
      }




