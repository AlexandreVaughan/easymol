/*
 * TODO Document
 */
package fr.emn.easymol.main;

import fr.emn.easymol.core.Molecule;

/**
 * @author munehiro
 *  
 */
public class MoleculeBuilder {

	private int newMoleculeCounter = 1;

	/**
	 *	Creates a new empty molecule
	 *  @return the molecule
	 */
	public Molecule createNewMolecule() {
		Molecule m = new Molecule();
		m.setName("Unknown "+(newMoleculeCounter++));
		return m;
	}

	/**
	 *	Creates a molecule from a given filename. FIXME: error handling
     *	@param filename the name of the file to open
	 *  @param fileFormat FIXME the format of the file (XML, CML, XYZ, Zmatrix...)
	 *  @return the molecule
	 */
	public Molecule createMoleculeFromFile(String filename, String fileFormat) {
		/* TODO */
		return null;
	}

}
