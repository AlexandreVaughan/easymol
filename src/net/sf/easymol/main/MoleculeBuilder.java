/*
 */
package net.sf.easymol.main;

import java.io.File;

import net.sf.easymol.core.Molecule;
import net.sf.easymol.io.xml.NewXMLtoMolecule;
import net.sf.easymol.ui.general.EasyMolDialog;

/**
 * @author munehiro
 *  
 */
public class MoleculeBuilder {

    private int newMoleculeCounter = 1;

    /**
     * Creates a new empty molecule
     * 
     * @return the molecule
     */
    public Molecule createNewMolecule() {
        Molecule m = new Molecule();
        m.setName("Unknown " + (newMoleculeCounter++));
        return m;
    }

    /**
     * Creates a molecule from a given filename. 
     * 
     * @param filename
     *            the name of the file to open
     * @param fileFormat
     *            th file format (xml or pdb)
     * @return the molecule
     */
    public Molecule createMoleculeFromFile(String filename, String fileFormat) {
    	File file = new File(filename);
    	if (file.exists()){
        if (fileFormat.equalsIgnoreCase("xml")) {
            NewXMLtoMolecule xml = new NewXMLtoMolecule(file);
            return xml.getMolecule();
        }
        
        if (fileFormat.equalsIgnoreCase("pdb")) {
            System.out.println("PDB File not handled yet");
            return null;
        }
    	}
    	else {
    		new EasyMolDialog("Error!","File does not excist!");
    		return null;
    	}
        return null;
    	
    	
    	
    	
    }

}
