/*
 * Created on 24 oct. 2004
 *
 */
package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.sf.easymol.core.Atom;
import net.sf.easymol.ui.comp2d.Molecule2DPane;
import net.sf.easymol.ui.general.IconProvider;


/**
 * @author avaughan
 */
public class InsertAtomAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Atom atom = null;

    private Molecule2DPane moleculePane = null;

    public InsertAtomAction(Molecule2DPane pane, Atom atom) {
        super();
        setAtom(atom);
        setMoleculePane(pane);
        String insertIconName = "";
        if (atom.equals(Atom.H)) {
            insertIconName = "hydrogen";
        } else if (atom.equals(Atom.C)) {
            insertIconName = "carbon";
        } else if (atom.equals(Atom.N)) {
            insertIconName = "nitrogen";
        } else if (atom.equals(Atom.O)) {
            insertIconName = "oxygen";
        }else if (atom.equals(Atom.S)) {
            insertIconName = "sulphur";
        }else if (atom.equals(Atom.P)) {
            insertIconName = "phosphorus";
        } else {
            insertIconName = "insert";
        }
        putValue(Action.NAME, "Insert " + atom.getSymbol());
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                insertIconName));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        moleculePane.insertCompound(new Atom(atom), 20, 20);
    }

    /**
     * @return Returns the compound.
     */
    public Atom getAtom() {
        return atom;
    }

    /**
     * @param compound
     *            The compound to set.
     */
    private void setAtom(Atom compound) {
        this.atom = compound;
    }

    /**
     * @return Returns the compoundPane.
     */
    public Molecule2DPane getMoleculePane() {
        return moleculePane;
    }

    /**
     * @param compoundPane
     *            The compoundPane to set.
     */
    private void setMoleculePane(Molecule2DPane compoundPane) {
        this.moleculePane = compoundPane;
    }
}
