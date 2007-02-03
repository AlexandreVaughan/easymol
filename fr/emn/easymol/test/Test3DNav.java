   package fr.emn.easymol.test;

// Importations standards
   import java.awt.BorderLayout;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.geometry.Molecule3D;


   public class Test3DNav extends JFrame { // Applet
      public Test3DNav() {
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
      
         Molecule m5 = new Molecule("Ammoniac");
         int h21 = m5.addAtom(Atom.H);
         int h22 = m5.addAtom(Atom.H);
         int h23 = m5.addAtom(Atom.H);
         int n21 = m5.addAtom(Atom.N);
         m5.addLink(n21,h21);
         m5.addLink(n21,h22);
         m5.addLink(n21,h23);
      
         //setLayout(new BorderLayout());
         getContentPane().setLayout(new BorderLayout());
         Canvas3D canvas3D = new Canvas3D(null);
         //add("Center", canvas3D);
         getContentPane().add("Center", canvas3D);
         setSize(400,400);
      
         BranchGroup scene = new BranchGroup();
         SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
         simpleU.getViewingPlatform().setNominalViewingTransform();
         scene.addChild(createSceneGraph(simpleU, m5, n21, this));
         scene.compile();
         simpleU.addBranchGraph(scene);
      }
   
      public BranchGroup createSceneGraph(SimpleUniverse su, Molecule m, int hashFirst, Test3DNav t3n) {
         t3n.getContentPane().add("South", new JLabel("Molecule Name : " + m.getName()));
         BranchGroup objRoot = new BranchGroup();
         TransformGroup rotateTrans = new TransformGroup();
         TransformGroup vpTrans= null;
         BoundingSphere mouseBounds = null;
      
         vpTrans =  su.getViewingPlatform().getViewPlatformTransform();
         rotateTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
         rotateTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
         rotateTrans.addChild(new Molecule3D(m,hashFirst).getMol3D());
         objRoot.addChild(rotateTrans);
         //objRoot.addChild(new Axis());
      
         mouseBounds = new BoundingSphere(new Point3d(), 1000.0);
      
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
   
      public static void main (String[] argv) {
         //Frame frame = new MainFrame(new Test3DNav(),256,256); // creation de la frame pour execution en appli
         Test3DNav n = new Test3DNav();
         n.show();
      }
   }
