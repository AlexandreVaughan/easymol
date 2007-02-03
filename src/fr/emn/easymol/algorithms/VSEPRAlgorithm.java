/*
 * Created on 17 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.algorithms;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.j3d.BranchGroup;

import fr.emn.easymol.algorithms.util.VSEPRAtom;
import fr.emn.easymol.algorithms.util.VSEPRMolecule;
import fr.emn.easymol.algorithms.util.VSEPRMolecule3D;
import fr.emn.easymol.core.AbstractChemicalBond;
import fr.emn.easymol.core.AbstractChemicalCompound;
import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.core.ValencyBond;

/**
 * @author avaughan
 *  
 */
public class VSEPRAlgorithm implements IAlgorithm {

    private Molecule data;

    public VSEPRAlgorithm() {
        data = null;
    }

    public AbstractChemicalCompound getData() {
        return data;
    }

    public void setData(AbstractChemicalCompound acc) {
        data = (Molecule) acc;
    }

    public int getNumberBonded(int index) {
        if (!(data.getCompound(index) instanceof Atom))
            return -1;
        int numberBonded = 0;
        Atom a = (Atom) data.getCompound(index);
        for (Enumeration e = data.getBonds(); e.hasMoreElements();) {
            AbstractChemicalBond bond = (AbstractChemicalBond) e.nextElement();
            if (bond instanceof ValencyBond) {
                ValencyBond b = (ValencyBond) bond;
                if (b.getFirst().equals(a) || b.getSecond().equals(a))
                    numberBonded++;
            }
        }
        return numberBonded;
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
        return -1;
    }

    public Object compute() {
        VSEPRMolecule om = new VSEPRMolecule(data.getName());
        Hashtable map = new Hashtable();
        
        if (!data.getCompounds().hasMoreElements())
            return new BranchGroup(); 
        // TODO : this is a quick fix to avoid throwing an exception
        // when no compound is set. a cleaner solution would be to
        // throw a user defined exception and to catch it / display it
        // to the user

        // Adding the atoms
        int index = -1;
        int oRoot = 0;
        for (Enumeration e = data.getCompounds(); e.hasMoreElements();) {
            AbstractChemicalCompound acc = (AbstractChemicalCompound) e
                    .nextElement();
            index++;
            if (acc instanceof Atom) {
                Atom cur = (Atom) acc;
                int type = getOldType(acc.getSymbol());
                int oHash = om.addAtom(type);
                if (oRoot == 0 && type != VSEPRAtom.H)
                    oRoot = oHash;
                map.put(new Integer(index), new Integer(oHash));
            }
        }

        // Adding the bonds
        for (Enumeration e = data.getBonds(); e.hasMoreElements();) {
            AbstractChemicalBond acb = (AbstractChemicalBond) e.nextElement();
            if (acb instanceof ValencyBond) {
                try {
                    ValencyBond vb = (ValencyBond) acb;
                    int oHash1 = ((Integer) map.get(new Integer(data
                            .getIndex(vb.getFirst())))).intValue();
                    int oHash2 = ((Integer) map.get(new Integer(data
                            .getIndex(vb.getSecond())))).intValue();
                    int type = vb.getBondType();
                    om.addLink(oHash1, oHash2, type);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
            }
        }

        VSEPRMolecule3D m3d = new VSEPRMolecule3D(om, oRoot);
        return m3d.getMol3D();
    }

}