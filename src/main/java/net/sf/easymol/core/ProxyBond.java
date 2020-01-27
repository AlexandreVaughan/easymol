package net.sf.easymol.core;

import java.util.Vector;

public class ProxyBond extends AbstractChemicalBond {
	private int bondType = 0;
    private Vector subBonds = new Vector(); // of type AbstractChemicalBond
    
    public ProxyBond(AbstractChemicalCompound first, AbstractChemicalCompound second, int type) throws EasyMolException{
        super(first,second);

        if (first instanceof Atom && second instanceof Atom) {
        	System.out.println("Atom: "+first);
            System.out.println("Atom: "+second);
            subBonds.addElement(new ValencyBond((Atom) first, (Atom) second, type));
        } else if (first instanceof Molecule && second instanceof Molecule){
        	
            //subBonds.addElement(new ProxyBond(()))
        	subBonds.addElement(new ValencyBond( (Atom)(((Molecule)first).getFirstCompound()),(Atom)(((Molecule)second).getFirstCompound()),type));
        	System.out.println("Molecule: "+first+"\n");
            System.out.println("Molecule: "+second+"\n");
            System.out.println("Compound first:"+((Molecule)first).getFirstCompound()+"\n");
            System.out.println("Compound second:"+((Molecule)second).getFirstCompound()+"\n");
            //TODO : should be smart here, either use the first available subcompound in the
            //molecules or try all possible combinations and find the one with least energy
            // (that option can be tricky to implement)
        } else
        {
        	
        }
        
    }
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
    public void cleanupBond() {
        // TODO Auto-generated method stub

    }

    public int getBondStrength() {
        // TODO Auto-generated method stub
        return 0;
    }

}
