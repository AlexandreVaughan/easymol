/*
 * Created on 22 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.components;

import org.jgraph.graph.DefaultEdge;

import fr.emn.easymol.core.AbstractChemicalBond;

/**
 * @author avaughan
 */
public class ChemicalBondEdge extends DefaultEdge {

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