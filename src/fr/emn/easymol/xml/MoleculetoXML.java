/*
 * MoleculetoXML.java
 *
 * Created on 28.05.2005, 14:10
 */

package fr.emn.easymol.xml;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import fr.emn.easymol.core.AbstractChemicalCompound;
import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.core.ValencyBond;
/**
 * Class that exports a molecule to an XML-file.
 * @version 0.1
 * @author Aleksi Suomalainen
 */
public class MoleculetoXML {
    
    private static Document doc;
    private static XMLOutputter serializer;
    private String name;
    private static Molecule mol;
	/** Creates a new instance of MoleculetoXML
     *  @param m Molecule to transform
     */
    public MoleculetoXML(Molecule m) {
    	try {
    		mol = m;
    		FileWriter fw = new FileWriter(new File(m.getName()+".xml"));
    		serializer = new XMLOutputter();
    		serializer.setFormat(Format.getPrettyFormat());
    		handleMolecule(doc);
    		
    		//System.out.println(serializer.outputString(doc));
    	}
    	catch (IOException ioe)
		{
    	}
    	
    }
    /*
     * 
     */
    public MoleculetoXML(Molecule m, String s)
    {
    	try {
    		mol = m;
    		mol.setName(s);
    		FileWriter fw = new FileWriter(new File(m.getName()+".xml"));
    		serializer = new XMLOutputter();
    		serializer.setFormat(Format.getPrettyFormat());
    		handleMolecule(doc);
    		
    		//System.out.println(serializer.outputString(doc));
    	}
    	catch (IOException ioe)
		{
    	}
    	
    	
    }
    public Document getDocument()
    {
    	return doc;
    }
    private void handleMolecule(Document doc) 
    {
    	Element root = new Element("compound");
    	
    	Element molecule = new Element("molecule");
    	molecule.setAttribute("name",mol.getName());
    	Enumeration compounds = mol.getCompounds();
    	int numCompounds = mol.getNbCompounds();
    	int i = 0;
    	int maxlim;
    	// First we add the compounds
    	for (int i2 = 0; i2<numCompounds;i2++)
    	{
    		maxlim = nbOccurenceOfSymbol(mol.getCompound(i2).getSymbol(),mol);
    		((Atom)mol.getCompound(i2)).setName(((""+mol.getCompound(i2).getSymbol().charAt(0))+((maxlim)-i2++)).toLowerCase());
		}
    	while(compounds.hasMoreElements())
    	{
    		Element atom = new Element("atom");
    		Atom a = ((Atom)compounds.nextElement());
    		//maxlim = nbOccurenceOfSymbol(a.getSymbol(),mol);
    		//a.setName(a.getSymbol()+((maxlim)-i++));
    		atom.setAttribute("name",a.getSymbol());
    		
    		atom.setAttribute("bondid",a.getName().toLowerCase());
    		molecule.addContent(atom);
    	}
    	Enumeration bonds = mol.getBonds();
    	int j = 1;
    	while(bonds.hasMoreElements())
    	{
    		Element bond = new Element("bond");
    		ValencyBond vb = ((ValencyBond)bonds.nextElement());
    		bond.setAttribute("from",((Atom)vb.getFirst()).getName().toLowerCase());
    		bond.setAttribute("to",((Atom)vb.getSecond()).getName().toLowerCase());
    		bond.setAttribute("valency",""+vb.getBondType());
    		molecule.addContent(bond);
    	}
    	root.addContent(molecule);
    	DocType type = new DocType("compound", "molecule.dtd");
    	doc = new Document(root,type);
    	try {
    		System.out.println(serializer.outputString(doc));
    		serializer.output(doc,new FileWriter(new File("xml/"+mol.getName()+".xml")));
    		
    	}
    	catch (IOException ioe)
		{
    	}
    	
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
