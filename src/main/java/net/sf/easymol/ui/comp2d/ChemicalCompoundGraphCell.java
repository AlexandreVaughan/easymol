/*
 * Created on 21 oct. 2004
 */
package net.sf.easymol.ui.comp2d;

import net.sf.easymol.core.AbstractChemicalCompound;

import org.jgraph.graph.DefaultGraphCell;


/**
 * @author avaughan
 *  
 */
public class ChemicalCompoundGraphCell extends DefaultGraphCell {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
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