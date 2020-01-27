/*
 * 04/18/2002
 *
 * VSEPRMoleculeNode.java - The atom links modelization for EasyMol
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

package net.sf.easymol.ui.comp3d.vsepr;

import java.util.*; // Contains LinkedList

/**
 * The class for VSEPRMolecule link modelization in the EasyMol software. <br>
 * <br>
 * 
 * This class implements a data structure to create and manipulate links between
 * different atoms (in a molecule for example). The links are in fact a
 * LinkedList containing the atoms (in fact the molecule nodes) linked to this
 * particular molecule node. This LinkedList cannot be longer in size than the
 * molecule node valence. <br>
 * This class extends the VSEPRAtom class and as a consequence a VSEPRMolecule
 * Node is an atom.
 * 
 * @see VSEPRAtom
 * @see VSEPRMolecule
 */
public class VSEPRMoleculeNode extends VSEPRAtom {

    private LinkedList links; // The list containing the links

    /**
     * Builds a new VSEPRMolecule Node and sets its type and valence
     * 
     * @param type
     *            the atom type
     * @param valence
     *            the atom valence
     */
    public VSEPRMoleculeNode(int type, int valence) {
        super(type, valence);
        links = new LinkedList();
    }

    /**
     * Builds a new VSEPRMolecule Node and uses scientific data to set the
     * valence
     * 
     * @param type
     *            the atom type
     */
    public VSEPRMoleculeNode(int type) {
        super(type);
        links = new LinkedList();
    }

    /**
     * Gets the list of links for the current molecule Node
     * 
     * @return the list of links
     */
    public LinkedList getLinks() {
        return links;
    }

    /**
     * Adds a new link to this VSEPRMolecule Node. If the link cannot be added
     * because of valence, false is returned by the method.
     * 
     * @param m
     *            the molecule node to link to
     * @return the method result
     */
    public boolean addLink(VSEPRMoleculeNode m) {
        if (links.size() < getValence()) {
            links.add(m);
            return true;
        } else
            return false;

    }

    /**
     * Removes a link for this molecule Node. If the link cannot be removed, the
     * method returns false.
     * 
     * @param m
     *            the link to remove
     * @return the method result
     */
    public boolean removeLink(VSEPRMoleculeNode m) {
        return links.remove(m);
    }

    // Returns a string representation of the link tree
    // Parent is to avoid circular reference (infinite loop)
    private String linkView(VSEPRMoleculeNode parent) {

        String s = super.toString(); // We get the VSEPRAtom string
        // representation
        for (int i = 0; i < links.size(); i++) { // We browse all links
            if ((VSEPRMoleculeNode) links.get(i) != parent) { // if the link is
                // not
                // the parent
                if (linkType(i) == 1 || i == links.indexOf(links.get(i))) { // if
                    // it
                    // is a
                    // single
                    // link
                    // or
                    // the
                    // first
                    // occurence
                    // of a
                    // multiple
                    // link
                    switch (linkType(i)) {
                    case 1:
                        s += "-";
                        break;
                    case 2:
                        s += "=";
                        break;
                    case 3:
                        s += "#";
                        break;
                    default:
                        s += "";
                        break;
                    }
                    s += ((VSEPRMoleculeNode) links.get(i)).linkView(this); // recursion
                }

            }
        }
        return s;
    }

    /**
     * Returns a linear string representation of the links tree (the whole
     * molecule) The represenattion will be displayed as (for CO <sub>2 </sub>)
     * <code>C=O=O</code>
     * 
     * @return the links tree
     */
    public String linkView() {
        return linkView(null);
    }

    /**
     * Returns the type of the link (single or double or more) for the node at
     * position i in the list of links
     * 
     * @param i
     *            the link position
     * @return the type of the link
     */
    public int linkType(int i) {
        int t = 1;
        VSEPRMoleculeNode currentAtom = (VSEPRMoleculeNode) links.get(i);
        VSEPRMoleculeNode parsedAtom = null;
        for (int j = 0; j < links.size(); j++) {
            parsedAtom = (VSEPRMoleculeNode) links.get(j);
            if (j != i && currentAtom.equals(parsedAtom)) {
                t++;
            }
        }
        return t;
    }

}