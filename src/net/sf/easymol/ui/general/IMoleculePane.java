/*
 * Created on 21 oct. 2004
 *
 */
package net.sf.easymol.ui.general;

import net.sf.easymol.core.IChemicalCompoundObserver;
import net.sf.easymol.core.Molecule;

/**
 * @author avaughan
 */
public interface IMoleculePane extends IChemicalCompoundObserver {

    public abstract void setMolecule(Molecule m);

    public abstract Molecule getMolecule();

}