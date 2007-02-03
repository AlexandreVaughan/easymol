/*
 * 04/18/2002
 *
 * VSEPRMolecule3D.java - The 3D geometry utilities for EasyMol
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

package fr.emn.easymol.algorithms.util;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

/**
 * The class to create a VSEPRMolecule 3D geometry. <br>
 * <br>
 * This class converts a VSEPRMolecule object into a Java3D BranchGroup object
 * which is in fact the VSEPRMolecule geometry. This geometry is claculated
 * using the VSEPR (Valence Electronic Shell Pair Repulsion) method (see the
 * EasyMol user guide for more details). It needs the VSEPRMolecule itself and
 * the hash code of a root element (generally a root carbon, but NEVER a
 * hydrogen atom) and it returns the calculated BranchGroup. Here is an example :
 * <br>
 * <br>
 * <code> VSEPRMolecule water = new VSEPRMolecule("Water");<br>
 *        ...<br>
 *        VSEPRMolecule3D water3D = new VSEPRMolecule3D(water,o); // o is the hash code of the root oxygen<br>
 *        BranchGroup waterGeometry = water3D.getMol3D();<br>
 *</code>
 * 
 * @see VSEPRMolecule
 */
public class VSEPRMolecule3D {

    private BranchGroup mol3D; // The 3D geometry

    private VSEPRMolecule molData; // The data structure

    private int hashFirst; // The hash code of the first element

    /**
     * Builds a new 3D Geometry for a VSEPRMolecule
     * 
     * @param data
     *            the molecule to parse
     * @param hashFirst
     *            the hash code of the root element (must be a core carbon)
     */
    public VSEPRMolecule3D(VSEPRMolecule data, int hashFirst) {
        // Initialization
        molData = data;
        this.hashFirst = hashFirst;
        // Object creation
        mol3D = moleculeView(data.getAtom(hashFirst), null, 0, 0);
        mol3D.compile();

    }

    /**
     * Gets the 3D geometry for this molecule
     * 
     * @return the 3D geometry (a Java3D BranchGroup object)
     */
    public BranchGroup getMol3D() {
        System.out.println("VSEPRMolecule Name : " + molData.getName());
        return mol3D;
    }

    /**
     * Gets an atom appearence (color) for 3D rendering
     * 
     * @param atomType
     *            the atom type (a constant in the VSEPRAtom class)
     * @return the atom Appearance
     * @see VSEPRAtom
     */
    public static Appearance getAtomAppearance(int atomType) {
        Appearance a = new Appearance();
        Material m = new Material();
        ColoringAttributes ca = new ColoringAttributes();
        switch (atomType) {
		// FIXME : refactor toward a cleaner implementation for the 3d
        case VSEPRAtom.H:
            ca.setColor(new Color3f(1.0f, 1.0f, 1.0f));
            m.setAmbientColor(new Color3f(0.65f, 0.65f, 0.65f));
            m.setDiffuseColor(new Color3f(0.5f, 0.5f, 0.5f));
            m.setSpecularColor(new Color3f(0.25f, 0.25f, 0.25f));
            break;
        case VSEPRAtom.C:
            ca.setColor(new Color3f(0.5f, 0.5f, 0.5f));
            m.setAmbientColor(new Color3f(0.0f, 0.0f, 0.0f));
            m.setDiffuseColor(new Color3f(0.30f, 0.30f, 0.30f));
            m.setSpecularColor(new Color3f(0.125f, 0.125f, 0.125f));
            break;
        case VSEPRAtom.O:
            ca.setColor(new Color3f(1.0f, 0.0f, 0.0f));
            m.setAmbientColor(new Color3f(1.0f, 0.0f, 0.0f));
            m.setDiffuseColor(new Color3f(1.00f, 0.00f, 0.00f));
            m.setSpecularColor(new Color3f(1.0f, 0.0f, 0.0f));
            break;
        case VSEPRAtom.N:
            ca.setColor(new Color3f(0.0f, 0.0f, 1.0f));
            m.setAmbientColor(new Color3f(0.0f, 0.0f, 1.0f));
            m.setDiffuseColor(new Color3f(0.3f, 0.3f, 0.3f));
            m.setSpecularColor(new Color3f(0.125f, 0.125f, 0.125f));
            break;
        default:
            ca.setColor(new Color3f(0.0f, 0.0f, 0.0f));
            m.setAmbientColor(new Color3f(0.0f, 0.0f, 0.0f));
            m.setDiffuseColor(new Color3f(0.0f, 0.0f, 0.0f));
            m.setSpecularColor(new Color3f(0.0f, 0.0f, 0.0f));
            break;
        }
        a.setColoringAttributes(ca); // FIXME : setting a material overrides color attribute? IIRC yes - mune
        m.setShininess(0.8f);
        a.setMaterial(m);
        return a;
    }

    // Gets the VSEPR position for an atom, knowing its link position, its
    // parent atom
    // and the number of linked atoms
    private static VSEPRAngle3D getVSEPR(VSEPRMoleculeNode parent,
            int nbLinkedAtoms, int linkPosition) {
        VSEPRAngle3D position = new VSEPRAngle3D(0.0d, 0.0d, 0.0d);
        int m = 0; // Non linking electron pairs
        int n = nbLinkedAtoms; // Number of links
        int sum = 0;
        if (parent != null) {
            switch (parent.getType()) {
            case VSEPRAtom.O:
                m = 2;
                break;
            case VSEPRAtom.N:
                m = 1;
                break;
            default:
                m = 0;
                break;
            }
            sum = n + m;
            switch (sum) {
            case 2: // digonal
                switch (linkPosition) {
                case 1:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 0.0d);
                    break;
                case 2:
                    position = new VSEPRAngle3D(0.0d, 180.0d, 0.0d);
                    break;
                }
                break;
            case 3: // trigonal
                switch (linkPosition) {
                case 1:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 0.0d);
                    break;
                case 2:
                    position = new VSEPRAngle3D(0.0d, 120.0d, 0.0d);
                    break;
                case 3:
                    position = new VSEPRAngle3D(0.0d, 240.0d, 0.0d);
                    break;
                }
                break;
            case 4: // tetragonal
                switch (linkPosition) {
                case 1:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 0.0d);
                    break;
                case 2:
                    position = new VSEPRAngle3D(0.0d, 109.46d, 0.0d);
                    break;
                case 3:
                    position = new VSEPRAngle3D(120.0d, 109.46d, 0.0d);
                    break;
                case 4:
                    position = new VSEPRAngle3D(240.0d, 109.46d, 0.0d);
                    break;
                }
                break;
            case 5: // bi-pyramidal
                switch (linkPosition) {
                case 1:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 0.0d);
                    break;
                case 2:
                    position = new VSEPRAngle3D(0.0d, 120.0d, 0.0d);
                    break;
                case 3:
                    position = new VSEPRAngle3D(0.0d, 240.0d, 0.0d);
                    break;
                case 4:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 90.0d);
                    break;
                case 5:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 270.0d);
                    break;
                }
                break;
            case 6: // octaedric
                switch (linkPosition) {
                case 1:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 0.0d);
                    break;
                case 2:
                    position = new VSEPRAngle3D(0.0d, 90.0d, 0.0d);
                    break;
                case 3:
                    position = new VSEPRAngle3D(0.0d, 180.0d, 0.0d);
                    break;
                case 4:
                    position = new VSEPRAngle3D(0.0d, 270.0d, 0.0d);
                    break;
                case 5:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 90.0d);
                    break;
                case 6:
                    position = new VSEPRAngle3D(0.0d, 0.0d, 270.0d);
                    break;
                }
                break;
            default:
                break;
            }

        }
        return position;
    }

    // Builds the 3D view for the molecule using Java3D and recursion
    private BranchGroup moleculeView(VSEPRMoleculeNode current,
            VSEPRMoleculeNode parent, int nbLinkedAtoms, int linkPosition) {
        // Transforms Creation
        BranchGroup object = new BranchGroup(); // The object which will be
                                                // returned
        Transform3D rotateX = new Transform3D(); // The X-axis rotation
        Transform3D rotateY = new Transform3D(); // The Y-axis rotation
        Transform3D rotateZ = new Transform3D(); // The Z-axis rotation
        Transform3D translate = new Transform3D(); // The translation

        // Transforms initialization
        VSEPRAngle3D theta = getVSEPR(parent, nbLinkedAtoms, linkPosition); // We
                                                                            // get
                                                                            // the
                                                                            // VSEPR
                                                                            // angles
                                                                            // for
                                                                            // rotation
        if (parent != null)
            translate.set(new Vector3f(0.3f, 0.0f, 0.0f));
        else
            translate.set(new Vector3f(0.0f, 0.0f, 0.0f));
        rotateX.rotX(theta.getAngleX());
        rotateY.rotY(theta.getAngleY());
        rotateZ.rotZ(theta.getAngleZ() + Math.PI); // The PI here is to make the
                                                   // atom repulse from his
                                                   // parent (thank you Pi)
        rotateY.mul(rotateZ);
        rotateX.mul(rotateY);
        TransformGroup objRotate = new TransformGroup(rotateX);
        TransformGroup objTranslate = new TransformGroup(translate);

        // VSEPRAtom Creation
        Sphere atom = new Sphere(getAtomRadius(current.getType())); // We get
                                                                    // the
                                                                    // radius
        atom.setAppearance(getAtomAppearance(current.getType())); // We get the
                                                                  // appearance

        // Final object creation
        objTranslate.addChild(atom);
        objRotate.addChild(objTranslate);
        object.addChild(objRotate);
        System.out.println(current.toString());

        // Transmitted parameters for VSEPR
        int position = 1; // The position in the links list
        int nbLinks = 0; // the number of atom linked to the current atom
        if (parent != null) {
            position++;
            nbLinks++;
        }
        for (int k = 0; k < current.getLinks().size(); k++) {
            if ((VSEPRMoleculeNode) current.getLinks().get(k) != parent) {
                if (current.linkType(k) == 1
                        || k == current.getLinks().indexOf(
                                current.getLinks().get(k))) {
                    nbLinks++;
                }
            }
        }

        // Recursion on all the atom linked
        for (int i = 0; i < current.getLinks().size(); i++) {
            if ((VSEPRMoleculeNode) current.getLinks().get(i) != parent) {
                if (current.linkType(i) == 1
                        || i == current.getLinks().indexOf(
                                current.getLinks().get(i))) {
                    objTranslate.addChild(moleculeView(
                            ((VSEPRMoleculeNode) current.getLinks().get(i)),
                            current, nbLinks, position));
                    position++;
                }
            }
        }

        return object;
    }

    //Gets an atom radius knowing its type
    private float getAtomRadius(int type) {
        if (type == VSEPRAtom.H)
            return (float) 0.2;
        else
            return (float) 0.3;
    }
}
