/*
 * XMLtoMolecule.java
 * @author Aleksi Suomalainen
 * @version 0.2
 * Created on 28.05.2005, 14:17
 */

package fr.emn.easymol.xml;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.EasyMolException;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.core.ValencyBond;
import fr.emn.easymol.ui.components.EasyMolDialog;
/**
 * Class that imports an XML-file and transforms it to a Molecule.
 * It supports the xml-form explained in Easymol wiki and has support for
 * both recursive and linear atom structure.
 * @author Aleksi Suomalainen
 */
public class XMLtoMolecule {
    private Document doc;
    private static Molecule mol;
    private static Vector frombonds, tobonds;
    private Hashtable valencies;
    private Atom a;
    /**
     * Method that scans through the xml-file and calls the methods
     * that are necessary to build the molecule, according to the core
     * model of course.
     *
     */
    public void visitDocument() {
        org.w3c.dom.Element element = doc.getDocumentElement();
        if ((element != null) && element.getTagName().equals("compound")) {
            //System.out.println("Visiting element");
            visitElement_compound(element);
        }
        if ((element != null) && element.getTagName().equals("molecule")) {
            visitElement_molecule(element);
        }
        if ((element != null) && element.getTagName().equals("atom")) {
            visitElement_atom(element);
        }
        if ((element != null) && element.getTagName().equals("loop")) {
            visitElement_bond(element);
        }
    }
    /**
     * Scan through org.w3c.dom.Element named compound (ie. the root element).
     */
    void visitElement_compound(org.w3c.dom.Element element) { // <compound>
        // element.getValue();
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    // ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element)node;
                    if (nodeElement.getTagName().equals("molecule")) {
                        visitElement_molecule(nodeElement);
                    }
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
            }
        }
    }
    
    /**
     * Scan through org.w3c.dom.Element named molecule.
     */
    void visitElement_molecule(org.w3c.dom.Element element) { 
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            if (attr.getName().equals("name")) { 
                mol.setName(attr.getValue());            	
            }
            if (attr.getName().equals("formula")) {
                mol.setSymbol(attr.getValue());            	
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
            //for future use.
//                case org.w3c.dom.Node.CDATA_SECTION_NODE:
//                    // ((org.w3c.dom.CDATASection)node).getData();
//                    break;
//            case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
//                // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
//                // ((org.w3c.dom.ProcessingInstruction)node).getData();
//                break;
//            case org.w3c.dom.Node.TEXT_NODE:
//                // ((org.w3c.dom.Text)node).getData();
//                break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element)node;
                    if (nodeElement.getTagName().equals("molecule")) {
                        visitElement_molecule(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("atom")) {
                        visitElement_atom(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("bond"))
                    {
                    	visitElement_bond(nodeElement);
                    }
                    break;
                
            }
        }
    }
    
    /**
     * Scan through org.w3c.dom.Element named atom. This elements
     * supports both recursive and linear structure.
     */
    void visitElement_atom(org.w3c.dom.Element element) {
        
    	if (element.getParentNode()!=null){
        	// Check the parent element value, then add the bond from  
        	// the child atom to the parent atom.
        		if (element.getParentNode().getNodeName().equals("atom")){
        			frombonds.add(element.getParentNode().getAttributes().getNamedItem("bondid").getNodeValue());
        			tobonds.add(element.getAttributes().getNamedItem("bondid").getNodeValue());
        		}
    	}
    	org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
    	org.w3c.dom.NodeList children = element.getChildNodes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            if (attr.getName().equals("name")) { // <atom name="???">
            	
            	if ((attr.getValue()).equalsIgnoreCase("O"))
            	{
            		a = new Atom(Atom.O);
            		a.setName(element.getAttributes().getNamedItem("bondid").getNodeValue());
            		mol.addCompound(a);
            	} 
            	else if((attr.getValue()).equalsIgnoreCase("H"))
            	{
            		a = new Atom(Atom.H);
            		a.setName(element.getAttributes().getNamedItem("bondid").getNodeValue());
            		mol.addCompound(a);
            	} 
            	else if ((attr.getValue()).equalsIgnoreCase("N"))
            	{
            		a = new Atom(Atom.N);
            		a.setName(element.getAttributes().getNamedItem("bondid").getNodeValue());
            		mol.addCompound(a);
            	}
            	else if ((attr.getValue()).equalsIgnoreCase("C"))
            	{
            		a = new Atom(Atom.C);
            		a.setName(element.getAttributes().getNamedItem("bondid").getNodeValue());
            		mol.addCompound(a);
            	}
            	
            	
            }
           
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
        
            switch (node.getNodeType()) {
                /* for future use.
                 * case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    // ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    // ((org.w3c.dom.Text)node).getData();
                    break;*/
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element)node;
                	if (nodeElement.getTagName().equals("atom")){
                    	visitElement_atom(nodeElement);
                	}
                	if (nodeElement.getTagName().equals("bond")){
                		visitElement_bond(nodeElement);
                	}
                    
                    break;
                
            }
            
            
        }
        
        			
        		
        		
        	
    }
    
    /**
     * Scan through org.w3c.dom.Element named bond. This is the core
     * idea for the linear structure.
     */
    void visitElement_bond(org.w3c.dom.Element element) { // <bond>
        //System.out.println(element.getValue());
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            if (attr.getName().equals("from")) { // <bond id="???">
                frombonds.add(attr.getValue());
            	
            }
            if (attr.getName().equals("to")) { // <bond valency="???">
            	tobonds.add(attr.getValue());
            	
            }
            if (attr.getName().equals("valency"))
            {
            	valencies.put(frombonds.lastElement()+"->"+tobonds.lastElement(),attr.getValue());
            	System.out.println(valencies);
            }
        }
        //System.out.println("Bond element ids:"+bonds);
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    // ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element)node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
            }
        }
        }
    /** 
     * Creates a new instance of XMLtoMolecule 
     *  @param f File to transform
     */
    public XMLtoMolecule(File f) {
    	try {
    	mol = new Molecule();
    	frombonds = new Vector();
    	tobonds = new Vector();
    	valencies = new Hashtable();
    	DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(true);
        
    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
        
        Document document = builder.parse(f);
        doc = document;
        visitDocument();
        buildBonds();        
        System.out.println(mol);
    	}
    	catch (javax.xml.parsers.ParserConfigurationException pce)
        {
    		System.out.println("PCE");
            System.out.println(pce.getMessage());
        }
        catch (java.io.FileNotFoundException fnfe)
        {
        	System.out.println("FNFE");
            System.out.println(fnfe.getMessage());
        }
        catch (org.xml.sax.SAXException saxe)
        {
        	System.out.println("SAXE");
            System.out.println(saxe);
        }
        catch (java.io.IOException ioe)
        {
        	System.out.println("IOE");
            System.out.println(ioe.getMessage());
        }
    }
    /**
     * Method that builds the molecule's bonds.
     * 
     * 
     */
    private void buildBonds()
    {
    	Vector valencybonds = new Vector();
    	String first, second;
    	System.out.println(frombonds+"\n"+tobonds);
    	
    	
    	Enumeration fromCompounds = frombonds.elements();
    	Enumeration toCompounds = tobonds.elements();
    	
    	    	
    	while (fromCompounds.hasMoreElements())
    	{
    		while (toCompounds.hasMoreElements())
    		{    			
    			first = (String)fromCompounds.nextElement();
    			second = (String)toCompounds.nextElement();
    			try {
    				
    				mol.addBond(new ValencyBond((Atom)mol.getCompoundForName(first),(Atom)mol.getCompoundForName(second),(valencies.get(first+"->"+second) != null) ? Integer.parseInt((String)valencies.get(first+"->"+second)):1));
    				
    			} catch (EasyMolException eme)
				{
    				new EasyMolDialog(eme.getMessage()+"\n"+first+"->"+second);
    				System.out.println(eme.getMessage()+"\n"+first+"->"+second);
    			}
    			
    		}
    		
    	}
    	
    }

    /**
     * Method to return a Molecule.
     * 
     * @return A molecule from XML-file
     */
    public Molecule getMolecule()
    {
    	//System.out.println(mol);
        return mol;
    }
}
