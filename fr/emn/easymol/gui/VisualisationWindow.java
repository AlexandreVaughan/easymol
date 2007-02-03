 /*
 * 06/27/2002
 *
 * VisualisationWindow.java - The window that holds 2D and 3D representations
 * Copyright (C) 2002 Vincent Rubiolo
 * vrubiolo@eleve.emn.fr
 * vincent.rubiolo@wanadoo.fr
 * http://easymol.ifrance.com
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

   import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.Canvas3D;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import fr.emn.easymol.core.Molecule;

/** The class to hold the 2D and 3D views.<br><br>
* It contains an instance of the edited <code>Molecule</code> along with its <code>RootAtom</code>. <br>
* The path of the file and the <code>Molecule</code> name are saved along with a reference to the <code>ToolBarWindow</code>
* which is the mother of this <code>ConsoleWindow</code>.<br>
* A boolean is used to store the state of the <code>Molecule</code> and determine whether save is needed when the window is closed.
**/


   public class VisualisationWindow extends JFrame implements GUIConstants {
      //Class Variables
      private static ToolBarWindow motherWin = null;
   	//Instance variables
      private Molecule currentMolecule = null;
      private int rootAtomCurrentHashCode;
      private LewisViewWindow view2D = null;
      private NavigationWindow currentNavWin;
      private Canvas3D view3D;
      private boolean isModified;
      private String path = null;
      private String moleculeFileName = null;
   
   /** Constructs a new VisualisationWindow 
   *   @param m a <code>Molecule</code> used to initialise the currentMolecule field
   *   @param hashCode the hash code of the root atom of this molecule
   *   @param tb the <code>ToolBarWindow</code> which is the mother window of this new <code>VisualisatironWindow</code>
   **/
      public VisualisationWindow(Molecule m, int hashCode, ToolBarWindow tb) {
         //Operations on current molecule
         setCurrentMolecule(m);
         setCurrentHashCode(hashCode);
         setMoleculeFileName(GUIConstants.NAME_UNNAMED);
         setModified(false);
      
      	//Operations about the relation btw ToolBarWindow and VisualisationWindow
         setMother(tb);
         tb.addOpenedWindow(this);
         tb.setFocusedWindow(this);
         tb.setActionsState(true);
      
      	//Operations on the differents views
         NavigationWindow nav = new NavigationWindow(currentMolecule,rootAtomCurrentHashCode);
         setNavWin(nav);
         set3DView(nav.getCanvas3D());
         setLewisView(new LewisViewWindow(currentMolecule, rootAtomCurrentHashCode));
         getContentPane().add(buildFrame());
      
      	//Setting the usual parameters
         setSize(GUIConstants.WIDTH_VISU, GUIConstants.HEIGHT_VISU);
         setLocation((GUIConstants.SCREEN_WIDTH - GUIConstants.WIDTH_VISU)/2, (GUIConstants.SCREEN_HEIGHT - GUIConstants.HEIGHT_VISU)/2);
         setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
         show();
         addWindowListener(
               new WindowAdapter() {
                  public void windowClosing(WindowEvent e){//Cleans the fields of MotherWin when exiting
                     LibGUI.close(VisualisationWindow.this, true);}
               
                  public void windowActivated(WindowEvent e) {
                     getMother().setFocusedWindow(VisualisationWindow.this);}
                  }
            );
         }
   
   
   /** Gets the current <code>Molecule</code>
   *   @return the <code>Molecule</code> which is currently edited 
   **/
      public Molecule getMolecule(){
         return currentMolecule;}
   
   /** Sets the current <code>Molecule<code and update the title of the <code>VisualisationWindow</code> accordingly
   *   @param m the <code>Molecule> to be set as the current one
   **/
      public void setCurrentMolecule(Molecule m){
         currentMolecule = m;
         updateTitle();}
   
   /** Gets the hash code of the current <code>Molecule</code>
   *   @return the hash code of the current <code>Molecule</code>
   **/
      public int getHashCode(){
         return rootAtomCurrentHashCode;}
   
   /** Sets the current <code>Molecule</code> hash code
   *   @param code the hash code that will be set as the current molecule hash code
   **/	 
      public void setCurrentHashCode(int code){
         rootAtomCurrentHashCode = code;}
   
   /** Gets the mother window of this <code>VisualisationWindow</code>
   *   @return the mother window
   **/
      public ToolBarWindow getMother(){
         return motherWin;}
   
   /**Sets the mother window of this <code>VisualisationWindow</code>
   *  @param tb the <code>ToolBarWindow</code> to be set as the mother window
   **/
      public void setMother(ToolBarWindow tb){
         motherWin = tb;}
   
   /** Gets the 2D panel i.e the <code>LewisViewWindow</code>
   *   @return the <code>LewisViewWindow</code>
   **/
      public LewisViewWindow getLewisView() {
         return view2D;}
   
   /** Sets the 2D panel i.e the <code>LewisViewWindow</code>
   *   @param a <code>LewisViewWindow</code> to be set as the 2D representation
   **/
      public void setLewisView(LewisViewWindow lws){
         view2D = lws;}
   
   /** Gets the 3D view i.e the <code>NavigationWindow</code>
   *   @return the <code>NavigationWindow</code>
   **/
      public NavigationWindow getNavWin(){
         return currentNavWin;}
   
   /** Sets the 3D view i.e the <code>NavigationWindow</code>
   *   @param a <code>NavigationWindow</code> to be set as the 3D representation
   **/
      public void setNavWin(NavigationWindow nav){
         currentNavWin = nav;}
   
   /** Gets the 3D panel on which the <code>NavigationWindow</code> is built
   *   @return the 3D panel
   **/
      public Canvas3D get3DView(){
         return view3D;}
   
   /** Sets the 3D panel on which the <code>NavigationWindow</code> is built
   *   @param the <code>Canvas3D<code> to be set as the 3D panel 
   **/
      public void set3DView(Canvas3D cv){
         view3D = cv;}
   
   /** Sets the pathfile of the <code>Molecule</code>
   *   @param the path of the <code>Molecule> we are editing
   **/
      public void setPath(String p) {
         //In order to write properly the path, we use the system-dependant file separator
         path = p + java.io.File.separator;}
   
   /** Gets the pathfile of the <code>Molecule</code>
   *   @return the path of the <code>Molecule> we are editing
   **/
      public String getPath(){
         if (path == null)
            return "";
         else 
            return path;}
   
   /** Sets the name of the <code>Molecule</code> and update the title of the <code>VisualisationWindow</code> accordingly
   *   @param the name of the <code>Molecule</code> we are editing
   **/
      public void setMoleculeFileName(String name) {
         moleculeFileName = name;
         updateTitle();}
   
   /** Gets the name of the <code>Molecule</code> 
   *   @return the name of the <code>Molecule</code>> we are editing
   **/
      public String getMoleculeFileName(){
         return moleculeFileName;}
   
   /**Sets the internal boolean to indicate that the molecule has been modified 
   @param state the state of the molecule
   **/
      public void setModified(boolean state) {
         isModified = state;}
   
   /**Returns whether the molecule has been modified or not
   **/
      public boolean isModified(){
         return isModified;}
   
   /** Updates the title of the <code>VisualisationWindow</code> according to the <code>Molecuwle</code> name and the filename stored in the <code>VisualisationWindow</code>
   **/
      public void updateTitle(){
         setTitle(GUIConstants.TITLE_VISU_ONE + getMolecule().getName() + GUIConstants.TITLE_VISU_TWO + getMoleculeFileName());
         }
   
   
   /** Builds the frame of the <code>Visualisation Window</code>.<br>
   *   It consists of the 2D and 3D views, the toolbar and the radio buttons to switch netween the views 
   **/
      private JPanel buildFrame() {//Easier than manipulating the ContentPane of the Visualisationwindow
         JPanel frame = new JPanel(new BorderLayout());
      	//Building the radio buttons
         JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         ButtonGroup radioButtons = new ButtonGroup();
         JRadioButton only2D = new JRadioButton(GUIConstants.TITLE_BUTTON_2D_ONLY); 
         JRadioButton only3D = new JRadioButton(GUIConstants.TITLE_BUTTON_3D_ONLY);
         JRadioButton both2DAnd3D = new JRadioButton(GUIConstants.TITLE_BUTTON_2D_AND_3D);
         both2DAnd3D.setSelected(true);
      
      	//Grouping the buttons in a logical container 
         radioButtons.add(only2D);    
         radioButtons.add(only3D);   
         radioButtons.add(both2DAnd3D);
      	//Grouping the buttons in a physical container
         buttonPanel.add(only3D);
         buttonPanel.add(both2DAnd3D);
         buttonPanel.add(only2D);
      
      
      	//Building the space for 2D and 3D views - The final modifier is for compatibility with the inner class
         //JScrollPane view2DHolder = new JScrollPane(view2D);
         view2D.setPreferredSize(new Dimension (400, 400));
         final JSplitPane views = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,  view2D, view3D); 
      
      	//Assembling all these components into frame and setting the borders
         buttonPanel.setBorder(BorderFactory.createEtchedBorder());
         views.setBorder(BorderFactory.createEtchedBorder());
         frame.add(views, BorderLayout.CENTER);
         frame.add(buttonPanel, BorderLayout.SOUTH);
      	//Adding the toolbar on the top
         frame.add(buildToolBar(), BorderLayout.NORTH);
      
      	//Implementing the listeners for the radios buttons
         only3D.addActionListener( 
               new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                     views.setDividerLocation(0);
                     }
                  }
            );
      
         only2D.addActionListener( 
               new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                     views.setDividerLocation(views.getWidth());
                     }
                  }
            );
      
         both2DAnd3D.addActionListener( 
               new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                     views.setDividerLocation(views.getWidth()/2);
                     }
                  }
            );
      
      
         return frame;
         }
   
   
   
   /** Build the toolbar of the <code>Visualisation Window</code>
   *   @return the newly built toolbar
   **/
      private JToolBar buildToolBar() {
         JToolBar tb = new JToolBar(SwingConstants.HORIZONTAL);
      
      	//Building the toolbar buttons
      	//File buttons
         tb.add(getMother().getLoadAction());
         tb.add(getMother().getSaveAction());
         tb.addSeparator();
      
      	//Edit buttons
         tb.add(getMother().getCutAction());
         tb.add(getMother().getCopyAction());
         tb.add(getMother().getPasteAction());
         tb.add(getMother().getRemoveAtomAction());
         tb.add(getMother().getRemoveBondAction());
         tb.addSeparator();
      
      	//Atoms buttons
         tb.add(getMother().getAddCarbonAction());
         tb.add(getMother().getAddHydrogenAction());
         tb.add(getMother().getAddOxygenAction());
         tb.add(getMother().getAddNitrogenAction());
         tb.addSeparator();
      
      	//Links buttons
         tb.add(getMother().getAddSgBondAction());
         tb.add(getMother().getAddDbBondAction());
         tb.add(getMother().getAddTrBondAction());
         tb.addSeparator();
      
      	//Render and Rearrange buttons
         tb.add(getMother().getRenderAction());
         tb.add(getMother().getRearrangeAction());
      
         return tb;
         }
   
   
      }
