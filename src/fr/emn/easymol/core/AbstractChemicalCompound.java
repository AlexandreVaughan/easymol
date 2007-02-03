/*
 * Created on 17 oct. 2004
 * 
 * AbstractChemicalCompound.java - Abstract description of a chemical compound in EasyMol
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
package fr.emn.easymol.core;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * The abstract description of a chemical compound in EasyMol.
 * <p>
 * This class provides the general structure and operations for every chemical
 * compound in EasyMol. It is the base class of a composite pattern. Thus, a
 * chemical compound can be either a molecule (node of the molecule tree) or an
 * atom (leaf of the molecule tree).
 * </p>
 * <p>
 * Every chemical compound has a name and a symbol (like, for example 'methan'
 * and 'CH4') and a table of user defined properties (useful to store scientific
 * data like melting point etc.). It also implements the observer pattern, so
 * that observers registered to this compound are notified when modifications
 * happen.
 * </p>
 * 
 * @see IChemicalCompoundObserver
 * @see Atom
 * @see Molecule
 * @author Alexandre Vaughan
 */
public abstract class AbstractChemicalCompound {
    private String name = ""; // name of this chemical compound

    private String symbol = ""; // symbol of this chemical compound

    private Hashtable properties = new Hashtable(); // user defined properties

    private Vector observers = new Vector(); // of type
                                             // IChemicalCompoundObserver

    /**
     * Default constructor for AbstractChemicalCompound
     *  
     */
    public AbstractChemicalCompound() {

    }

    /**
     * Sets (or add if it does not exists) a property for this chemical compound
     * 
     * @param property
     *            the name (key) of the property to set
     * @param value
     *            the value of the property to set
     */
    public void setProperty(int property, Object value) {
        properties.put(new Integer(property), value);
    }

    /**
     * Gets a property of this chemical compound
     * 
     * @param property
     *            the name (key) of the property to get
     * @return the value of the property
     */
    public Object getProperty(int property) {
        return properties.get(new Integer(property));
    }

    public String toString() {
        String s = "Compound name   : " + name + "\n" + "Compound symbol : "
                + symbol;
        return s;
    }

    /**
     * Gets the name of this chemical compound
     * 
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this chemical compound
     * 
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the symbol of this chemical compound
     * 
     * @return Returns the symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of this chemical compound
     * 
     * @param symbol
     *            The symbol to set.
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Registers an observer to this chemical compound. The observer will be
     * notified when interesting events happen.
     * 
     * @see IChemicalCompoundObserver
     * @param obs
     *            the observer to add
     */
    public void addObserver(IChemicalCompoundObserver obs) {
        observers.add(obs);
    }

    /**
     * Unregisters an observer of this chemical compound. The observer will no
     * longer be notified of events happening in this compound.
     * 
     * @see IChemicalCompoundObserver
     * @param obs
     *            the observer to remove
     */
    public void removeObserver(IChemicalCompoundObserver obs) {
        observers.remove(obs);
    }

    /**
     * Clears the list of observers for this compound. All the previuously 
     * registered observers will no longer receive event from this compound.
     *
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Notifies the observers of this compound of interesting events.
     * 
     * @see IChemicalCompoundObserver
     */
    public void notifyObservers() {
        for (Enumeration e = observers.elements(); e.hasMoreElements();) {
            ((IChemicalCompoundObserver) e.nextElement()).refresh();
        }
    }
}