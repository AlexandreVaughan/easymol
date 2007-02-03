package net.sf.easymol.io.pdb;

public class PDBSpaceMaker {
	/**
	 * A constant string of one spaces.
	 */
	public static final String ONE = " ";
	/**
	 * A constant string of two spaces.
	 */
	public static final String TWO = "  ";
	/**
	 * A constant string of three spaces.
	 */
	public static final String THREE = "   ";
	/**
	 * A constant string of four spaces.
	 */
	public static final String FOUR = "    ";	
	/**
	 * A constant string of six spaces.
	 */
	public static final String SIX = "      ";
	
	/**
	 * Constructs a string of certain number of spaces. 
	 * @return A string of spaces
	 */
	public static String makeSpace(int numSpaces)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i<numSpaces;i++)
		{
			buf.append(" ");
		}
		return buf.toString();
	}
	
}
