/*
 * MoleculetoXML.java
 *
 * Created on 28.05.2005, 14:10
 */

package net.sf.easymol.io.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import net.sf.easymol.core.AbstractChemicalBond;
import net.sf.easymol.core.AbstractChemicalCompound;
import net.sf.easymol.core.Molecule;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

// TODO : Review the DTD so that it is more generic. Instead of applying to atoms and
// valency bonds, it should apply to AbstractChemical*
/**
 * Class that exports a molecule to an XML-file.
 * 
 * @version 0.1
 * @author Aleksi Suomalainen
 */
public class MoleculetoXML {

    private static Document doc;

    private static XMLOutputter serializer;

    // private String name;

    private static Molecule mol;

    private String filename;

    /**
     * Creates a new instance of MoleculetoXML
     * 
     * @param m
     *            Molecule to transform
     */
    public MoleculetoXML(Molecule m) {

        mol = m;

        serializer = new XMLOutputter();
        serializer.setFormat(Format.getPrettyFormat());
        handleMolecule(doc);

        //System.out.println(serializer.outputString(doc));

    }

    /*
     *  
     */
    public MoleculetoXML(Molecule m, String filename) {
        mol = m;
        this.filename = filename;

        serializer = new XMLOutputter();
        serializer.setFormat(Format.getPrettyFormat());
        handleMolecule(doc,filename);

    }

    public Document getDocument() {
        return doc;
    }

    private void handleMolecule(Document doc,String filename) {
        Element root = new Element("compound");

        Element molecule = new Element("molecule");
        molecule.setAttribute("name", mol.getName());
        Enumeration compounds = mol.getCompounds();
        int numCompounds = mol.getNbCompounds();
        // int i = 0;
        int maxlim;
        // First we add the compounds
        for (int i2 = 0; i2 < numCompounds; i2++) {
            maxlim = nbOccurenceOfSymbol(mol.getCompound(i2).getSymbol(), mol);
            (mol.getCompound(i2))
                    .setUniqueId(("" + (mol.getCompound(i2).getSymbol().charAt(0)) + ((maxlim) - (i2 + 1)))
                            .toLowerCase());
        }
        while (compounds.hasMoreElements()) {
            Element atom = new Element("atom");
            AbstractChemicalCompound a = ((AbstractChemicalCompound)compounds.nextElement());
            //maxlim = nbOccurenceOfSymbol(a.getSymbol(),mol);
            //a.setName(a.getSymbol()+((maxlim)-i++));
            atom.setAttribute("name", a.getSymbol());

            atom.setAttribute("bondid", a.getUniqueId().toLowerCase());
            molecule.addContent(atom);
        }
        Enumeration bonds = mol.getBonds();
        // int j = 1;
        while (bonds.hasMoreElements()) {
            Element bond = new Element("bond");
            AbstractChemicalBond vb = ((AbstractChemicalBond) bonds.nextElement());
            bond.setAttribute("from", ((AbstractChemicalCompound) vb.getFirst()).getUniqueId()
                    .toLowerCase());
            bond.setAttribute("to", ((AbstractChemicalCompound) vb.getSecond()).getUniqueId()
                    .toLowerCase());
            bond.setAttribute("valency", "" + vb.getBondStrength());
            molecule.addContent(bond);
        }
        root.addContent(molecule);
        DocType type = new DocType("compound", "molecule.dtd");
        doc = new Document(root, type);
        try {
            System.out.println(serializer.outputString(doc));
            serializer.output(doc, new FileWriter(new File(filename)));

        } catch (IOException ioe) {
        }

    }
    private void handleMolecule(Document doc) {
        Element root = new Element("compound");

        Element molecule = new Element("molecule");
        molecule.setAttribute("name", mol.getName());
        Enumeration compounds = mol.getCompounds();
        int numCompounds = mol.getNbCompounds();
        // int i = 0;
        int maxlim;
        // First we add the compounds
        for (int i2 = 0; i2 < numCompounds; i2++) {
            maxlim = nbOccurenceOfSymbol(mol.getCompound(i2).getSymbol(), mol);
            ((AbstractChemicalCompound) mol.getCompound(i2))
                    .setUniqueId(("" + (mol.getCompound(i2).getSymbol().charAt(0)) + ((maxlim) - (i2 + 1)))
                            .toLowerCase());
        }
        while (compounds.hasMoreElements()) {
            Element atom = new Element("atom");
            AbstractChemicalCompound a = ((AbstractChemicalCompound) compounds.nextElement());
            //maxlim = nbOccurenceOfSymbol(a.getSymbol(),mol);
            //a.setName(a.getSymbol()+((maxlim)-i++));
            atom.setAttribute("name", a.getSymbol());

            atom.setAttribute("bondid", a.getUniqueId().toLowerCase());
            molecule.addContent(atom);
        }
        Enumeration bonds = mol.getBonds();
        // int j = 1;
        while (bonds.hasMoreElements()) {
            Element bond = new Element("bond");
            AbstractChemicalBond vb = ((AbstractChemicalBond) bonds.nextElement());
            bond.setAttribute("from", ((AbstractChemicalCompound) vb.getFirst()).getUniqueId()
                    .toLowerCase());
            bond.setAttribute("to", ((AbstractChemicalCompound) vb.getSecond()).getUniqueId()
                    .toLowerCase());
            bond.setAttribute("valency", "" + vb.getBondStrength());
            molecule.addContent(bond);
        }
        root.addContent(molecule);
        DocType type = new DocType("compound", "xml/molecule.dtd");
        this.doc = new Document(root, type);
        

    }

    private int nbOccurenceOfSymbol(String symbol, AbstractChemicalCompound data) {
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
}
