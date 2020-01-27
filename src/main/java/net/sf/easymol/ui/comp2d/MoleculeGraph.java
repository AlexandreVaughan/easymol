/*
 * Created on 1 nov. 2004
 */
package net.sf.easymol.ui.comp2d;

import java.awt.event.MouseEvent;

import net.sf.easymol.core.AbstractChemicalCompound;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;


/**
 * @author avaughan
 *  
 */
public class MoleculeGraph extends JGraph {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
                        return acc.toString();
                }
            }
        }
        return null;
    }

}