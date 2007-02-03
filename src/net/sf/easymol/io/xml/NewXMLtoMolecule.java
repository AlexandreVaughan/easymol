package net.sf.easymol.io.xml;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.easymol.core.Atom;
import net.sf.easymol.core.EasyMolException;
import net.sf.easymol.core.Molecule;
import net.sf.easymol.core.ProxyBond;
import net.sf.easymol.test.PreBuiltMols;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class NewXMLtoMolecule {
	
	private Document doc;

	private Molecule mol;

	private Vector frombonds, tobonds;

	/**
	 * 
	 */
	public NewXMLtoMolecule(File f) {
		try {
			SAXBuilder b = new SAXBuilder(true);

			mol = new Molecule();
			doc = b.build(f);
			frombonds = new Vector();
			tobonds = new Vector();
			XMLOutputter out = new XMLOutputter();
			System.out.println(out.outputString(doc));
			visitDocument();

		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (JDOMException jdome) {
			System.out.println(jdome);
		}
	}

	public NewXMLtoMolecule(Document d) {
		mol = new Molecule();
		doc = d;
		frombonds = new Vector();
		tobonds = new Vector();
		visitDocument();
	}

	/**
	 * Method to return a Molecule.
	 * 
	 * @return A molecule from XML-file
	 */
	public Molecule getMolecule() {
		// System.out.println(mol);
		return mol;
	}
	public Document getDocument()
	{
		return doc;
	}
	

	/**
	 * Method that scans through the xml-file and calls the methods that are
	 * necessary to build the molecule, according to the core model of course.
	 * 
	 */
	public void visitDocument() {
		System.out.println("VISITDOCUMENT");
		Element element = doc.getRootElement();
		if ((element != null) && element.getName().equals("compound")) {
			System.out.println("Visiting compound");
			visitElement_compound(element);
		}
		if ((element != null) && element.getName().equals("bond")) {
			System.out.println("Visiting bond");
			visitElement_bond(element);
		}
		if ((element != null) && element.getName().equals("root")) {
			System.out.println("Visiting root");
			visitElement_root(element);
		}
	}

	void visitElement_root(Element element) {
		mol.setName(element.getAttributeValue("name"));
		List l = element.getChildren();
		for (Iterator i = l.iterator(); i.hasNext();) {
			Object o = i.next();
			if (((Element) o).getName().equals("compound"))
				visitElement_compound((Element) o);
			if (((Element) o).getName().equals("bond")) {
				visitElement_bond((Element) o);
			}
		}
	}

	/**
	 * Scan through org.w3c.dom.Element named bond. This is the core idea for
	 * the linear structure.
	 */
	void visitElement_bond(org.jdom.Element element) { // <bond>

		// System.out.println(element.getAttributeValue("from")+"->"+element.getAttributeValue("to"));
		frombonds.add(element.getAttributeValue("from"));
		tobonds.add(element.getAttributeValue("to"));
		System.out.println(frombonds);
		System.out.println(tobonds);
		
		try {
			mol
					.addBond(new ProxyBond(mol.getCompoundByUniqueId(element
							.getAttributeValue("from")), mol
							.getCompoundByUniqueId(element
									.getAttributeValue("to")), (element
							.getAttributeValue("valency") != null) ? Integer
							.parseInt(element.getAttributeValue("valency")) : 1));
			
		} catch (EasyMolException eme) {
			System.out.println(eme);
		}

	}

	/**
	 * Scan through org.w3c.dom.Element named compound (ie. the root element).
	 */

	void visitElement_compound(org.jdom.Element element) { // <compound>
		if (element.getAttributeValue("name")!=null){
			System.out.println(element.getAttributeValue("name"));
		} else {
			System.out.println(element.getAttributeValue("symbol"));
		}
		if (element.isRootElement()) {
			mol.setName(element.getAttributeValue("name"));
			mol.setUniqueId(element.getAttributeValue("id"));
		}
		if ((element.getAttributeValue("symbol") != null)
				&& element.getAttributeValue("symbol").length() == 1
				&& !(isLeaf(element))) {
			switch (element.getAttributeValue("symbol").charAt(0)) {
			case 'O':
				mol.addCompound(PreBuiltMols.O);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
				break;
			case 'N':
				mol.addCompound(PreBuiltMols.N);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
				break;
			case 'C':
				mol.addCompound(PreBuiltMols.C);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
				break;
			case 'H':
				mol.addCompound(PreBuiltMols.H);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
				break;
			default:
				System.out.println("Error! Compound not found");
			}
		} else {
			if (element.getAttributeValue("symbol").equals("H2O")) {
				mol.addCompound(PreBuiltMols.H2O);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
			} else if(element.getAttributeValue("symbol").equals("CH3"))
			{
				mol.addCompound(PreBuiltMols.CH3);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
			}
			else if(element.getAttributeValue("symbol").equals("CH2"))
			{
				mol.addCompound(PreBuiltMols.CH2);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
			}
			else if(element.getAttributeValue("symbol").equals("OH"))
			{
				mol.addCompound(PreBuiltMols.OH);
				mol.getLastCompound().setUniqueId(
						element.getAttributeValue("id"));
			}
		}
		// Recursion
		if (!isLeaf(element)) {
			List l = element.getChildren();
			for (Iterator i = l.iterator(); i.hasNext();) {
				Object o = i.next();
				if (((Element) o).getName().equals("compound"))
					visitElement_compound((Element) o);
				if (((Element) o).getName().equals("bond")) {
					visitElement_bond((Element) o);
				}
			}
		} else if (isLeaf(element)) {
			if (element.getAttributeValue("symbol") != null
					&& element.getAttributeValue("symbol").length() == 1) {
				switch (element.getAttributeValue("symbol").charAt(0)) {
				case 'O':
					mol.addCompound(new Atom(Atom.O));
					mol.getLastCompound().setUniqueId(
							element.getAttributeValue("id"));

					break;
				case 'N':
					mol.addCompound(new Atom(Atom.N));
					mol.getLastCompound().setUniqueId(
							element.getAttributeValue("id"));

					break;
				case 'C':
					mol.addCompound(new Atom(Atom.C));
					mol.getLastCompound().setUniqueId(
							element.getAttributeValue("id"));

					break;
				case 'H':
					mol.addCompound(new Atom(Atom.H));
					mol.getLastCompound().setUniqueId(
							element.getAttributeValue("id"));

					break;
				case 'S':
					mol.addCompound(new Atom(Atom.S));
					mol.getLastCompound().setUniqueId(
							element.getAttributeValue("id"));

					break;
				case 'P':
					mol.addCompound(new Atom(Atom.P));
					mol.getLastCompound().setUniqueId(
							element.getAttributeValue("id"));

					break;
				default:
					System.out.println("Error! Compound not found");
				}
			} else {
				if (element.getAttributeValue("symbol").equals("H2O")) {
					mol.addCompound(PreBuiltMols.H2O);
					mol.getLastCompound().setUniqueId(
							element.getAttributeValue("id"));
				} else if (element.getAttributeValue("id") != null)
				{
					mol.getLastCompound().setUniqueId(element.getAttributeValue("id"));
				}
			}
		}

	}

	private boolean isLeaf(Element e) {
		List l = e.getChildren();
		return l.isEmpty();
	}

	public static void main(String args[]) {
		NewXMLtoMolecule nxml = new NewXMLtoMolecule(
				new File("xml/jee.xml"));
		System.out.println(nxml.getMolecule());
		// nxml.visitDocument();
	}

}
