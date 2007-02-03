/*
 * Created on 17 oct. 2004
 *
 */
package net.sf.easymol.ui.comp3d.vsepr;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.j3d.BranchGroup;

import net.sf.easymol.core.AbstractChemicalBond;
import net.sf.easymol.core.AbstractChemicalCompound;
import net.sf.easymol.core.Molecule;


/**
 * @author avaughan
 *  
 */
public class VSEPRCoreAdapter {

    private Molecule molecule;

    public VSEPRCoreAdapter() {
        molecule = null;
    }

    public AbstractChemicalCompound getMolecule() {
        return molecule;
    }

    public void setMolecule(AbstractChemicalCompound acc) {
        molecule = (Molecule) acc;
    }



    private int getOldType(String symbol) {
        if (symbol.equals("H"))
            return VSEPRAtom.H;
        if (symbol.equals("O"))
            return VSEPRAtom.O;
        if (symbol.equals("N"))
            return VSEPRAtom.N;
        if (symbol.equals("C"))
            return VSEPRAtom.C;
        if (symbol.equals("P"))
            return VSEPRAtom.P;
        if (symbol.equals("S"))
            return VSEPRAtom.S;
        return -1;
    }

    public BranchGroup getMoleculeGeometry() {
        VSEPRMolecule om = new VSEPRMolecule(molecule.getName());
        Hashtable map = new Hashtable();

        if (!molecule.getCompounds().hasMoreElements())
            return new BranchGroup();

        // Adding the atoms
        int index = -1;
        int oRoot = 0;
        for (Enumeration e = molecule.getCompounds(); e.hasMoreElements();) {
            AbstractChemicalCompound acc = (AbstractChemicalCompound) e
                    .nextElement();
            index++;
            int type = getOldType(acc.getSymbol());
            int oHash = om.addAtom(type);
            if (oRoot == 0 && type != VSEPRAtom.H)
                oRoot = oHash;
            map.put(new Integer(index), new Integer(oHash));
        }

        // Adding the bonds
        for (Enumeration e = molecule.getBonds(); e.hasMoreElements();) {
            AbstractChemicalBond acb = (AbstractChemicalBond) e.nextElement();
            try {
                int oHash1 = ((Integer) map.get(new Integer(molecule
                        .getIndex(acb.getFirst())))).intValue();
                int oHash2 = ((Integer) map.get(new Integer(molecule
                        .getIndex(acb.getSecond())))).intValue();
                int type = acb.getBondStrength();
                om.addLink(oHash1, oHash2, type);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }

        VSEPRMolecule3D m3d = new VSEPRMolecule3D(om, oRoot);
        return m3d.getMol3D();
    }

}