   package fr.emn.easymol.test;

// Importations standards
   import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.geometry.Molecule3D;


   public class Test3D extends Applet {
      public Test3D() {
         setLayout(new BorderLayout());
         Canvas3D canvas3D = new Canvas3D(null);
         add("Center", canvas3D); 
         BranchGroup scene = new BranchGroup();
         scene.addChild(createSceneGraph());
         scene.compile();
         SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
         simpleU.getViewingPlatform().setNominalViewingTransform();
         simpleU.addBranchGraph(scene);
      }
   
      public TransformGroup createSceneGraph() {
         Molecule m = new Molecule("Water");
         int h1 = m.addAtom(Atom.H);
         int h2 = m.addAtom(Atom.H);
         int o = m.addAtom(Atom.O);
         m.addLink(h1,o);
         m.addLink(h2,o);
      
         Molecule m2 = new Molecule("Carbon Dioxyde");
         int o1 = m2.addAtom(Atom.O);
         int o2 = m2.addAtom(Atom.O);
         int c = m2.addAtom(Atom.C);
         m2.addLink(o1,c,2);
         m2.addLink(o2,c,2);
      
      
         Molecule m3 = new Molecule("Ethanol");
         int h01 = m3.addAtom(Atom.H);
         int h02 = m3.addAtom(Atom.H);
         int h03 = m3.addAtom(Atom.H);
         int c01 = m3.addAtom(Atom.C);
         int h04 = m3.addAtom(Atom.H);
         int h05 = m3.addAtom(Atom.H);
         int h06 = m3.addAtom(Atom.H);
         int c02 = m3.addAtom(Atom.C);
         int o01 = m3.addAtom(Atom.O);
         m3.addLink(c01,o01);
         m3.addLink(c01,c02);
         m3.addLink(c01,h01);
         m3.addLink(c01,h02);
         m3.addLink(c02,h03);
         m3.addLink(c02,h04);
         m3.addLink(c02,h05);
         m3.addLink(o01,h06);

			Molecule m4 = new Molecule("Methan");
			int h11 = m4.addAtom(Atom.H);
			int h12 = m4.addAtom(Atom.H);
			int h13 = m4.addAtom(Atom.H);
			int h14 = m4.addAtom(Atom.H);
			int c11 = m4.addAtom(Atom.C);
			m4.addLink(c11,h11);
			m4.addLink(c11,h12);
			m4.addLink(c11,h13);
			m4.addLink(c11,h14);
      
         Transform3D rotate = new Transform3D();
         rotate.rotX(-Math.PI/8.0d);
         TransformGroup objRotate = new TransformGroup(rotate);
      
      
                // Etape 1 : Creation du groupe de transformation et reglage de l'aptitude
         TransformGroup objSpin = new TransformGroup();
         objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      
                // Ajout de la molecule au groupe de transfo
                // le cube est une feuille de l'arbre des graphes
         objSpin.addChild((new Molecule3D(m4,c11)).getMol3D());
                // Etape 2 : Creation de l'objet Alpha - fonction de variation temporelle
         Alpha rotationAlpha = new Alpha(-1,4000);
      
                // Etape 3 : ajout du comportement (objet determinant interpolator)
         RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objSpin);
      
                // Etape 4 : Determination de la region planifiee
         BoundingSphere bounds = new BoundingSphere();
         rotator.setSchedulingBounds(bounds);
      
                // Etape 5 : le Behavior (comportament) deviennt fils de objSpin (TG)
         objSpin.addChild(rotator);
      
         objRotate.addChild(objSpin);
      
         return objRotate;
      }        
   
      public static void main (String[] argv) {
         Frame frame = new MainFrame(new Test3D(),256,256); // creation de la frame pour execution en appli
      }
   }
