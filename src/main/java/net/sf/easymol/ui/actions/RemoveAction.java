/*
 * Created on 31 oct. 2004
 *
 */
package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import net.sf.easymol.ui.comp2d.Molecule2DPane;
import net.sf.easymol.ui.general.IconProvider;


/**
 * @author avaughan
 *  
 */
public class RemoveAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Molecule2DPane moleculePane = null;

    public RemoveAction(Molecule2DPane pane) {
        moleculePane = pane;
        ImageIcon icon = IconProvider.getInstance().getIconByName("removeAtom");
        putValue(Action.SMALL_ICON, icon);
        putValue(Action.NAME, "Remove Selection");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
    }

    public void actionPerformed(ActionEvent e) {
        moleculePane.remove();
    }

    public Molecule2DPane getMoleculePane() {
        return moleculePane;
    }
}
