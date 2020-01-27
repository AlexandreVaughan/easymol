/*
 * Created on 17 oct. 2004
 *
 */
package net.sf.easymol.ui.comp3d.vsepr;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import javafx.scene.Group;

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

    public Group getMoleculeGeometry() {
        VSEPRMolecule om = new VSEPRMolecule(molecule.getName());
        HashMap<Integer,Integer> map = new HashMap<>(); 
        Iterator<AbstractChemicalCompound> iterCompounds = molecule.getCompounds();
        if (!iterCompounds.hasNext())
            return new Group();

        // Adding the atoms
        int index = -1;
        int oRoot = 0;
        while (iterCompounds.hasNext()) {
            AbstractChemicalCompound acc = iterCompounds.next();
            index++;
            int type = getOldType(acc.getSymbol());
            int oHash = om.addAtom(type);
            if (oRoot == 0 && type != VSEPRAtom.H)
                oRoot = oHash;
            map.put(index, oHash);
        }

        Iterator<AbstractChemicalBond> iterBonds = molecule.getBonds();
        // Adding the bonds
        while (iterBonds.hasNext()) {
            AbstractChemicalBond acb = iterBonds.next();
            try {
                int oHash1 = map.get(molecule.getIndex(acb.getFirst()));
                int oHash2 = map.get(molecule.getIndex(acb.getSecond()));
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