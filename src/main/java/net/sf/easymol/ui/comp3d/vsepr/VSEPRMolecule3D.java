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

package net.sf.easymol.ui.comp3d.vsepr;



import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;


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

    private final Group mol3D; // The 3D geometry

    private final VSEPRMolecule molData; // The data structure

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
        //Object creation
        mol3D = moleculeView(data.getAtom(hashFirst), null, 0, 0);

    }

    /**
     * Gets the 3D geometry for this molecule
     * 
     * @return the 3D geometry (a Java3D BranchGroup object)
     */
    public Group getMol3D() {
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
    public static PhongMaterial getAtomAppearance(int atomType) {
        PhongMaterial a = new PhongMaterial();
        switch (atomType) {
        case VSEPRAtom.H:
            a.setDiffuseColor(new Color(1.0, 1.0, 1.0, 1.0));
            a.setSpecularColor(new Color(0.25, 0.25, 0.25, 1.0));
            break;
        case VSEPRAtom.C:
            a.setDiffuseColor(new Color(0.50, 0.50, 0.50, 1.0));
            a.setSpecularColor(new Color(0.125, 0.125, 0.125, 1.0));
            break;
        case VSEPRAtom.O:
            a.setDiffuseColor(new Color(1.0, 0.0, 0.0, 1.0));
            a.setSpecularColor(new Color(0.125, 0.125, 0.125, 1.0));
            break;
        case VSEPRAtom.N:
            a.setDiffuseColor(new Color(0.0, 0.0, 1.0, 1.0));
            a.setSpecularColor(new Color(0.125, 0.125, 0.125, 1.0));
            break;
        case VSEPRAtom.S:
            a.setDiffuseColor(new Color(1.0, 1.0, 0.0, 1.0));
            a.setSpecularColor(new Color(0.125, 0.125, 0.125, 1.0));
            break;
        case VSEPRAtom.P:
            a.setDiffuseColor(new Color(1.0, 0.0, 1.0, 1.0));
            a.setSpecularColor(new Color(0.125, 0.125, 0.125, 1.0));
            break;
        default:
            a.setDiffuseColor(new Color(0.0, 0.0, 0.0, 1.0));
            a.setSpecularColor(new Color(0.0, 0.0, 0.0, 1.0));
            break;
        }
        return a;
    }

    // Gets the VSEPR position for an atom, knowing its link position, its
    // parent atom and the number of linked atoms
    private static VSEPRAngle3D getVSEPR(VSEPRMoleculeNode parent,
            int nbLinkedAtoms, int linkPosition) {
        VSEPRAngle3D position = new VSEPRAngle3D(0.0d, 0.0d, 0.0d);
        int m = 0;             // Non linking electron pairs
        int n = nbLinkedAtoms; // Number of links
        int sum = 0;
        if (parent != null) {
            switch (parent.getType()) {
            case VSEPRAtom.O:
            case VSEPRAtom.S:
                m = 2;
                break;
            case VSEPRAtom.N:
            case VSEPRAtom.P:
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
    private Group moleculeView(VSEPRMoleculeNode current,
            VSEPRMoleculeNode parent, int nbLinkedAtoms, int linkPosition) {

        // Transforms Creation
        // -------------------

        
        Group object = new Group(); // The object which will be returned
        // We get the VSEPR angles for rotation
        VSEPRAngle3D theta = getVSEPR(parent, nbLinkedAtoms, linkPosition);
        Rotate rotateX = new Rotate(theta.getAngleX(),Rotate.X_AXIS); // The X-axis rotation
        Rotate rotateY = new Rotate(theta.getAngleY(),Rotate.Y_AXIS); // The Y-axis rotation
        Rotate rotateZ = new Rotate(theta.getAngleZ() + Math.PI, Rotate.Z_AXIS); // The Z-axis rotation
        Translate translate = null; // The translation
        if (parent != null)
            translate = new Translate(0.3, 0.0, 0.0);
        
        if (translate != null)
            object.getTransforms().add(translate);

        object.getTransforms().add(rotateX);
        object.getTransforms().add(rotateY);
        object.getTransforms().add(rotateZ);

        // VSEPRAtom Creation
        // -------------------
        
        // We get the radius
        Sphere atom = new Sphere(getAtomRadius(current.getType()));
        
        // We get the appearance
        atom.setMaterial(getAtomAppearance(current.getType()));
        
        object.getChildren().add(atom);

        // Final object creation
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
                    object.getChildren().add(moleculeView(
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
