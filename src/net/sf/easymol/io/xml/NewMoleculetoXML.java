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
public class NewMoleculetoXML {

    private Document doc;

    private static XMLOutputter serializer;

    // private String name;

    private static Molecule mol;

    /**
     * Creates a new instance of MoleculetoXML
     * 
     * @param m
     *            Molecule to transform
     */
    public NewMoleculetoXML(Molecule m) {

        mol = m;
        doc = new Document();
        serializer = new XMLOutputter();
        serializer.setFormat(Format.getPrettyFormat());
        handleMolecule(doc);

        System.out.println(serializer.outputString(doc));

    }

    /*
     *  
     */
    public NewMoleculetoXML(Molecule m, String filename) {
        mol = m;
        doc = new Document();
        serializer = new XMLOutputter();
        serializer.setFormat(Format.getPrettyFormat());
        handleMolecule(doc,filename);
        //System.out.println(serializer.outputString(doc));
    }

    public Document getDocument() {
        return doc;
    }

    private void handleMolecule(Document doc,String filename) {
        Element root = new Element("root");

        Element molecule = new Element("compound");
        root.setAttribute("name", mol.getName());
        Enumeration compounds = mol.getCompounds();
        int numCompounds = mol.getNbCompounds();
        molecule.setAttribute("id","0");
        // int i = 0;
        
        // First we add the compounds
        for (int i2 = 0; i2 < numCompounds; i2++) {
            
            (mol.getCompound(i2))
                    .setUniqueId("" + (i2+1));
        }
        String symbolformainmol = new String();
        while (compounds.hasMoreElements()) {
            Element atom = new Element("compound");
            AbstractChemicalCompound a = ((AbstractChemicalCompound)compounds.nextElement());
            //maxlim = nbOccurenceOfSymbol(a.getSymbol(),mol);
            //a.setName(a.getSymbol()+((maxlim)-i++));
            symbolformainmol+=a.getSymbol();
            atom.setAttribute("symbol", a.getSymbol());

            atom.setAttribute("id", a.getUniqueId().toLowerCase());
            molecule.addContent(atom);
        }
        molecule.setAttribute("symbol",symbolformainmol);
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
        DocType type = new DocType("root", "xml/newdtd.dtd");
        doc = new Document(root, type);
        try {
            System.out.println(serializer.outputString(doc));
            serializer.output(doc, new FileWriter(new File(filename)));

        } catch (IOException ioe) {
        }

    }
    private void handleMolecule(Document doc) {
    	Element root = new Element("root");

        Element molecule = new Element("compound");
        root.setAttribute("name", mol.getName());
        Enumeration compounds = mol.getCompounds();
        int numCompounds = mol.getNbCompounds();
        molecule.setAttribute("id","0");
        // int i = 0;
        
        // First we add the compounds
        for (int i2 = 0; i2 < numCompounds; i2++) {
            
            (mol.getCompound(i2))
                    .setUniqueId("" + (i2+1));
        }
        String symbolformainmol = new String();
        while (compounds.hasMoreElements()) {
            Element atom = new Element("compound");
            AbstractChemicalCompound a = ((AbstractChemicalCompound)compounds.nextElement());
            //maxlim = nbOccurenceOfSymbol(a.getSymbol(),mol);
            //a.setName(a.getSymbol()+((maxlim)-i++));
            symbolformainmol+=a.getSymbol();
            atom.setAttribute("symbol", a.getSymbol());

            atom.setAttribute("id", a.getUniqueId().toLowerCase());
            molecule.addContent(atom);
        }
        molecule.setAttribute("symbol",symbolformainmol);
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
        DocType type = new DocType("root", "newdtd.dtd");
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
