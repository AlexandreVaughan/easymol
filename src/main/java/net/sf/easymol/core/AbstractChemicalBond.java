/*
 * Created on 17 oct. 2004
 * 
 * AbstractChemicalBond.java - Abstract description of a chemical bond in EasyMol
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

import java.util.HashMap;


/**
 * The abstract description of a chemical bond in EasyMol.
 * <p>
 * A bond is basically a pair of two chemical compounds, with some user defined
 * properties.
 * </p>
 * 
 * @see ValencyBond
 * @see AbstractChemicalCompound
 * @author Alexandre Vaughan
 *  
 */
public abstract class AbstractChemicalBond {

    private AbstractChemicalCompound first = null; // first compound of this

    // bond

    private AbstractChemicalCompound second = null; // second compound of this

    // bond

    private HashMap<Integer,Object> properties = null; // properties of this bond

    /**
     * Constructs a new chemical bond
     * 
     * @param first
     *            first compound of this bond
     * @param second
     *            second compound of this bond
     */
    public AbstractChemicalBond(AbstractChemicalCompound first,
            AbstractChemicalCompound second) {
        setFirst(first);
        setSecond(second);
        properties = new HashMap<Integer,Object>();
    }

    /**
     * Translates this bond to string.
     *  
     */
    public String toString() {
    	if (first != null && second != null)
    	{
        return first.getSymbol() + "<->" + second.getSymbol();
    	}
    	else if (first==null && second !=null){
    		return "null<->"+second.getSymbol();
    		
    	} else if (first != null && second==null)
    	{
    		return first.getSymbol()+"<->null";
    	}
    	else    	
    	{
    		return "null<->null";
    	}
    }

    /**
     * Sets (or adds if it does not exists) a property for this chemical bond
     * 
     * @param property
     *            the name (key) of the property to set
     * @param value
     *            the value of the property to set
     */
    public void setProperty(int property, Object value) {
        properties.put(property, value);
    }

    /**
     * Gets a property of this chemical bond
     * 
     * @param property
     *            the name (key) of the property to get
     * @return the value of the property
     */
    public Object getProperty(int property) {
        return properties.get(property);
    }

    /**
     * Gets the first compound of this bond
     * 
     * @return Returns the first.
     */
    public AbstractChemicalCompound getFirst() {
        return first;
    }

    /**
     * Sets the first compound of this bond
     * 
     * @param first
     *            The first to set.
     */
    public void setFirst(AbstractChemicalCompound first) {
        this.first = first;
    }

    /**
     * Gets the second compound of this bond
     * 
     * @return Returns the second.
     */
    public AbstractChemicalCompound getSecond() {
        return second;
    }

    /**
     * Sets the second compound of this bond
     * 
     * @param second
     *            The second to set.
     */
    public void setSecond(AbstractChemicalCompound second) {
        this.second = second;
    }

    /**
     * Cleanups this bond.
     *  
     */
    public abstract void cleanupBond();
    
    public abstract int getBondStrength();
}