package net.sf.easymol.io.xml.xslt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.dom.DOMSource;

import net.sf.easymol.core.Molecule;
import net.sf.easymol.io.xml.MoleculetoXML;
import net.sf.easymol.io.xml.XMLtoMolecule;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.XSLTransformException;
import org.jdom.transform.XSLTransformer;
import org.jdom.xpath.XPath;

public class HTMLexporter {
	MoleculetoXML molxml;	
	Document input, output;
	Molecule mol;	
	DOMSource source;
	XPath path;
	XSLTransformer transformer;
	static String filename;
	public HTMLexporter(Molecule mol,String filename)
	{
		try{
		this.mol=mol;
		molxml = new MoleculetoXML(mol);
		input = molxml.getDocument();
		transformer = new XSLTransformer(new File("xml/html.xslt"));
		output = transformer.transform(input);
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		out.output(output, new FileWriter(filename));//System.out);
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
		catch(XSLTransformException xslte)
		{
			System.out.println(xslte);
		}
	}
}

