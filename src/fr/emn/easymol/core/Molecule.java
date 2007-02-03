/*
 * Created on 17 oct. 2004
 *
 * 
 */
package fr.emn.easymol.core;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * A class describing a molecule
 * @author avaughan
 *  
 */
public class Molecule extends AbstractChemicalCompound {

    private Vector compounds = new Vector(); // of type AbstractChemicalCompound

    // (composite pattern)
    private Vector bonds = new Vector(); // of type AbstractChemicalBond
    /**
     * Creates a new molecule
     */
    public Molecule() {
        super();
    }
    /**
     * Adds a new compound to the molecule.
     * @param acc A compound to add @see AbstractChemicalCompound
     * @return The number of compounds - 1 
     */
    public int addCompound(AbstractChemicalCompound acc) {
        compounds.add(acc);
        //notifyObservers();
        return compounds.size() - 1;
    }
    /**
     * Removes a compound from the molecule.
     * @param acc Compound to remove
     */
    public void removeCompound(AbstractChemicalCompound acc) {
        //AbstractChemicalCompound acc = (AbstractChemicalCompound)
        // compounds.elementAt(index);
        int currentIdx = 0;
        for (Enumeration e = getBonds(); e.hasMoreElements();) {
            AbstractChemicalBond jamesBond = (AbstractChemicalBond) e
                    .nextElement();
            if (jamesBond.getFirst().equals(acc)
                    || jamesBond.getSecond().equals(acc))
                removeBond(currentIdx);
            currentIdx++;
        }
        compounds.remove(acc);
        notifyObservers();
    }
    /**
     * Removes a compound based on specified index.
     * @param index The removed compounds index.
     */
    public void removeCompound(int index) {
        AbstractChemicalCompound acc = (AbstractChemicalCompound) compounds
                .elementAt(index);
        removeCompound(acc);
    }
    /**
     * Adds a bond to the molecule.
     * @param jamesBond The bond to add.
     * @return the number of bonds - 1 
     */
    public int addBond(AbstractChemicalBond jamesBond) {
        bonds.add(jamesBond);
        notifyObservers();
        return bonds.size() - 1;
    }
    /**
     * Removes an user specified bond.
     */
    public void removeBond(AbstractChemicalBond jamesBond) {
        //AbstractChemicalBond jamesBond = (AbstractChemicalBond)
        // bonds.elementAt(index);
        jamesBond.cleanupBond();
        bonds.remove(jamesBond);
        notifyObservers();
    }
    /**
     * Removes a bond from specified index.
     */
    public void removeBond(int index) {
        removeBond((AbstractChemicalBond) bonds.elementAt(index));
    }
    /**
     * Gets the bonds of this molecule.
     * 
     * @return An enumeration of the bonds on this molecule, if any.
     */
    public Enumeration getBonds() {
        //TODO Don't return anything when no bonds are found.
    	try {
        return bonds.elements();
    	}
    	catch (NoSuchElementException nsee)
		{
        	return null;
        }
    }
    /**
     * Gets the compounds of this molecule
     * 
     * @return An enumeration of the compounds, if any.
     */
    public Enumeration getCompounds() {
    	
        //TODO Don't return anything when no compounds are found.
        try {
    	return compounds.elements();
        }
        catch (NoSuchElementException nsee)
		{
        	return null;
        }
    }
    /**
     * Transforms this molecule to a String
     * @return A string describing this molecule. 
     *
     */
    public String toString() {
        String s = super.toString() + "\nSubcompounds :  [ ";
        for (Enumeration e = getCompounds(); e.hasMoreElements();) {
            AbstractChemicalCompound current = (AbstractChemicalCompound) e
                    .nextElement();
            s += current.getSymbol();
            if (e.hasMoreElements())
                s += ", ";
        }
        s += " ]\n";
        s += "Bonds : [ ";
        for (Enumeration e = getBonds(); e.hasMoreElements();) {
            AbstractChemicalBond current = (AbstractChemicalBond) e
                    .nextElement();
            s += current.toString();
            if (e.hasMoreElements())
                s += ", ";
        }
        s += " ]";
        return s;
    }
    /**
     * Gets the current compound of this molecule
     * @return A chemical compound.
     */
    public AbstractChemicalCompound getCompound(int index) {
        return (AbstractChemicalCompound) compounds.elementAt(index);
    }
    /**
     * Gets the compound for a given name.
     * 
     * @param name
     * @return The compound that has the given name
     * TODO: Add compliance for molecules, too.
     */
    public AbstractChemicalCompound getCompoundForName(String name)
    {
    	Enumeration e = compounds.elements();
    	while (e.hasMoreElements())
    	{
    		AbstractChemicalCompound acc = (Atom)e.nextElement();
    		if (((Atom)acc).getName().equals(name))
    		{
    			return acc;
    		}
    	}
    	return null;
    }
    /**
     * Adds a valency bond to this molecule
     * @return The number of bonds -1
     */
    public int addValencyBond(int atom1, int atom2, int bondType)
            throws EasyMolException {
        AbstractChemicalCompound a1 = getCompound(atom1);
        AbstractChemicalCompound a2 = getCompound(atom2);
        if (!(a1 instanceof Atom) || !(a2 instanceof Atom))
            throw new EasyMolException(111, "Molecule " + getSymbol(),
                    "Either " + a1.getSymbol() + " or " + a2.getSymbol()
                            + " is not an atom.");
        ValencyBond jamesBond = new ValencyBond((Atom) a1, (Atom) a2, bondType);
        return addBond(jamesBond);
    }
    /**
     * Returns an index of an user specified compound
     * @return The index of the specified compound.
     */
    public int getIndex(AbstractChemicalCompound acc) {
        return compounds.indexOf(acc);
    }
    /** Returns the number of compounds contained in this molecule
     * 
     * @author ava
     *
     */
    public int getNbCompounds() {
        return compounds.size();
    }
    /** Returns the number of bonds in this molecule
     * 
     * @author ava
     *
     */
    public int getNbBonds() {
        return bonds.size();
    }
    

}