package net.sf.easymol.core;

public class CoreUtilities {

    /**
     * Adds a valency bond to a molecule (should be used only for testing)
     * 
     * @return The number of bonds -1
     */
    public static int addValencyBond(Molecule molecule, int atom1, int atom2, int bondType)
            throws EasyMolException {
        AbstractChemicalCompound a1 = molecule.getCompound(atom1);
        AbstractChemicalCompound a2 = molecule.getCompound(atom2);
        if (!(a1 instanceof Atom) || !(a2 instanceof Atom))
            throw new EasyMolException(111, "Molecule " + molecule.getSymbol(),
                    "Either " + a1.getSymbol() + " or " + a2.getSymbol()
                            + " is not an atom.");
        ValencyBond jamesBond = new ValencyBond((Atom) a1, (Atom) a2, bondType);
        return molecule.addBond(jamesBond);
    }
    
}
