/*
 * 06/27/2002
 *
 * LibGUI.java - The most common used save/load/close functions
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

   import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.emn.easymol.core.Atom;
import fr.emn.easymol.core.Molecule;
import fr.emn.easymol.xml.MoleculeXMLReader;
import fr.emn.easymol.xml.MoleculeXMLWriter;


/** The class to store all the methods used by the GUI to perform file operations.<br><br>
	 It is used as a library.
**/

   public class LibGUI{
   
   
   /** Ask the user the name and root atom of his molecule and creates a new <code>VisualisationWindow</code>. Also properly updates the <code>ToolBarWindow</code> fields.
   *   @param tb the mother window of the <code>VisualisationWindow</code> you want to create
   **/
      public static void createNewFile(ToolBarWindow tb) {
      
      	// We will use the fantastic custom dialog provided by Swing (it's about midnight now...)!
         Object[] components = new Object[4];//the objects int the dialog box
         JPanel textPart = new JPanel();
         JLabel nameLabel = new JLabel("Molecule Name");
         JTextField nameField = new JTextField(20);
         textPart.add(nameLabel); 
         textPart.add(nameField);
         components[0] = textPart;
         components[1] = "What will be the first atom of your molecule ?";
      
         String[] options = {"Carbon", "Oxygen" , "Nitrogen", "Cancel"};
      
         int choice = JOptionPane.showOptionDialog(null, components, "New Molecule", JOptionPane.DEFAULT_OPTION,
         JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
      
         int rootAtom = -1;
         String name =null ;
      
         if (nameField.getText().length() == 0) // If there is no name entered, we set it to the default one
            name = GUIConstants.NAME_UNNAMED;
         else name = nameField.getText();
      
         switch (choice) {
            case 0 : //Carbon
               rootAtom = Atom.C;
               break;
            case 1 : // Oxygen
               rootAtom = Atom.O;
               break;
            case 2: // Nitrogen
               rootAtom = Atom.N;
               break;
            default: //Cancel
               return ;
            }
      
         Molecule newBorn = new Molecule(name);
         int hashRoot = newBorn.addAtom(rootAtom);
         VisualisationWindow newVisu = new VisualisationWindow(newBorn, hashRoot, tb);
         } 
   
   
   /** The generic method to save a molecule. Handles all cases by itself (no window opened, modified <code>Molecule</code>...)
   *   @param tb a <code>ToolBarWindow</code> whose console will be used to write the messages to.
   *   @param directSave if the method has to be used as direct saving (i.e no JFileChooser). Still passes all the tests.
   *   @return theresult of the operation. Possibles values are : noVisuWin, noChangesToSave, savedAs, canceled and DirectSave.
   **/
   
      public static String save(VisualisationWindow visu, ToolBarWindow tb, boolean directSave){
         ConsoleWindow csl = tb.getConsoleWindow();
         String output = null;
      
         if (!visu.isModified()) { // No changes to save
            csl.write(GUIConstants.INFO_NO_CHANGES_TO_SAVE + visu.getMoleculeFileName(), true);
            return "noChangesToSave";}
      
         if ((!directSave) || (visu.getMoleculeFileName() == GUIConstants.NAME_UNNAMED)) { // Perform a saveAs
            JFileChooser saveFile = new JFileChooser(tb.getFocusedWindow().getPath());
            saveFile.setFileFilter(new XMLFileFilter());
            saveFile.setSelectedFile(new File(visu.getMoleculeFileName()));
         	//We set it to be a save dialog
            int option = saveFile.showSaveDialog(null);
         
         	//Processing the different possibilities when the user hit OK 
            if (option == JFileChooser.APPROVE_OPTION) {
               File selectedFile = saveFile.getSelectedFile();
            //We keep trace of the path
               visu.setPath(selectedFile.getParent());
            //Here, the only problem might be an incorrect file name
               if (LibGUI.isFileTypeCorrect(selectedFile)) {
                  visu.setMoleculeFileName(selectedFile.getName());           
                  output = "savedAs";}
               else 
                  tb.getConsoleWindow().write(GUIConstants.ERR_INCORRECT_SAVE_FILE_TYPE, true);
               }
            
            else 
               return "canceled";
            }
         if (directSave) { // Perform a direct save
            output =  "directSave";
            }
      
      	//Here the fields have been properly saved so we call the low-level saveToDisk method
         String fileName = visu.getPath() + visu.getMoleculeFileName();
         LibGUI.saveToDisk(fileName, visu.getMolecule(), visu.getHashCode(), visu);
         visu.setModified(false);
         return output;
         }
   
   
   
   /** Generic method to arbitrary save all or close files. A quit option is also present.
   *   @param tb the <code>ToolBarWindow</code> whose console will be used to write the output messages to.
   *   @param doClosed if the frame must be closed.
   *   @param quit if we exit the application after performing the operation.
   **/
      public static void allSaveCloseQuit(ToolBarWindow tb, boolean doClosed, boolean quit){
         LinkedList winList = tb.getOpenedWins();
         boolean parsed = false;
      
         if (winList.isEmpty()) { // No window opened, we tell the user and exit
            if (quit)
               System.exit(0);
            tb.getConsoleWindow().write(GUIConstants.ERR_NO_MOLECULE_AVAILABLE, true);
            return;}
      
         if (doClosed){ // It's a "Close All". We remove the elements one after each other through close().
            int start = winList.size();
            int index =0; // We keep the number of wins closed to check if we don't already have parsed this window (the user may have choosen "cancel")
            while (!winList.isEmpty() && index<start) { //Parsing the list of opened windows
               close((VisualisationWindow) winList.getFirst(), true);
               index +=1;
               }
            }
         else{ //It's a "Save All". As the elements are not removed, we can parse the LinkedList peacefully...
            for (int i=0;i<winList.size();i++) {
               VisualisationWindow oneAmongOthers = (VisualisationWindow) winList.get(i);
             	//Direct save depends whether the molecule has no name, hence the test
               save(oneAmongOthers, tb, oneAmongOthers.getMoleculeFileName() != GUIConstants.NAME_UNNAMED);
               }}
      
         if (quit)
            System.exit(0);
         }
   
   
   
   /** This is a low-level method to save molecules to disk. It doesn't do any checking. 
   *   @param name the path of the file in which the molecule has to be saved.
   *   @param m the molecule to save.
   *   @param rootAtomHashCode the root atom of the Molecule (see core package).
   *   @param tb The <code>ToolBarWindow</code> whose console will be used to write the output messages to.
   *   @param visu The <code>VisualisationWindow</code> whose molecule will be saved.
   **/
      private static void saveToDisk(String name, Molecule m ,int rootAtomHashCode, VisualisationWindow visu) {
         ConsoleWindow csl = visu.getMother().getConsoleWindow();
         MoleculeXMLWriter mxw = new MoleculeXMLWriter(m, rootAtomHashCode, name);
         try{
            mxw.writeMolecule();
            csl.write( GUIConstants.SUCCESS_SAVE + visu.getMolecule().getName() + GUIConstants.SUCCESS_SAVE_LOCATION + name, false);}
            catch (java.io.IOException io) {
               csl.write(GUIConstants.ERR_SAVING, true);}
         }
   
   
   
   
   /**Loads a molecule from a file on disk. Handles the case when no <code>VisualisationWindow</code> is available.
   *  @param tb The <code>ToolBarWindow</code> whose <code>VisualisationWindow</code> will be used to receive the molecule loaded 
   **/
      public static void loadFromFile(ToolBarWindow tb) {
         if (tb.getFocusedWindow() != null)
            close(tb.getFocusedWindow(), false);
         JFileChooser openFile = new JFileChooser(tb.getVisuWinPath());
         openFile.setFileFilter(new XMLFileFilter());
         int option = openFile.showOpenDialog(null);
      	//We set it to be an open dialog
         if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = openFile.getSelectedFile();
         // We check whether the filename is correct; the process uses generic code in order to adapt to the situations
            if (LibGUI.isFileTypeCorrect(selectedFile)) {
               MoleculeXMLReader r = null;
               try {
                  r = new MoleculeXMLReader(selectedFile.getAbsolutePath());
                  }
                  catch (Exception e) {
                     tb.getConsoleWindow().write(GUIConstants.ERR_LOADING, true);}
            
               if (tb.getFocusedWindow() == null){  //Creates a VisualisationWindow because there is none available
                  VisualisationWindow newVisu = new VisualisationWindow(r.getMolecule(), r.getHashFirst(), tb);}
            
            
            	// All others operations are performed whatever window it is
               VisualisationWindow visu = tb.getFocusedWindow();
               visu.setPath(selectedFile.getParent());
               visu.setMoleculeFileName(selectedFile.getName());
               visu.setCurrentMolecule(r.getMolecule());
               visu.setCurrentHashCode(r.getHashFirst());
               visu.getNavWin().refresh3D(r.getMolecule(), r.getHashFirst());
               visu.getLewisView().refresh2D(r.getMolecule(), r.getHashFirst());
               visu.setModified(false);
               tb.getConsoleWindow().write(GUIConstants.SUCCESS_LOAD + visu.getMolecule().getName() + GUIConstants.SUCCESS_LOAD_LOCATION + selectedFile.getName(), false);}
            
            else { // The user wanted to fool us, hence tried to select a wrong type of file :) We tell him.
               tb.getConsoleWindow().write(GUIConstants.ERR_INCORRECT_LOAD_FILE_TYPE, true);
               }
            }
         }
   
   
   
   /** Closes a <code>VisualisationWindow</code> and checks whether the molecule needs to be saved.
   *   @param visu the <code>VisualisationWindow</code> to close.
   *   @param doClose if the frame must also disappear.
   **/
      public static void close(VisualisationWindow visu, boolean disappear){
         if (visu.isModified()){ //The molecule has been modified hence needs saving
            int selected = JOptionPane.showConfirmDialog(visu, GUIConstants.INFO_MOLECULE_MODIFIED ,visu.getTitle(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
         
            switch (selected){//Handles the differents user's choices
               case JOptionPane.YES_OPTION:
                  LibGUI.save(visu, visu.getMother(), true);
               
               case JOptionPane.NO_OPTION: // User don't want to save
                  break;
            
               case JOptionPane.CANCEL_OPTION:
                  return;
               }
            }
      
         if (!disappear) // We get rid of the frame, otherwise, the fields are kept and overwritten (e.g when a "load" is performed)
            return;
      
         ToolBarWindow tb = visu.getMother();
         tb.removeOpenedWindow(visu);
      	//Checking whether the actions must be disabled
         if (tb.getOpenedWins().isEmpty())
            tb.setActionsState(false);
         tb.setFocusedWindow(null);
         visu.dispose();
         }
   
   
   /** Tells the user the feature is not present in this release. Used when commands are not implemented. 
   **/
      public static void notYetImplemented() {
         JOptionPane.showMessageDialog(null, "<html>This feature hasn't been implemented yet.<br>Maybe in another release...<html>");
         }
   
   
   /** Tests whether a filename ends with .xml or .XML **/
      public static boolean isFileTypeCorrect(File f) {
         if (f == null)
            return false;
      
         return (f.getName().endsWith(".xml") || f.getName().endsWith(".XML"));}
   
      }