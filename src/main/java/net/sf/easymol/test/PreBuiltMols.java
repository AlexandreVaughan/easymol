package net.sf.easymol.test;

import java.util.Arrays;

import net.sf.easymol.core.Atom;
import net.sf.easymol.core.CoreUtilities;
import net.sf.easymol.core.EasyMolException;
import net.sf.easymol.core.Molecule;

public class PreBuiltMols {
	public static Molecule H2O = getH2O();
	public static Molecule CH2 = getCHn(2);
	public static Molecule CH3 = getCHn(3);
	public static Molecule OH = getOHn(1);
	public static Molecule O = getO();
	public static Molecule N = getN();
	public static Molecule H = getH();
	public static Molecule C = getC();
	public static Molecule S = getS();
	public static Molecule P = getP();
	public static Molecule getOHn(int num)
	{
		Molecule mol = new Molecule();
		int atoms[] = new int[num+1];
		atoms[0] = mol.addCompound(new Atom(Atom.O));
		if (num>1)
		{
		mol.setSymbol("OH"+num);
		} else 
		{
			mol.setSymbol("OH");
		}
		for (int i=1;i<=num;i++)
		{
			atoms[i] = mol.addCompound(new Atom(Atom.H));
		}
		for (int j=1;j<atoms.length;j++)
		{
			try {
			CoreUtilities.addValencyBond(mol,atoms[0], atoms[j], 1);
			}
			catch (EasyMolException eme)
			{
				
			}
		}
		System.out.println("len:"+atoms.length+"Atoms"+Arrays.toString(atoms));
		return mol;
	}
	public static Molecule getCHn(int num)
	{
		Molecule mol = new Molecule();
		int atoms[] = new int[num+1];
		atoms[0] = mol.addCompound(new Atom(Atom.C));
		if (num>1)
		{
		mol.setSymbol("CH"+num);
		} else 
		{
			mol.setSymbol("CH");
		}
		for (int i=1;i<=num;i++)
		{
			atoms[i] = mol.addCompound(new Atom(Atom.H));
		}
		for (int j=1;j<atoms.length;j++)
		{
			try {
			CoreUtilities.addValencyBond(mol,atoms[0], atoms[j], 1);
			}
			catch (EasyMolException eme)
			{
				
			}
		}
		System.out.println("len:"+atoms.length+"Atoms"+Arrays.toString(atoms));
		return mol;
	}
	private static Molecule getO()
	{
		Molecule mol = new Molecule();
		mol.setSymbol("O");
		mol.setName("Oxygen");
		mol.addCompound(new Atom(Atom.O));
		return mol;
	}
	private static Molecule getN()
	{
		Molecule mol = new Molecule();
		mol.setSymbol("N");
		mol.setName("Nitrogen");
		mol.addCompound(new Atom(Atom.N));
		return mol;
	}
	private static Molecule getH()
	{
		Molecule mol = new Molecule();
		mol.setSymbol("H");
		mol.setName("Hydrogen");
		mol.addCompound(new Atom(Atom.H));
		return mol;
	}
	private static Molecule getC()
	{
		Molecule mol = new Molecule();
		mol.setSymbol("C");
		mol.setName("Carbon");
		mol.addCompound(new Atom(Atom.C));
		return mol;
	}
	private static Molecule getS()
	{
		Molecule mol = new Molecule();
		mol.setSymbol("S");
		mol.setName("Sulphur");
		mol.addCompound(new Atom(Atom.S));
		return mol;
	}
	private static Molecule getP()
	{
		Molecule mol = new Molecule();
		mol.setSymbol("P");
		mol.setName("Phosphorus");
		mol.addCompound(new Atom(Atom.P));
		return mol;
	}
	private static Molecule getH2O()
	{
		try {
		int h1,h2,o;
		Molecule mol = new Molecule();
		mol.setName("water");
		mol.setSymbol("H2O");
		h1 = mol.addCompound(new Atom(Atom.H));
        h2 = mol.addCompound(new Atom(Atom.H));
        o = mol.addCompound(new Atom(Atom.O));
        CoreUtilities.addValencyBond(mol,h1, o, 1);
        CoreUtilities.addValencyBond(mol,h2, o, 1);
		return mol; 
		}catch(EasyMolException ee)
		{
			System.out.println(ee);
		}
		return null;
	}
}
