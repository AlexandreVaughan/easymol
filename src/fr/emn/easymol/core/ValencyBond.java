/*
 * Created on 17 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.core;

/**
 * Class that describes a valency bond.
 * @author avaughan
 * @see AbstractChemicalBond
 */
public class ValencyBond extends AbstractChemicalBond {

    private int bondType = 0;
    // A constant for the maximum valency.
    public static final int MAX_BOND_TYPE = 3;
    // A constant for single bond.
    public static final int SINGLE_BOND = 1;
    // A constant for double bond
    public static final int DOUBLE_BOND = 2;
    // A constant for triple bond
    public static final int TRIPLE_BOND = 3;

    /**
     * Constructs a valency bond out of two atoms and specified bonds
     * type. 
     * @param First atom.
     * @param Second atom.
     * @param Valency type.
     */
    public ValencyBond(Atom first, Atom second, int type)
            throws EasyMolException {
        super(first, second);
        this.setBondType(type);
        for (int i = 0; i < bondType; i++) {
            try {
                first.incrementSatisfiedValency();
            } catch (EasyMolException e) {
                for (int j = 0; j < i + 1; j++) {
                    first.decrementSatisfiedValency();
                }
                throw e;
            }
        }
        for (int i = 0; i < bondType; i++) {
            try {
                second.incrementSatisfiedValency();
            } catch (EasyMolException e) {
                for (int j = 0; j < i + 1; j++) {
                    first.decrementSatisfiedValency();
                    second.decrementSatisfiedValency();
                }
                throw e;
            }
        }
    }

    /**
     * @return Returns the bondType.
     */
    public int getBondType() {
        return bondType;
    }

    /**
     * @param bondType
     *            The bondType to set.
     */
    public void setBondType(int bondType) throws EasyMolException {
        if (bondType > 0 && bondType <= MAX_BOND_TYPE)
            this.bondType = bondType;
        else
            throw new EasyMolException(121, "ValencyBond",
                    "Bond type is invalid : " + bondType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.emn.easymol.core.AbstractChemicalBond#removeBond()
     */
    public void cleanupBond() {
        for (int i = 0; i < bondType; i++) {
            ((Atom) getFirst()).decrementSatisfiedValency();
            ((Atom) getSecond()).decrementSatisfiedValency();
        }
        bondType = 0;
    }
    /** 
     * Transforms this valency bond in to a String.
     */
    public String toString() {
        char bondView = '-';
        switch (bondType) {
        case 2:
            bondView = '=';
            break;
        case 3:
            bondView = '#';
            break;

        }
        return getFirst().getSymbol() + bondView + getSecond().getSymbol();
    }
}