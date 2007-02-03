/*
 * Created on 31 oct. 2004
 *
 */
package fr.emn.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import fr.emn.easymol.ui.components.Molecule2DPane;
import fr.emn.easymol.ui.components.IconProvider;

/**
 * @author avaughan
 *  
 */
public class RemoveAction extends AbstractAction {

    private Molecule2DPane moleculePane = null;

    public RemoveAction(Molecule2DPane pane) {
        moleculePane = pane;
        ImageIcon icon = IconProvider.getInstance().getIconByName("removeAtom");
        putValue(Action.SMALL_ICON, icon);
        putValue(Action.NAME, "Remove Selection");
    }

    public void actionPerformed(ActionEvent e) {
        moleculePane.remove();
    }

    public Molecule2DPane getMoleculePane() {
        return moleculePane;
    }
}
