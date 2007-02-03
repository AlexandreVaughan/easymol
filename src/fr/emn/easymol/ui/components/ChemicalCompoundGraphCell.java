/*
 * Created on 21 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.components;

import org.jgraph.graph.DefaultGraphCell;

import fr.emn.easymol.core.AbstractChemicalCompound;

/**
 * @author avaughan
 *  
 */
public class ChemicalCompoundGraphCell extends DefaultGraphCell {

    private AbstractChemicalCompound compound = null;

    /**
     *  
     */
    public ChemicalCompoundGraphCell(AbstractChemicalCompound compound) {
        super();
        this.setCompound(compound);
    }

    /**
     * @param arg0
     */
    public ChemicalCompoundGraphCell(AbstractChemicalCompound compound,
            Object arg0) {
        super(arg0);
        this.setCompound(compound);
    }


    /**
     * @return Returns the compound.
     */
    public AbstractChemicalCompound getCompound() {
        return compound;
    }

    /**
     * @param compound
     *            The compound to set.
     */
    public void setCompound(AbstractChemicalCompound compound) {
        this.compound = compound;
    }
}