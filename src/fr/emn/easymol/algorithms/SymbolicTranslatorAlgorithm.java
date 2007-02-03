/*
 * Created on Oct 19, 2004
 *
 * TODO Document
 */
package fr.emn.easymol.algorithms;

import java.util.Enumeration;
import java.util.Vector;

import fr.emn.easymol.core.AbstractChemicalCompound;
import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;

/**
 * @author ava
 */
public class SymbolicTranslatorAlgorithm implements IAlgorithm {

    public static final int TO_COMPOUND = 0;

    public static final int TO_SYMBOL = 1;

    private int translationType = TO_COMPOUND;

    private AbstractChemicalCompound data = null;

    private Vector candidateCompounds = null;

    private Vector knownAtoms = new Vector();

    // TODO : also add a new compound vector dynamically filled

    public SymbolicTranslatorAlgorithm() {
        knownAtoms.add(Atom.C);
        knownAtoms.add(Atom.H);
        knownAtoms.add(Atom.N);
        knownAtoms.add(Atom.O);
    }

    public Atom getAtomFromSymbol(String symbol) {
        for (Enumeration e = knownAtoms.elements(); e.hasMoreElements();) {
            Atom atom = (Atom) e.nextElement();
            if (atom.getSymbol().equals(symbol))
                return atom;
        }
        return null; // TODO : and throw exception
    }

    public AbstractChemicalCompound getData() {
        return data;
    }

    public void setData(AbstractChemicalCompound acc) {
        data = acc;
    }

    public Object compute() {
        switch (translationType) {
        case TO_COMPOUND:
            return toCompound();
        case TO_SYMBOL:
            return toSymbol();
        }
        return null;
    }

    private String toSymbol() {
        Vector memory = new Vector();
        String toReturn = "";
        if (data instanceof Atom)
            return ((Atom) data).getSymbol();
        else if (data instanceof Molecule) {

            for (Enumeration e = ((Molecule) data).getCompounds(); e
                    .hasMoreElements();) {
                int curNbOcc = 0;
                AbstractChemicalCompound acc = (AbstractChemicalCompound) e
                        .nextElement();
                if (!memory.contains(acc.getSymbol())) {
                    memory.add(acc.getSymbol());
                    curNbOcc = nbOccurenceOfSymbol(acc.getSymbol());
                    toReturn += (acc.getSymbol().length() == 1 ? "" : "(")
                            + acc.getSymbol()
                            + (acc.getSymbol().length() == 1 ? "" : ")")
                            + (curNbOcc != 1 ? String.valueOf(curNbOcc) : "");
                }
            }
        }
        data.setSymbol(toReturn);
        return toReturn;
    }

    private int nbOccurenceOfSymbol(String symbol) {
        int toReturn = 0;
        for (Enumeration e = ((Molecule) data).getCompounds(); e
                .hasMoreElements();) {
            AbstractChemicalCompound acc = (AbstractChemicalCompound) e
                    .nextElement();
            if (acc.getSymbol().equals(symbol))
                toReturn++;
        }
        return toReturn;
    }

    // INFO : does not create the bonds : multiple possible configurations
    // TODO : could create bonds on case by case for know groups (ex. CH2, COOH
    // ...)
    // TODO : could also create inter group valency bonds for known groups
    // CH3-CH2, etc...
    // TODO : improve token parsing => string tokens : Br12, for example
    // TODO : improve recursion level => CH3((CH2)(CH2))6COOH for example
    // TODO : solution for all above : the interpreter pattern
    private AbstractChemicalCompound toCompound() {
        String symbol = data.getSymbol();
        if (data instanceof Atom) {
            return data = new Atom(getAtomFromSymbol(data.getSymbol()));
            // FIXME : modify data (copy the new compounds in data)
        }
        char[] tokens = symbol.toCharArray();
        String toSend = "";
        int curNbCpd = 1;
        boolean subCompound = false;
        boolean hasSubCompound = false;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == '(') {

                subCompound = true;
                hasSubCompound = true;
                if (toSend.length() > 1)
                    ((Molecule) data).addCompound(toSimpleCompound(toSend));
                toSend = "";
            } else if (tokens[i] == ')') {
                subCompound = false;
                if (i + 1 < tokens.length && Character.isDigit(tokens[i + 1]))
                    curNbCpd = Integer.parseInt("" + tokens[i + 1]);
                for (int j = 0; j < curNbCpd; j++)
                    ((Molecule) data).addCompound(toSimpleCompound(toSend));
                curNbCpd = 1;
                toSend = "";

            } else {
                toSend += tokens[i];
            }
        }
        if (hasSubCompound)
            ((Molecule) data).addCompound(toSimpleCompound(toSend));
        else
            data = toSimpleCompound(toSend);
        // FIXME : modify data (copy the new compounds in data)
        return data;

    }

    private AbstractChemicalCompound toSimpleCompound(String symbol) {
        Molecule m = new Molecule();
        if (Character.isDigit(symbol.charAt(0)))
            symbol = symbol.substring(1);
        m.setSymbol(symbol);
        char[] tokens = symbol.toCharArray();
        int curNbAtoms = 1;
        for (int i = 0; i < tokens.length; i++) {
            if (Character.isLetter(tokens[i])) {
                Atom a = getAtomFromSymbol(String.valueOf(tokens[i]));
                if (i + 1 < tokens.length && Character.isDigit(tokens[i + 1]))
                    curNbAtoms = Integer.parseInt("" + tokens[i + 1]);
                for (int j = 0; j < curNbAtoms; j++)
                    m.addCompound(new Atom(a));
                curNbAtoms = 1;
            }
        }
        return m;
    }

    /**
     * @return Returns the translationType.
     */
    public int getTranslationType() {
        return translationType;
    }

    /**
     * @param translationType
     *            The translationType to set.
     */
    public void setTranslationType(int translationType) {
        this.translationType = translationType;
    }
}