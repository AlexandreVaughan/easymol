/*
 * 04/18/2002
 *
 * VSEPRAtom.java - The atom modelization for EasyMol
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

/**
 * The class for VSEPRAtom Modelization in the EasyMol software <br>
 * <br>
 * 
 * This class provides a simple API to manipulate VSEPRAtom objects. It
 * modelizes an atom using its type and its valence. The type of the atome is an
 * integer (the most common atoms are implemented using the constants
 * <code>H,C,O,N ...
 * </code>). There is also a special constructor to
 * automatically set the valence of the atom knowing its type. So an atom can be
 * created by <br>
 * <br>
 * <code>VSEPRAtom a = new VSEPRAtom(VSEPRAtom.H)</code><br>
 * <br>
 * 
 * @see VSEPRMoleculeNode
 * @see VSEPRMolecule
 */
public class VSEPRAtom {

    // Data structure
    private int type; // The atom type

    private int valence; // The atom valence

    /** The Hydrogen atom * */
    public static final int H = 1;

    /** The Carbon atom * */
    public static final int C = 2;

    /** The Oxygen atom * */
    public static final int O = 3;

    /** The Nitrogen VSEPRAtom * */
    public static final int N = 4;
    
    /** The Sulphur atom * */
    public static final int S = 5;
    /** The Phosphorus atom * */
    public static final int P = 6;

    /**
     * Builds a new VSEPRAtom object, setting its type and valence
     * 
     * @param type
     *            the atom type
     * @param valence
     *            the atom valence
     */
    public VSEPRAtom(int type, int valence) {
        setType(type);
        setValence(valence);
    }

    /**
     * Builds a new VSEPRAtom object and sets the valence using the atom type
     * 
     * @param type
     *            the atom type
     */
    public VSEPRAtom(int type) {
        setType(type);
        switch (type) {
        case VSEPRAtom.H:
            setValence(1);
            break;
        case VSEPRAtom.C:
            setValence(4);
            break;
        case VSEPRAtom.O:
            setValence(2);
            break;
        case VSEPRAtom.N:
            setValence(3);
            break;
        case VSEPRAtom.S:
            setValence(2);
            break;
        case VSEPRAtom.P:
            setValence(3);
            break;
        default:
            setValence(0);
            break;
        }
    }

    /**
     * Sets the type of the current atom
     * 
     * @param type
     *            the atom type
     */

    public void setType(int type) {
        if (type > 0 && type <= 6) {
            this.type = type;
        } else {
            System.err.println("Error : VSEPRAtom type does not exist");
            System.exit(0);
        }
    }

    /**
     * Sets the valence of the current atom
     * 
     * @param valence
     *            the atom valence
     */

    public void setValence(int valence) {
        if (valence >= 0 && valence <= 4) {
            this.valence = valence;
        } else {
            System.err.println("Error : Valence value must be in [0,4]");
            System.exit(0);
        }
    }

    /**
     * Gets the valence of the atom
     * 
     * @return the atom valence
     */
    public int getValence() {
        return this.valence;
    }

    /**
     * Gets the type of the atom
     * 
     * @return the atom type
     */
    public int getType() {
        return this.type;
    }

    /**
     * Creates a string representation of the current atom (its chemical symbol
     * in the Mendeleiev table)
     * 
     * @return the string representation
     */
    public String toString() {
        String s = "";
        switch (getType()) {
        case VSEPRAtom.H:
            s += "H";
            break;
        case VSEPRAtom.C:
            s += "C";
            break;
        case VSEPRAtom.O:
            s += "O";
            break;
        case VSEPRAtom.N:
            s += "N";
            break;
        case VSEPRAtom.S:
            s += "S";
            break;
        case VSEPRAtom.P:
            s += "P";
            break;
        default:
            s += "Unknown";
            break;
        }
        return s;
    }

    /**
     * Returns the atom type, knowing its Mendeleiv symbol
     * 
     * @param s
     *            the atom symbol
     * @return the atom type
     */
    public static int getAtomType(String s) {
        if (s.equals("H")) {
            return VSEPRAtom.H;
        } else if (s.equals("C")) {
            return VSEPRAtom.C;
        } else if (s.equals("O")) {
            return VSEPRAtom.O;
        } else if (s.equals("N")) {
            return VSEPRAtom.N;
        }else if (s.equals("S")) {
            return VSEPRAtom.S;
        }else if (s.equals("P")) {
            return VSEPRAtom.P;
        } else {
            return 0;
        }
    }

    /** Prints the atom on standard output * */
    public void print() {
        System.out.println(toString());
    }

}
