/*
 * 04/18/2002
 *
 * MoleculeXMLReader.java - To read a Molecule from a XML file
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

// Input/output utilities 
   import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;

/** A class to read a molecule description from a XML file.<br><br>
  * The MoleculeXMLReader class is used to load a molecule described in
  * a file. The description of the Molecule must be in the EasyMol XML
  * format (see the user's guide for more information) for the class to load
  * it. After reading, the two objects returned by the class are the Molecule itself
  * and the hash code of the first element.
  * @see Molecule
  * @see MoleculeXMLWriter
**/
   public class MoleculeXMLReader {
   
      private Molecule molecule;
      private int hashFirst;
      private String xmlFileName;
   
   /** Builds a new MoleculeXMLReader
     * @param fileName the name of the XML file to parse
     * @throws IOException if file does not exist
   **/
      public MoleculeXMLReader(String fileName) throws IOException {
         xmlFileName=fileName;
         hashFirst=0;
         Document doc = createDOMDocument(xmlFileName);
         molecule = new Molecule(doc.getDocumentElement().getAttribute("name"));
         createMolecule(doc, doc.getDocumentElement(), 0);
      
      }
   
   // creates a new DOM document using a xml file
      private Document createDOMDocument(String uri) throws IOException
      {
         try
         {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(uri);
            return doc;
         } 
            catch (ParserConfigurationException pce) {
               System.err.println("[Parser configuration error]");
               return null;
            } 
            catch (SAXException saxe) {
               System.err.println("[SAX Exception]");
               return null;
            } 
      }
   
   // creates a molecule using a DOM document
      private void createMolecule(Document doc, Element element, int parent) {
         int m = 0;	
         NodeList childs = element.getChildNodes();
         for (int i = 0; i < childs.getLength(); i++) {
            Node node = childs.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element e = (Element) node;
               if (e.getTagName().equals("moleculenode")) {
                  m = molecule.addAtom(Atom.getAtomType(e.getAttribute("rootatom")));
                  if (parent != 0 && !(e.getAttribute("link").equals("0"))) {
                     molecule.addLink(parent, m, (new Integer(e.getAttribute("link")).intValue()));
                  }
                  else { 
                     hashFirst = m;
                  }
                  createMolecule(doc, e, m);
               }
               else if (e.getTagName().equals("atom")) {
                  m = molecule.addAtom(Atom.getAtomType(e.getAttribute("name")));
                  if (parent != 0 && !(e.getAttribute("link").equals("0"))) {
                     molecule.addLink(parent, m, (new Integer(e.getAttribute("link")).intValue()));
                  }
                  else {
                     hashFirst = m;
                  }
               }
            }
         }
      }
   
   /** Gets the root element of the molecule
     * @return the hash code of the root element
   **/
      public int getHashFirst() {
         return hashFirst;
      }
   
   /** Gets the parsed molecule
     * @return the molecule
   **/
      public Molecule getMolecule() {
         return molecule;
      }
   
   }