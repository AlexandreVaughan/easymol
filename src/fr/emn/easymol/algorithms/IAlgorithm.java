/*
 * Created on 17 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.algorithms;

import fr.emn.easymol.core.AbstractChemicalCompound;

/**
 * @author avaughan
 *  
 */
public interface IAlgorithm {

    public abstract AbstractChemicalCompound getData();

    public abstract void setData(AbstractChemicalCompound acc);

    public abstract Object compute();

}