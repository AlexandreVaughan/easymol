/*
 * Created on 24 oct. 2004
 *
 */
package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.sf.easymol.core.ValencyBond;
import net.sf.easymol.ui.comp2d.Molecule2DPane;
import net.sf.easymol.ui.general.IconProvider;


/**
 * @author avaughan
 *  
 */
public class InsertValencyBondAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    //private ChemicalCompoundGraphCell cell1 = null;

    //private ChemicalCompoundGraphCell cell2 = null;

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
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                iconName));

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
