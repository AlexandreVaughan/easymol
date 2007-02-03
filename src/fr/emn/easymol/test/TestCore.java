/*
 * Created on 17 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.test;

import java.util.Enumeration;

import junit.framework.TestCase;
import fr.emn.easymol.algorithms.SymbolicTranslatorAlgorithm;
import fr.emn.easymol.algorithms.VSEPRAlgorithm;
import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;

/**
 * @author avaughan
 *  
 */
public class TestCore extends TestCase {

    protected Molecule water;

    protected int h1;

    protected int h2;

    protected int o;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        water = new Molecule();
        water.setName("water");
        water.setSymbol("H2O");
        h1 = water.addCompound(new Atom(Atom.H));
        h2 = water.addCompound(new Atom(Atom.H));
        o = water.addCompound(new Atom(Atom.O));
        water.addValencyBond(h1, o, 1);
        water.addValencyBond(h2, o, 1);
    }
    public void testMolecule(){
        int a=1;
        Atom h1Atom = (Atom)water.getCompound(h1);
        Atom h2Atom = (Atom)water.getCompound(h2);
        Atom oAtom = (Atom)water.getCompound(o);
        assertTrue(h1Atom.getSatisfiedValency()==1);
        assertTrue(h2Atom.getSatisfiedValency()==1);
        assertTrue(oAtom.getSatisfiedValency()==2);
    }
    public void testBonds() {
        System.out.println(water);
        VSEPRAlgorithm algo = new VSEPRAlgorithm();
        algo.setData(water);
        assertTrue(algo.getNumberBonded(o) == 2);
        assertTrue(algo.getNumberBonded(h1) == 1);
        assertTrue(algo.getNumberBonded(h1) == 1);
        // TODO : add more cases
    }

    public void testVSEPR() {
        VSEPRAlgorithm algo = new VSEPRAlgorithm();
        algo.setData(water);
        assertTrue(algo.compute() != null);
        // TODO : improve test
    }

    public void testTranslator() {
        SymbolicTranslatorAlgorithm algo = new SymbolicTranslatorAlgorithm();
        algo.setData(water);
        algo.setTranslationType(SymbolicTranslatorAlgorithm.TO_SYMBOL);
        assertTrue(algo.compute().equals("H2O"));
        SymbolicTranslatorAlgorithm algo2 = new SymbolicTranslatorAlgorithm();
        Molecule test = new Molecule();
        test.setName("");
        test.setSymbol("CH3(CH2)6(NH)3COOH");
        algo2.setData(test);
        algo2.setTranslationType(SymbolicTranslatorAlgorithm.TO_COMPOUND);
        System.out.println(algo2.compute());
        for (Enumeration e = test.getCompounds(); e.hasMoreElements();)
            System.out.println(e.nextElement());
        // TODO : improve test
    }
}