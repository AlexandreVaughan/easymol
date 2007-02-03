/*
 * Created on 17 oct. 2004
 *
 * TODO Maintain tests
 */
package net.sf.easymol.test;

import junit.framework.TestCase;
import net.sf.easymol.core.Atom;
import net.sf.easymol.core.CoreUtilities;
import net.sf.easymol.core.Molecule;
import net.sf.easymol.ui.comp3d.vsepr.VSEPRCoreAdapter;

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
        CoreUtilities.addValencyBond(water,h1, o, 1);
        CoreUtilities.addValencyBond(water,h2, o, 1);
    }

    public void testMolecule() {
        //int a = 1;
        Atom h1Atom = (Atom) water.getCompound(h1);
        Atom h2Atom = (Atom) water.getCompound(h2);
        Atom oAtom = (Atom) water.getCompound(o);
        assertTrue(h1Atom.getSatisfiedValency() == 1);
        assertTrue(h2Atom.getSatisfiedValency() == 1);
        assertTrue(oAtom.getSatisfiedValency() == 2);
    }

    public void testVSEPR() {
        VSEPRCoreAdapter algo = new VSEPRCoreAdapter();
        algo.setMolecule(water);
        assertTrue(algo.getMoleculeGeometry() != null);
    }

}