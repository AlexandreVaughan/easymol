package net.sf.easymol.ui.comp3d;

import javax.media.j3d.Canvas3D;

import net.sf.easymol.ui.general.IMoleculePane;


public interface IMolecule3DPane extends IMoleculePane {
    /**
     * Gets the Canvas3D object containing the VSEPRMolecule 3D view and the
     * navigation settings
     * 
     * @return the Canvas3D object
     */
    public abstract Canvas3D getCanvas3D();


}