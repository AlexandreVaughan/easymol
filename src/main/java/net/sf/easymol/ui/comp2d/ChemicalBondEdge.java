/*
 * Created on 22 oct. 2004
 *
 */
package net.sf.easymol.ui.comp2d;

import net.sf.easymol.core.AbstractChemicalBond;

import org.jgraph.graph.DefaultEdge;


/**
 * @author avaughan
 */
public class ChemicalBondEdge extends DefaultEdge {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private AbstractChemicalBond bond = null;

    /**
     *  
     */
    public ChemicalBondEdge(AbstractChemicalBond bond) {
        super();
        this.setBond(bond);
    }

    /**
     * @param arg0
     */
    public ChemicalBondEdge(AbstractChemicalBond bond, Object arg0) {
        super(arg0);
        this.setBond(bond);
    }

    /**
     * @return Returns the bond.
     */
    public AbstractChemicalBond getBond() {
        return bond;
    }

    /**
     * @param bond
     *            The bond to set.
     */
    public void setBond(AbstractChemicalBond bond) {
        this.bond = bond;
    }
}