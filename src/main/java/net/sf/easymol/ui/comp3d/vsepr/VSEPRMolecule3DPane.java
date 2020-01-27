/*
 * 04/18/2002
 *
 * NavigationWindow.java - The window for 3D navigation
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




import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import net.sf.easymol.core.Molecule;
import net.sf.easymol.ui.comp3d.IMolecule3DPane;
import net.sf.easymol.ui.general.IMoleculePane;




/**
 * The class to display the 3D view for a molecule and navigate into this 3D
 * view. <br>
 * <br>
 * As it uses VSEPRMolecule3D to render the molecule, the root atom passed to
 * this class must * NEVER be an hydrogen (see VSEPRMolecule3D for more details,
 * this class is used quite the same way).
 * 
 * @see VSEPRMolecule3D
 * @see VSEPRMolecule
 */
public class VSEPRMolecule3DPane implements IMolecule3DPane, IMoleculePane {

    private Group moleculeGeometry;

    private Molecule moleculeData;

    /**
     * Creates a new 3D Navigation Window
     * 
     * @param m
     *            the molecule which will be used for navigation
     */
    public VSEPRMolecule3DPane(Molecule m) {
        moleculeData = m;
    }
    
    @Override
    public Parent constructScene() {
        init();
        return moleculeGeometry;
    }
    
    private void init() {
        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -3));
 
        // Build the Scene Graph
        Group root = new Group();       
        root.getChildren().add(camera);
        VSEPRCoreAdapter adapter = new VSEPRCoreAdapter();
        adapter.setMolecule(moleculeData);
        Group moleculeVSEPR = adapter.getMoleculeGeometry();
        root.getChildren().add(moleculeVSEPR);
 
        // Use a SubScene       
        SubScene subScene = new SubScene(root, 300,300);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        moleculeGeometry = group;
    }

    /**
     * Repaint this Navigation Window.
     *  
     */
    public void refresh() {
        //init();
    }

    /**
     * @return Returns the molecule.
     */
    @Override
    public Molecule getMolecule() {
        return moleculeData;
    }

    /**
     * @param molecule
     *            The molecule to set.
     */
    @Override
    public void setMolecule(Molecule molecule) {
       moleculeData = molecule;
       
    }

}
