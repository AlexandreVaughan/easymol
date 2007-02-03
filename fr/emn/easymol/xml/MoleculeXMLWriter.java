/*
 * 04/18/2002
 *
 * MoleculeXMLWriter.java - To write a Molecule to diskl
 * Copyright (C) 2002 Alexandre Vaughan
 * avaughan@altern.org
 * 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
   package fr.emn.easymol.xml;

// I/O utils
   import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.core.MoleculeNode;

/** A class to write a Molecule to disk using the EasyMol XML format<br><br>
  * This class just saves the molecule object passed to its constructor 
  * with the method writeMolecule. The molecule is saved in the EasyMol
  * XML format (see the user's guide). For the molecule to be displayed
  * correctly after reloading, the root element should NEVER be an hydrogen atom.
  * It can be a root carbon or oxygen for instance ... Here is an example of 
  * this : <br><br>
  * <code>
  *      Molecule water = new Molecule("Water");<br>
  *      ...<br>
  *      MoleculeXMLWriter mxw = new MoleculeXMLWriter(water,o,"water.xml"); // o is the root atom (oxygen)<br>
  *      mxw.writeMolecule();<br>
  * </code>
  * @see Molecule
  * @see MoleculeXMLReader
**/
   public class MoleculeXMLWriter {
   
      private Molecule molecule; // The molecule to write
      private int hashFirst; // The hash code of the root element
      private String xmlFileName; // the name of the file to output
   
   /** Builds a new MoleculeXMLWriter
     * @param m the molecule to parse
     * @param h the hash code of the root element (should be a root carbon)
     * @param s the name of the file to write
   **/
      public MoleculeXMLWriter(Molecule m, int h, String s) {
         molecule = m;
         hashFirst = h;
         xmlFileName = s;
      }
   
   // Creates an empty DOM document
      private Document createDOMDocument() {
         try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader("<molecule></molecule>")));
            return doc;
         }
            catch (ParserConfigurationException pce) {
               System.err.println("Parser configuration error");
               return null;
            }
            catch (SAXException saxe) {
               System.err.println("Sax exception");
               return null;
            }
            catch (IOException ioe) {
               System.err.println("IO exception");
               return null;
            }
      }
   
   // Parses the molecule to create the DOM Document
      private Document parseMolecule(Document doc, Element element, MoleculeNode current, MoleculeNode parent, int linkType) {
      
      
         if (current.getLinks().size() == linkType && parent != null) {// Non-linked atom
            Element atom = doc.createElement("atom");
            atom.setAttribute("name", current.toString());
            atom.setAttribute("link", String.valueOf(linkType));
            element.appendChild(atom);
         }
         else { // linked atom
            Element molec = doc.createElement("moleculenode");
            molec.setAttribute("rootatom", current.toString());
            molec.setAttribute("link", String.valueOf(linkType));
            element.appendChild(molec);
            for(int i = 0; i < current.getLinks().size(); i++) {
               if((MoleculeNode) current.getLinks().get(i) != parent) {
                  if(current.linkType(i)==1 || i == current.getLinks().indexOf(current.getLinks().get(i))) {
                     parseMolecule(doc, molec, (MoleculeNode)current.getLinks().get(i), current, current.linkType(i));
                  }
               }
            }
         }
      
         return doc;
      }
   
   // Gets the XML code from the DOM Document
      private String getXMLCode(Document doc) {
         OutputFormat output = new OutputFormat(doc, "ISO-8859-1", true);
         output.setIndent(2);
         StringWriter writer = new StringWriter();
         try {
            XMLSerializer serializer = new XMLSerializer(writer, output);
            serializer.serialize(doc.getDocumentElement());
            return writer.toString();
         }
            catch (IOException e) {
               return null;
            }
      }
   
   // Writes the XML code from the DOM document to disk
      private void writeXMLCode(Document doc) throws IOException {
      
         BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(xmlFileName));
         byte[] b = getXMLCode(doc).getBytes();
         out.write(b,0,b.length);
         out.close();
      
      }
   
   /** Writes the molecule to disk using XML specifications 
     * @throws IOException if the directory to save the file to does not exists 
   **/
      public void writeMolecule() throws IOException {
         System.out.println("Writing the " +  molecule.getName() + " molecule to disk. Please wait...");
         Document doc = createDOMDocument();
         Element root = doc.getDocumentElement();
         root.setAttribute("name", molecule.getName());
         doc = parseMolecule(doc, root, molecule.getAtom(hashFirst), null, 0);
         writeXMLCode(doc);
         System.out.println("Done.");
      }
   
   }