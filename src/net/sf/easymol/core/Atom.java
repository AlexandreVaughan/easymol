/*
 * Created on 17 oct. 2004
 * 
 * Atom.java - The description of an atom in EasyMol
 * Copyright (c) 2004 Alexandre Vaughan
 * avaughan@altern.org
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
package net.sf.easymol.core;

/**
 * The description of an atom in EasyMol.
 * <p>
 * An atom is a special type of chemical compound that cannot have any
 * subcompounds (it is a leaf in the compound tree). It also has specific
 * chemical properties :
 * <ul>
 * <li>the <b>valency </b> is used to create valency bonds between two atoms
 * </li>
 * <li>the <b>satisfied valency </b> indicates the number of bonds already
 * existing to this atom</li>
 * <li>the <b>number of electron pairs </b> indicates how many electron pairs
 * are present on the last layer of this atom</li>
 * </ul>
 * </p>
 * <p>
 * Atoms can be cloned using the copy constructor so that atoms already existing
 * in the system don't have to be recreated. Basically, an atom is created like
 * this :
 * 
 * <pre>
 * Atom myNitrogen = new Atom(Atom.N); // we create a new atom using the nitrogen atom constant
 * 
 * 
 * </pre>
 * 
 * </p>
 * 
 * @see AbstractChemicalCompound
 * @author avaughan
 */
public class Atom extends AbstractChemicalCompound {
    
    public static final int COORD_X = 0;
    public static final int COORD_Y = 1;
    public static final int COORD_Z = 2;

    private int valency = 0; // valency of this atom

    private int satisfiedValency = 0; // already satisfied valency

    private int nbElectronPairs = 0; // number of electron pairs

    /** Maximum valency for an atom */
    public static final int MAX_VALENCY = 4;

    //---------------------------------------------------------------------
    //                 Table of elements (to be completed)
    //---------------------------------------------------------------------
    /** Hydrogen */
    public static final Atom H = new Atom("Hydrogen", "H", 1, 0);

    /** Carbon */
    public static final Atom C = new Atom("Carbon", "C", 4, 0);

    /** Oxygen */
    public static final Atom O = new Atom("Oxygen", "O", 2, 2);

    /** Nitrogen */
    public static final Atom N = new Atom("Nitrogen", "N", 3, 1);
    
    /** Sulphur */
    public static final Atom S = new Atom("Sulphur", "S",2,2);
    
    /** Phosphorus*/
    public static final Atom P = new Atom("Phosphorus", "P",5,1);

    //---------------------------------------------------------------------

    /**
     * Constructs a new atom
     * 
     * @param name
     *            name of the atom
     * @param symbol
     *            symbol of the atom
     * @param valency
     *            valency of the atom
     * @param electronPairs
     *            number of electron pairs for this atom
     */
    public Atom(String name, String symbol, int valency, int electronPairs) {
        super();
        this.setName(name);
        this.setSymbol(symbol);
        this.setValency(valency);
        this.setNbElectronPairs(electronPairs);
    }

    /**
     * Default constructor for an atom
     * 
     *  
     */
    public Atom() {
        super();
    }

    /**
     * Copy constructor for an atom
     * 
     * @param that
     *            the atom to copy
     */
    public Atom(Atom that) {
        super();
        this.setUniqueId(that.getUniqueId());
        this.setName(that.getName());
        this.setSymbol(that.getSymbol());
        this.setValency(that.getValency());
        this.setNbElectronPairs(that.getNbElectronPairs());
    }

    public String toString() {
        return "Name : " + getName()
        + ", Valency : " + getValency()
        + ", Satisfied valency : "
        + getSatisfiedValency();
    }

    /**
     * Increments the satisfied valency of this atom. Called by ValencyBond
     * 
     * @see ValencyBond
     * @throws EasyMolException
     *             if the valency is already fully satisfied
     */
    public void incrementSatisfiedValency() throws EasyMolException {
        if (satisfiedValency < valency)
            satisfiedValency++;
        else
            throw new EasyMolException(101, "Atom " + this.getSymbol(), "Atom "
                    + this.getSymbol() + " valency is already satisfied");
    }

    /**
     * Decrements the satisfied valency of this atom. Called by ValencyBond
     * 
     * @see ValencyBond
     */
    public void decrementSatisfiedValency() {
        if (satisfiedValency > 0)
            satisfiedValency--;
    }

    /**
     * Gets the number of electron pairs for this atom.
     * 
     * @return Returns the nbElectronPairs.
     */
    public int getNbElectronPairs() {
        return nbElectronPairs;
    }

    /**
     * Sets the number of electron pairs for this atom.
     * 
     * @param nbElectronPairs
     *            The nbElectronPairs to set.
     */
    public void setNbElectronPairs(int nbElectronPairs) {
        this.nbElectronPairs = nbElectronPairs;
    }

    /**
     * Gets the valency of this atom.
     * 
     * @return Returns the valency.
     */
    public int getValency() {
        return valency;
    }

    /**
     * Sets the valency of this atom.
     * 
     * @param valency
     *            The valency to set.
     */
    public void setValency(int valency) {
        if (valency <= MAX_VALENCY && valency > 0)
            this.valency = valency;
        else
            this.valency = MAX_VALENCY;
    }

    /**
     * Gets the satisfied valency of this atom
     * 
     * @return the staisfied valency
     */
    public int getSatisfiedValency() {
        return satisfiedValency;
    }

    public boolean canBond() {
        return (getSatisfiedValency() < getValency());
    }
}