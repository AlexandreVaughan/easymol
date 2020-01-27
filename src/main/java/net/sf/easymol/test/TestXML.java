package net.sf.easymol.test;

import junit.framework.TestCase;
import net.sf.easymol.core.Atom;
import net.sf.easymol.core.CoreUtilities;
import net.sf.easymol.core.EasyMolException;
import net.sf.easymol.core.Molecule;
import net.sf.easymol.io.xml.NewXMLtoMolecule;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
/*
 * Created on 28.11.2005
 * Just a stub class for testing the new XML-engine.
 */
public class TestXML extends TestCase {
	protected NewXMLtoMolecule nxml;
	protected XMLOutputter serializer;
	/*
	 * Test method for 'net.sf.easymol.io.xml.NewXMLtoMolecule.NewXMLtoMolecule(File)'
	 */
	public void testNewXMLtoMolecule() {
		// TODO Auto-generated method stub

	}
	protected void setUp() throws Exception
	{
		super.setUp();
		DocType type = new DocType("root", "newdtd.dtd");
		Element root = new Element("root");
		root.setAttribute("name","water");
		//root.setAttribute("id","1");
		Element compound = new Element("compound");
		compound.setAttribute("name","water");
		compound.setAttribute("symbol","H2O");
		compound.setAttribute("id","2");
		serializer = new XMLOutputter(Format.getPrettyFormat());
		
		root.addContent(compound);
		Document doc = new Document(root,type);
		System.out.println(serializer.outputString(doc));
		nxml = new NewXMLtoMolecule(doc);
		
	}
	/*
	 * Test method for 'net.sf.easymol.io.xml.NewXMLtoMolecule.getMolecule()'
	 * It creates internally made document (ie. via jdom)
	 */
	public void testGetMolecule() {
		// TODO Auto-generated method stub
		System.out.println(nxml.getMolecule().getLastCompound().toString());
		System.out.println(PreBuiltMols.H2O.toString());
		assertTrue(nxml.getMolecule().getLastCompound().toString().equals(PreBuiltMols.H2O.toString()));
		
	}
	public void testGetMolecule2()
	{
		DocType type = new DocType("root", "newdtd.dtd");
		Element root = new Element("root");
		serializer = new XMLOutputter(Format.getPrettyFormat());
		root.setAttribute("name","CH3");
		Element carbonCompound = new Element("compound");
		carbonCompound.setAttribute("name","carbon");
		carbonCompound.setAttribute("symbol","C");
		carbonCompound.setAttribute("id","1");
		Element hydroCompound1 = new Element("compound");
		hydroCompound1.setAttribute("name","hydrogen");
		hydroCompound1.setAttribute("symbol","H");
		hydroCompound1.setAttribute("id","2");
		Element hydroCompound2 = new Element("compound");
		hydroCompound2.setAttribute("name","hydrogen");
		hydroCompound2.setAttribute("symbol","H");
		hydroCompound2.setAttribute("id","3");
		Element hydroCompound3 = new Element("compound");
		hydroCompound3.setAttribute("name","hydrogen");
		hydroCompound3.setAttribute("symbol","H");
		hydroCompound3.setAttribute("id","4");
		
		root.addContent(hydroCompound1);
		root.addContent(hydroCompound2);
		root.addContent(hydroCompound3);
		Element bond1 = new Element("bond");
		bond1.setAttribute("from","2");
		bond1.setAttribute("to","1");
		Element bond2 = new Element("bond");
		bond2.setAttribute("from","3");
		bond2.setAttribute("to","1");
		Element bond3 = new Element("bond");
		bond3.setAttribute("from","4");
		bond3.setAttribute("to","1");
		carbonCompound.addContent(bond1);
		carbonCompound.addContent(bond2);
		carbonCompound.addContent(bond3);
		root.addContent(carbonCompound);
		Document doc = new Document(root,type);
		nxml = new NewXMLtoMolecule(doc);
		System.out.println(serializer.outputString(doc));
		try {
		Molecule mol = new Molecule();
		mol.setName("CH3");        
        int c;
        int h1;
        int h2;
        int h3;
        
        h1 = mol.addCompound(new Atom(Atom.H));
        h2 = mol.addCompound(new Atom(Atom.H));
        h3 = mol.addCompound(new Atom(Atom.H));
        c = mol.addCompound(new Atom(Atom.C));
        CoreUtilities.addValencyBond(mol,h1, c, 1);
        CoreUtilities.addValencyBond(mol,h2, c, 1);
        CoreUtilities.addValencyBond(mol,h3, c, 1);
        String clean = nxml.getMolecule().toString().replaceAll("<->","-");
        System.out.println(clean);//nxml.getMolecule().toString());
        System.out.println(mol.toString());
        assertTrue(clean.equals(mol.toString()));
		}catch (EasyMolException eme)
		{
			
		}
	}
}
