/*
 * Created on 24 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import fr.emn.easymol.core.ValencyBond;
import fr.emn.easymol.ui.components.ChemicalCompoundGraphCell;
import fr.emn.easymol.ui.components.IconProvider;
import fr.emn.easymol.ui.components.Molecule2DPane;

/**
 * @author avaughan
 *  
 */
public class InsertValencyBondAction extends AbstractAction {

    private ChemicalCompoundGraphCell cell1 = null;

    private ChemicalCompoundGraphCell cell2 = null;

    private Molecule2DPane moleculePane = null;

    private int mode = ValencyBond.SINGLE_BOND;

    public InsertValencyBondAction(Molecule2DPane pane, int mode) {
        String iconName = "";
        String actionString = "";
        this.mode = mode;
        switch (mode) {
        case ValencyBond.SINGLE_BOND:
            iconName = "singleLink";
            actionString = "Insert Single Bond";
            break;
        case ValencyBond.DOUBLE_BOND:
            iconName = "doubleLink";
            actionString = "Insert Double Bond";
            break;
        case ValencyBond.TRIPLE_BOND:
            iconName = "tripleLink";
			actionString = "Insert Triple Bond";
            break;
        }
        
		putValue(Action.NAME, actionString);
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(iconName));

        setMoleculePane(pane);

    }

    public void actionPerformed(ActionEvent e) {
        moleculePane.setValencyBondMode(mode);
        //System.out.println("Switching to mode : "+ mode);
    }

    public Molecule2DPane getMoleculePane() {
        return moleculePane;
    }

    private void setMoleculePane(Molecule2DPane moleculePane) {
        this.moleculePane = moleculePane;
    }

}
