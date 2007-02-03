/*
 * 04/18/2002
 *
 * VSEPRMolecule.java - The molecule data structure
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

package fr.emn.easymol.algorithms.util;

import java.util.*; // Contains HashTable

/**
 * The class to manipulate Molecules in EasyMol <br>
 * <br>
 * 
 * This class is the core class of the EasyMol software. It implements the data
 * structure to create and manipulate the molecule objects in the program. A
 * molecule is modelized by
 * <ul>
 * <li>A string which is the molecule's name
 * <li>A hash table containing all the molecule nodes
 * </ul>
 * It also provides high level methods to add atoms and links to the molecule,
 * without having to know how molecule nodes and atom are made. <br>
 * The hash code (an integer returned by the addAtom method) of a molecule node
 * is the only thing needed to manipulate it (add or remove link, add or remove
 * atom). <br>
 * For example, creating the water molecule is made like this <br>
 * <br>
 * <code>VSEPRMolecule water = new VSEPRMolecule("Water");<br>
 *       int h1 = water.addAtom(VSEPRAtom.H);<br>
 *       int h2 = water.addAtom(VSEPRAtom.H);<br>
 *       int o = water.addAtom(VSEPRAtom.O);<br>
 *       water.addLink(h1,o);<br>
 *       water.addLink(h2,o);<br>
 * </code><br>
 * <br>
 * The remove methods are used the same way.
 * 
 * @see VSEPRAtom
 * @see VSEPRMoleculeNode
 */
public class VSEPRMolecule {

    private String name; // The name of the molecule (optional)

    private Hashtable nodeTable; // The table containing the nodes of the

    // molecule

    /**
     * Builds a new molecule and sets its name
     * 
     * @param name
     *            the name of the molecule
     */
    public VSEPRMolecule(String name) {
        this.name = name;
        nodeTable = new Hashtable();
    }

    /** Builds a new molecule with no name * */
    public VSEPRMolecule() {
        this("--");
    }

    /**
     * Adds an atom to this molecule (does not add any link to other atoms).
     * <br>
     * The integer in parameter must be a valid atom type (a constant in the
     * class VSEPRAtom, like <code>VSEPRAtom.H</code>).<br>
     * The integer returned is used to manipulate this particular atom in the
     * molecule. <br>
     * 
     * @param type
     *            the atom type (should be a constant in the class VSEPRAtom)
     * @return the atom hash code
     */
    public int addAtom(int type) {
        VSEPRMoleculeNode m = new VSEPRMoleculeNode(type);
        nodeTable.put(new Integer(m.hashCode()), m);
        return m.hashCode();
    }

    /**
     * Adds an atom to this molecule and sets its valence
     * 
     * @see #addAtom(int)
     * @param type
     *            the atom type
     * @param valence
     *            the atom valence
     * @return the atom hashcode
     */
    public int addAtom(int type, int valence) {
        VSEPRMoleculeNode m = new VSEPRMoleculeNode(type, valence);
        nodeTable.put(new Integer(m.hashCode()), m);
        return m.hashCode();
    }

    /**
     * Adds a link for this molecule. The two molecule nodes (referenced by
     * their hash codes) are linked to each other using VSEPRMoleculeNode
     * addLink method. The link type (single or double or more) is set here.
     * 
     * @see VSEPRMoleculeNode
     * @param hashA1
     *            the hash code of the first atom to link
     * @param hashA2
     *            the hash code of the second atom to link
     * @param typeLink
     *            the type of the link (an integer)
     * @return the operation success (true) or failure (false)
     */
    public boolean addLink(int hashA1, int hashA2, int typeLink) {
        VSEPRMoleculeNode a1 = (VSEPRMoleculeNode) nodeTable.get(new Integer(
                hashA1));
        VSEPRMoleculeNode a2 = (VSEPRMoleculeNode) nodeTable.get(new Integer(
                hashA2));
        boolean b = true;
        switch (typeLink) {
        case 1:
            b = b && a1.addLink(a2);
            b = b && a2.addLink(a1);
            break;
        case 2:
            b = b && a1.addLink(a2);
            b = b && a2.addLink(a1);
            b = b && a1.addLink(a2);
            b = b && a2.addLink(a1);
            break;
        case 3:
            b = b && a1.addLink(a2);
            b = b && a2.addLink(a1);
            b = b && a1.addLink(a2);
            b = b && a2.addLink(a1);
            b = b && a1.addLink(a2);
            b = b && a2.addLink(a1);
            break;
        default:
            b = false;
            break;
        }
        return b;
    }

    /**
     * Adds a link for this molecule and sets the link type to single
     * 
     * @see #addLink(int,int,int)
     * @param hashA1
     *            the hash code of the first atom to link
     * @param hashA2
     *            the hash code of the second atom to link
     * @return the operation success (true) or failure (false)
     */
    public boolean addLink(int hashA1, int hashA2) {
        return addLink(hashA1, hashA2, 1);
    }

    /**
     * Removes an atom in this molecule and removes all the links to this atom.
     * 
     * @param hashA1
     *            the hash code for the atom to remove
     * @return the operation success (true) or failure (false)
     */
    public boolean removeAtom(int hashA1) {
        VSEPRMoleculeNode a1 = (VSEPRMoleculeNode) nodeTable.get(new Integer(
                hashA1));
        LinkedList l1 = a1.getLinks();
        boolean b = true;
        for (int i = 0; i < l1.size(); i++) {
            VSEPRMoleculeNode parsedNode = (VSEPRMoleculeNode) l1.get(i);
            b = b && parsedNode.removeLink(a1);
        }
        for (int i = 0; i < l1.size(); i++) {
            VSEPRMoleculeNode parsedNode = (VSEPRMoleculeNode) l1.get(i);
            parsedNode.removeLink(a1);
        }
        for (int i = 0; i < l1.size(); i++) {
            VSEPRMoleculeNode parsedNode = (VSEPRMoleculeNode) l1.get(i);
            parsedNode.removeLink(a1);
        }
        nodeTable.remove(new Integer(hashA1));
        return b;
    }

    /**
     * Removes a link in this molecule.
     * 
     * @param hashA1
     *            the hash code of the first atom in the link
     * @param hashA2
     *            the hash code of the second atom in the link
     * @return the operation success (true) or failure (false)
     */
    public boolean removeLink(int hashA1, int hashA2) {
        VSEPRMoleculeNode a1 = (VSEPRMoleculeNode) nodeTable.get(new Integer(
                hashA1));
        VSEPRMoleculeNode a2 = (VSEPRMoleculeNode) nodeTable.get(new Integer(
                hashA2));
        boolean b = true;
        b = b && a1.removeLink(a2);
        b = b && a2.removeLink(a1);
        a1.removeLink(a2);
        a2.removeLink(a1);
        a1.removeLink(a2);
        a2.removeLink(a1);
        return b;
    }

    /**
     * Gets an atom in the molecule
     * 
     * @param atom
     *            the hash code of the atom
     * @return the atom itself
     */
    public VSEPRMoleculeNode getAtom(int atom) {
        return (VSEPRMoleculeNode) nodeTable.get(new Integer(atom));
    }

    /**
     * Gets the name of the molecule
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the molecule
     * 
     * @param s
     *            the name to set
     */
    public void setName(String s) {
        name = s;
    }

}