/*
 * Created on 1 nov. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.components;

import java.awt.event.MouseEvent;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;

import fr.emn.easymol.core.AbstractChemicalCompound;
import fr.emn.easymol.core.Atom;

/**
 * @author avaughan
 *  
 */
public class MoleculeGraph extends JGraph {
    public MoleculeGraph(GraphModel gm) {
        super(gm);
    }

    public String getToolTipText(MouseEvent e) {
        if (e != null) {
            Object c = this.getFirstCellForLocation(e.getX(), e.getY());
            if (c != null) {
                if (c instanceof ChemicalBondEdge) {
                    return ((ChemicalBondEdge) c).getBond().toString();
                } else if (c instanceof ChemicalCompoundGraphCell) {
                    AbstractChemicalCompound acc = ((ChemicalCompoundGraphCell) c)
                            .getCompound();
                    if (acc instanceof Atom) {
                        return "Name : " + ((Atom) acc).getName()
                                + ", Valency : " + ((Atom) acc).getValency()
                                + ", Satisfied valency : "
                                + ((Atom) acc).getSatisfiedValency();
                    }
                }
            }
        }
        return null;
    }

}