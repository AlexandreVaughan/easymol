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
   package fr.emn.easymol.gui;

// 3D imports   
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.geometry.Molecule3D;

/** The class to display the 3D view for a molecule and navigate into this 3D view.<br><br>
  * As it uses Molecule3D to render the molecule, the root atom passed to this class must *
  * NEVER be an hydrogen (see Molecule3D for more details, this class is used quite the same way).  
  * @see Molecule3D
  * @see Molecule
**/
   public class NavigationWindow extends JFrame implements GUIConstants { 
   
      private Canvas3D canvas3D = null;
      private SimpleUniverse simpleU=null;
      private BranchGroup scene=null;
   
   /** Creates a new 3D Navigation Window
     * @param m the molecule which will be used for navigation
     * @param hashFirst the hash code of the root atom for the molecule(must NEVER be an hydrogen)
   **/
      public NavigationWindow(Molecule m, int hashFirst) {
      // Window creation
         //GraphicsConfiguration def = (GraphicsEnvironment.getLocalGraphicsEnvironment()).getDefaultScreenDevice().getDefaultConfiguration();
         getContentPane().setLayout(new BorderLayout());
         GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
         canvas3D = new Canvas3D(gc); //def
         getContentPane().add("Center", canvas3D);
         setTitle(GUIConstants.TITLE_NAVIGATION);
         setSize(GUIConstants.WIDTH_NAVIGATION, GUIConstants.HEIGHT_NAVIGATION);
      
      
      // 3D Creation
         scene = new BranchGroup();
         scene.setCapability(BranchGroup.ALLOW_DETACH);
         simpleU = new SimpleUniverse(canvas3D);
         simpleU.getViewingPlatform().setNominalViewingTransform();
         scene.addChild(createSceneGraph(simpleU, m, hashFirst, this));
         scene.compile();
         simpleU.addBranchGraph(scene);
      }
   
   /** Creates a new empty NavigationWindow **/
      public NavigationWindow() {
         getContentPane().setLayout(new BorderLayout());
         canvas3D = new Canvas3D(null); //def
         getContentPane().add("Center", canvas3D);
         setTitle(GUIConstants.TITLE_NAVIGATION);
         setSize(GUIConstants.WIDTH_NAVIGATION, GUIConstants.HEIGHT_NAVIGATION);
      
      
      // 3D Creation
         scene = new BranchGroup();
         scene.setCapability(BranchGroup.ALLOW_DETACH);
         simpleU = new SimpleUniverse(canvas3D);
         simpleU.getViewingPlatform().setNominalViewingTransform();
         scene.compile();
         simpleU.addBranchGraph(scene);
      }
   
   /** Repaint this Navigation Window.
     * @param m the new molecule to draw
     * @param hashFirst the hash code od the first atom to parse
   **/
      public void refresh3D(Molecule m, int hashFirst) {
         simpleU.getLocale().removeBranchGraph(scene);
         scene=new BranchGroup();
         scene.setCapability(BranchGroup.ALLOW_DETACH);
         scene.addChild(createSceneGraph(simpleU, m, hashFirst, this));
         scene.compile();
         simpleU.addBranchGraph(scene);
      }
   
   // Creates the scene graph for the navigation window
      private BranchGroup createSceneGraph(SimpleUniverse su, Molecule m, int hashFirst, NavigationWindow t3n) {
         // Objects declarations
         t3n.getContentPane().add("North", new JLabel("Molecule Name : " + m.getName()));
         BranchGroup objRoot = new BranchGroup();
         TransformGroup rotateTrans = new TransformGroup();
         TransformGroup vpTrans= null;
         BoundingSphere mouseBounds = null;
         AmbientLight lightA = new AmbientLight();
         DirectionalLight lightD1 = new DirectionalLight();
      
      
         // Molecule creation
         vpTrans =  su.getViewingPlatform().getViewPlatformTransform();
         rotateTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
         rotateTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
         rotateTrans.addChild(new Molecule3D(m,hashFirst).getMol3D()); // here we add our molecule
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
      
         MouseTranslate myMouseTranslate = new MouseTranslate(MouseBehavior.INVERT_INPUT);
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
   
   /** Gets the Canvas3D object containing the Molecule 3D view and the navigation settings
     * @return the Canvas3D object
   **/ 
      public Canvas3D getCanvas3D() {
         return canvas3D;
      }       
   
   }
