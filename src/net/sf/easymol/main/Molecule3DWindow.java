/*
 * Created on 1 nov. 2004
 *
 */
package net.sf.easymol.main;

import javax.swing.JFrame;

import net.sf.easymol.ui.comp3d.vsepr.VSEPRMolecule3DPane;


/**
 * @author avaughan
 */
public class Molecule3DWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private VSEPRMolecule3DPane pane = null;

    public Molecule3DWindow(VSEPRMolecule3DPane myPane) {
        pane = myPane;
        this.setTitle(pane.getMolecule().getName() + " VSEPR View");
        this.setSize(300, 300);
        this.getContentPane().add(pane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}