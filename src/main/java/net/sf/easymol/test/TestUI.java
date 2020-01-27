/*
 * Created on 21 oct. 2004
 */
package net.sf.easymol.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalLookAndFeel;

import net.sf.easymol.core.Atom;
import net.sf.easymol.core.CoreUtilities;
import net.sf.easymol.core.Molecule;
import net.sf.easymol.ui.comp2d.Molecule2DPane;
import net.sf.easymol.ui.comp3d.vsepr.VSEPRMolecule3DPane;
import net.sf.easymol.ui.general.EasyMolTheme;


/**
 * @author avaughan
 */
public class TestUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Molecule methanol = null;

    private int c = 0;

    private int h1 = 0;

    private int h2 = 0;

    private int h3 = 0;

    private int h4 = 0;

    private int o = 0;

    public TestUI() {

        methanol = new Molecule();
        methanol.setName("Methanol");
        methanol.setSymbol("CH3OH");
        c = methanol.addCompound(new Atom(Atom.C));

        h1 = methanol.addCompound(new Atom(Atom.H));
        h2 = methanol.addCompound(new Atom(Atom.H));
        h3 = methanol.addCompound(new Atom(Atom.H));
        h4 = methanol.addCompound(new Atom(Atom.H));
        o = methanol.addCompound(new Atom(Atom.O));
        try {
            CoreUtilities.addValencyBond(methanol,c, o, 1);
            CoreUtilities.addValencyBond(methanol,h1, o, 1);
            CoreUtilities.addValencyBond(methanol,c, h2, 1);
            CoreUtilities.addValencyBond(methanol,c, h3, 1);
            CoreUtilities.addValencyBond(methanol,h4, c, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setTitle("Test EasyMol UI Components");
        this.setSize(900, 450);
        this.getContentPane().setLayout(new BorderLayout());
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new GridLayout(1, 2));
        mainPane.add(new Molecule2DPane(methanol));
        //mainPane.add(new VSEPRMolecule3DPane(methanol));
        this.getContentPane().add(mainPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MetalLookAndFeel.setCurrentTheme(new EasyMolTheme());
        JFrame.setDefaultLookAndFeelDecorated(true);
        TestUI test = new TestUI();
        test.setVisible(true);
    }
}