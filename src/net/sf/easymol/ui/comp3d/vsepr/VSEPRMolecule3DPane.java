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

// 3D imports   
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.TransformGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import net.sf.easymol.core.Molecule;
import net.sf.easymol.ui.comp3d.IMolecule3DPane;
import net.sf.easymol.ui.general.IMoleculePane;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;


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
public class VSEPRMolecule3DPane extends JPanel implements IMolecule3DPane, IMoleculePane {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Canvas3D canvas3D = null;

    private SimpleUniverse simpleU = null;

    private BranchGroup scene = null;

    private VSEPRCoreAdapter vsepr = null;

    private Molecule molecule = null;

    /**
     * Creates a new 3D Navigation Window
     * 
     * @param m
     *            the molecule which will be used for navigation
     */
    public VSEPRMolecule3DPane(Molecule m) {
        // Window creation
        //GraphicsConfiguration def =
        // (GraphicsEnvironment.getLocalGraphicsEnvironment()).getDefaultScreenDevice().getDefaultConfiguration();
        vsepr = new VSEPRCoreAdapter();
        m.addObserver(this);
        this.setMolecule(m);
        this.setLayout(new BorderLayout());
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        canvas3D = new Canvas3D(gc); //def
        this.add("Center", canvas3D);
        //setTitle("EasyMol Navigation Window");
        setSize(300, 300);

        // 3D Creation
        scene = new BranchGroup();
        scene.setCapability(BranchGroup.ALLOW_DETACH);
        simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        scene.addChild(createSceneGraph(simpleU, this.getMolecule(), this));
        scene.compile();
        simpleU.addBranchGraph(scene);
    }

    /**
     * Repaint this Navigation Window.
     *  
     */
    public void refresh() {
        simpleU.getLocale().removeBranchGraph(scene);
        scene = new BranchGroup();
        scene.setCapability(BranchGroup.ALLOW_DETACH);
        scene.addChild(createSceneGraph(simpleU, this.getMolecule(), this));
        scene.compile();
        simpleU.addBranchGraph(scene);
    }

    // Creates the scene graph for the navigation window
    private BranchGroup createSceneGraph(SimpleUniverse su, Molecule m,
            VSEPRMolecule3DPane t3n) {
        // Objects declarations
        vsepr.setMolecule(m);
        t3n.add("North", new JLabel("Name : " + m.getName()));
        BranchGroup objRoot = new BranchGroup();
        TransformGroup rotateTrans = new TransformGroup();
        TransformGroup vpTrans = null;
        BoundingSphere mouseBounds = null;
        AmbientLight lightA = new AmbientLight();
        DirectionalLight lightD1 = new DirectionalLight();

        // VSEPRMolecule creation
        vpTrans = su.getViewingPlatform().getViewPlatformTransform();
        rotateTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        rotateTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Object o = vsepr.getMoleculeGeometry();
        if (o != null) {
            rotateTrans.addChild((BranchGroup) o); // here
        }
        // we add
        // our
        // molecule
        objRoot.addChild(rotateTrans);

        // Lights
        lightA.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000.0));
        objRoot.addChild(lightA);

        lightD1.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000.0));
        objRoot.addChild(lightD1);

        mouseBounds = new BoundingSphere(new Point3d(), 1000.0);

        //Mouse Behaviors
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(rotateTrans);
        myMouseRotate.setSchedulingBounds(mouseBounds);
        objRoot.addChild(myMouseRotate);

        MouseTranslate myMouseTranslate = new MouseTranslate(
                MouseBehavior.INVERT_INPUT);
        myMouseTranslate.setTransformGroup(vpTrans);
        myMouseTranslate.setSchedulingBounds(mouseBounds);
        objRoot.addChild(myMouseTranslate);

        MouseZoom myMouseZoom = new MouseZoom(MouseBehavior.INVERT_INPUT);
        myMouseZoom.setTransformGroup(vpTrans);
        myMouseZoom.setSchedulingBounds(mouseBounds);
        objRoot.addChild(myMouseZoom);
        objRoot.compile();
        return objRoot;

    }

    /**
     * Gets the Canvas3D object containing the VSEPRMolecule 3D view and the
     * navigation settings
     * 
     * @return the Canvas3D object
     */
    public Canvas3D getCanvas3D() {
        return canvas3D;
    }

    /**
     * @return Returns the molecule.
     */
    public Molecule getMolecule() {
        return molecule;
    }

    /**
     * @param molecule
     *            The molecule to set.
     */
    public void setMolecule(Molecule molecule) {
        // FIXME is the existence of this method ok ?
        // Molecule3Dpane is a view on a document (molecule).
        // Should we associate each view to its document?
        // What is the sennso behind changing the document to
        // a 1-1 association between a view and its document? - mune
        this.molecule = molecule;
    }

}
