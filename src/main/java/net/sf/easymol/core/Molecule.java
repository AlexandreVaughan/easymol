/*
 * Created on 17 oct. 2004
 *
 * 
 */
package net.sf.easymol.core;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class describing a molecule
 * 
 * @author avaughan
 *  
 */
public class Molecule extends AbstractChemicalCompound {

    private final ArrayList<AbstractChemicalCompound> compounds = new ArrayList<>(); // of type AbstractChemicalCompound

    // (composite pattern)
    private final ArrayList<AbstractChemicalBond> bonds = new ArrayList<>(); // of type AbstractChemicalBond

    /**
     * Creates a new molecule
     */
    public Molecule() {
        super();
    }

    /**
     * Adds a new compound to the molecule.
     * 
     * @param acc
     *            A compound to add
     * @see AbstractChemicalCompound
     * @return The number of compounds - 1
     */
    public int addCompound(AbstractChemicalCompound acc) {
        compounds.add(acc);
        return compounds.size() - 1;
    }

    /**
     * Removes a compound from the molecule.
     * 
     * @param acc
     *            Compound to remove
     */
    public void removeCompound(AbstractChemicalCompound acc) {
        //AbstractChemicalCompound acc = (AbstractChemicalCompound)
        // compounds.elementAt(index);
        int currentIdx = 0;
        for (AbstractChemicalBond jamesBond : bonds)
        {
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
     * 
     * @param index
     *            The removed compounds index.
     */
    public void removeCompound(int index) {
        AbstractChemicalCompound acc =  compounds.get(index);
        removeCompound(acc);
    }

    /**
     * Adds a bond to the molecule.
     * 
     * @param jamesBond
     *            The bond to add.
     * @return the number of bonds - 1
     */
    public int addBond(AbstractChemicalBond jamesBond) {
        bonds.add(jamesBond);
        notifyObservers();
        return bonds.size() - 1;
    }

    /**
     * Removes an user specified bond.
     * @param jamesBond
     *            The bond to remove.
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
     * @param index index of the bond
     */
    public void removeBond(int index) {
        removeBond(bonds.get(index));
    }

    /**
     * Gets the bonds of this molecule.
     * 
     * @return An enumeration of the bonds on this molecule, if any.
     */
    public Iterator<AbstractChemicalBond> getBonds() {
        return bonds.iterator();
    }

    /**
     * Gets the compounds of this molecule
     * 
     * @return An enumeration of the compounds, if any.
     */
    public Iterator<AbstractChemicalCompound> getCompounds() {
        return compounds.iterator();
    }

    /**
     * Transforms this molecule to a String
     * 
     * @return A string describing this molecule.
     *  
     */
    public String toString() {
        String s = super.toString() + "\nSubcompounds :  [ ";
        Iterator<AbstractChemicalCompound> iterComp = getCompounds();
        while(iterComp.hasNext())
        {
            AbstractChemicalCompound current =  iterComp.next();
            s += current.getSymbol();
            if (iterComp.hasNext())
                s += ", ";
        }

        s += " ]\n";
        s += "Bonds : [ ";
        Iterator<AbstractChemicalBond> iterBonds = getBonds();
        while(iterBonds.hasNext())
        {
            AbstractChemicalBond current =  iterBonds.next();
            s += current.toString();
            if (iterComp.hasNext())
                s += ", ";
        }
        s += " ]";
        return s;
    }

    /**
     * Gets the current compound of this molecule
     * 
     * @return A chemical compound.
     */
    public AbstractChemicalCompound getCompound(int index) {
        return compounds.get(index);
    }
    public AbstractChemicalCompound getLastCompound() {
        if (compounds.isEmpty()) {
            return null;
        }
        
    	return compounds.get(compounds.size()-1); 
    }
    public AbstractChemicalCompound getFirstCompound() {
        if (compounds.isEmpty()){
            return null;
        }
    	return (AbstractChemicalCompound) compounds.get(0); 
    }
    /**
     * Gets the compound for a given name.
     * 
     * @param uniqueId
     * @return The compound that has the given name
     */
    public AbstractChemicalCompound getCompoundByUniqueId(String uniqueId) {
        
        for (AbstractChemicalCompound acc : compounds) {
            if ((acc.getUniqueId()).equals(uniqueId)) {
                return acc;  
            }
        }
        return null;
    }


    /**
     * Returns an index of an user specified compound
     * 
     * @param acc compound
     * @return The index of the specified compound.
     */
    public int getIndex(AbstractChemicalCompound acc) {
        return compounds.indexOf(acc);
    }

    /**
     * Returns the number of compounds contained in this molecule
     * 
     * @author ava
     *  
     */
    public int getNbCompounds() {
        return compounds.size();
    }

    /**
     * Returns the number of bonds in this molecule
     * 
     * @author ava
     *  
     */
    public int getNbBonds() {
        return bonds.size();
    }

    public boolean canBond() {
        for (AbstractChemicalCompound acc: compounds) {
            if (acc.canBond())
                return true;
        }
        return false;
    }

}