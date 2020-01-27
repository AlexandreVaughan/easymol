package net.sf.easymol.io.pdb;

import net.sf.easymol.core.Atom;

public class PDBAtom extends PDBSection {
	
	private double x,y,z;
	private Atom a;
	private String atomname;
	private int id;
	private String resname;
	private double occupancy,tempfactor;
	private String segID,element,charge;
	public PDBAtom(double x, double y, double z, String atomname, int id, 
			String resname)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.atomname=atomname;
		this.id=id;
		this.resname=resname;
		
	}
	
	public String read() {		
		/*
		 * eg.
		 * 12345678901234567890123456789012345678901234567890123456789012345678901234567890
		   ATOM    145  N   VAL A  25      32.433  16.336  57.540  1.00 11.92      A1   N
		   ATOM    146  CA  VAL A  25      31.132  16.439  58.160  1.00 11.85      A1   C
		 */
		return "ATOM"+PDBSpaceMaker.FOUR+id+" "+"  "+atomname+PDBSpaceMaker.TWO+"VAL A 25"+PDBSpaceMaker.SIX+x+PDBSpaceMaker.TWO+y+PDBSpaceMaker.TWO+z+PDBSpaceMaker.TWO+"1.00 11.92"+PDBSpaceMaker.SIX+"A1   "+atomname;//null;
	}

}
