/*
 * Created on 21 oct. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.components;

import fr.emn.easymol.core.IChemicalCompoundObserver;
import fr.emn.easymol.core.Molecule;

/**
 * @author avaughan
 */
public interface IMoleculePane extends IChemicalCompoundObserver {

    public abstract void setMolecule(Molecule m);

    public abstract Molecule getMolecule();

    public abstract void refresh();

}