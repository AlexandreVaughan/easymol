/*
 * MoleculeExample.java
 *
 * Created on 10.6.2005, 23:43
 */

package fr.emn.easymol.xml;

import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.DocumentBuilder; 
import org.w3c.dom.Document; 
import fr.emn.easymol.xml.MoleculeScanner;         
         
/**
 * Sample class that parses the example document found with the project.
 * This version only displays information about bonds of the atom.
 * @version 0.1
 * @author Aleksi Suomalainen
 */
public class MoleculeExample {
    
    /** Creates a new instance of MoleculeExample */
    public MoleculeExample() {
    }
    public static void main(String args[])
    {
        try {
        DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new java.io.File("xml/sample.xml"));
        MoleculeScanner scanner = new MoleculeScanner (document);
        scanner.visitDocument();
        }
        catch (javax.xml.parsers.ParserConfigurationException pce)
        {
            System.out.println(pce.getMessage());
        }
        catch (java.io.FileNotFoundException fnfe)
        {
            System.out.println(fnfe.getMessage());
        }
        catch (org.xml.sax.SAXException saxe)
        {
            System.out.println(saxe.getMessage());
        }
        catch (java.io.IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
