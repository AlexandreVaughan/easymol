 /*
 * 06/27/2002
 *
 * ToolBarWindow.java - The main window of EasyMol
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
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import fr.emn.easymol.core.Atom;

/** The class to build the main window of EasyMol.<br><br>
*    It contains references to: <ul>
*	 <li> the currently running <code>ToolbarWindow</code>.
*	 <li> a <code>LinkedList</code> of opened windows.
*	 <li> the focused <code>VisualisationWindow</code> in which <code>Molecule</code>s are edited.
*   <li> the <code>ConsoleWindow</code> to write information and error messages.
*	 </ul>
*   The action listeners are implemented using <code>Actions</code> so that they can be reused anywhere.
**/



   public class ToolBarWindow extends JFrame implements GUIConstants {
   
   	//Class Variables
      private static ToolBarWindow theOnlyOne = null;
      private static ConsoleWindow currentCslWin = new ConsoleWindow();
   	// In order for the console to uncheck the console option in the menu
      private static JCheckBoxMenuItem console = new JCheckBoxMenuItem(GUIConstants.TITLE_MENU_ITEM_CONSOLE);
      private static LinkedList openedWins = new LinkedList();
      private static VisualisationWindow currentFocused = null;
   
   	/** The "New" action used in menus. and toolbars **/
      private ActionNew newFile = new ActionNew(this,
      GUIConstants.NAME_NEW,
      GUIConstants.TOOLTIP_NEW, 
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_NEW)); 
   
   	/** The "Load" action used in menus. and toolbars **/
      private ActionLoad loadFile = new ActionLoad(this,
      GUIConstants.NAME_LOAD,
      GUIConstants.TOOLTIP_LOAD,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L,
      java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_LOAD)); 
   
   	/** The "Save" action used in menus. and toolbars **/
      private ActionSave save = new ActionSave(this,
      GUIConstants.NAME_SAVE,
      GUIConstants.TOOLTIP_SAVE,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_SAVE )); 
   
   	/** The "Save As" action used in menus. and toolbars **/
      private ActionSaveAs saveAs = new ActionSaveAs(this,
      GUIConstants.NAME_SAVE_AS,
      GUIConstants.TOOLTIP_SAVE_AS,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
      java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_SAVE_AS));  
   
   	/** The "Save All" action used in menus. and toolbars **/
      private ActionSaveAll saveAllFiles = new ActionSaveAll(this,
      GUIConstants.NAME_SAVE_ALL,
      GUIConstants.TOOLTIP_SAVE_ALL,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L,
      java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_SAVE_ALL));  
   
   	/** The "Close All" action used in menus. and toolbars **/
      private ActionCloseAll closeAllFiles = new ActionCloseAll(this,
      GUIConstants.NAME_CLOSE_ALL,
      GUIConstants.TOOLTIP_CLOSE_ALL,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_CLOSE_ALL));  
   
   	/** The "Quit" action used in menus. and toolbars **/
      private ActionQuit quit = new ActionQuit(this, 
      GUIConstants.NAME_QUIT, 
      GUIConstants.TOOLTIP_QUIT,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_QUIT));  
   
   	/** The "Cut" action used in menus. and toolbars **/
      private ActionCut cut = new ActionCut(this,
      GUIConstants.NAME_CUT, 
      GUIConstants.NAME_CUT,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_CUT));  
   
   	/** The "Copy" action used in menus. and toolbars **/
      private ActionCopy copy = new  ActionCopy(this,
      GUIConstants.NAME_COPY,
      GUIConstants.NAME_COPY,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_COPY));  
   
   	/** The "Paste" action used in menus. and toolbars **/
      private ActionPaste paste = new ActionPaste(this,
      GUIConstants.NAME_PASTE,
      GUIConstants.NAME_PASTE,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_PASTE));  
   
   	/** The "Add Carbon Atom" action used in menus. and toolbars **/
      private ActionAddOrRemoveAtom addCarbon = new ActionAddOrRemoveAtom(this, 
      GUIConstants.NAME_CARBON,
      GUIConstants.TOOLTIP_CARBON,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_CARBON),
      Atom.C,
      false);
   
   	/** The "Add Hydrogen Atom" action used in menus. and toolbars **/
      private ActionAddOrRemoveAtom addHydrogen= new ActionAddOrRemoveAtom(this,
      GUIConstants.NAME_HYDROGEN,
      GUIConstants.TOOLTIP_HYDROGEN,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_HYDROGEN),
      Atom.H,
      false);
   
   	/** The "Add Nitrogen Atom" action used in menus. and toolbars **/
      private ActionAddOrRemoveAtom addNitrogen = new ActionAddOrRemoveAtom(this,
      GUIConstants.NAME_NITROGEN,
      GUIConstants.TOOLTIP_NITROGEN,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_NITROGEN),
      Atom.N,
      false);
   
   	/** The "Add Oxygen Atom" action used in menus. and toolbars **/
      private ActionAddOrRemoveAtom addOxygen = new ActionAddOrRemoveAtom(this, 
      GUIConstants.NAME_OXYGEN,
      GUIConstants.TOOLTIP_OXYGEN,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_OXYGEN),
      Atom.O,
      false);
   
   	/** The "Remove Atom" action used in menus. and toolbars **/
      private ActionAddOrRemoveAtom removeAtom = new ActionAddOrRemoveAtom(this,
      GUIConstants.NAME_ATOM_REMOVE,
      GUIConstants.TOOLTIP_ATOM_REMOVE,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_ATOM_REMOVE),
      -1,
      true); 
   
   	/** The "Add Single Bond" action used in menus. and toolbars **/
      private ActionAddOrRemoveBond addSingle = new ActionAddOrRemoveBond(this, 
      GUIConstants.NAME_BOND_SG,
      GUIConstants.TOOLTIP_BOND_SG,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_BOND_SG),
      1,
      false);
   
   	/** The "Add Double Bond" action used in menus. and toolbars **/
      private ActionAddOrRemoveBond addDouble = new ActionAddOrRemoveBond(this, 
      GUIConstants.NAME_BOND_DB,
      GUIConstants.TOOLTIP_BOND_DB,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_BOND_DB),
      2,
      false);
   
   	/** The "Add Triple Bond" action used in menus. and toolbars **/
      private ActionAddOrRemoveBond addTriple = new ActionAddOrRemoveBond(this, 
      GUIConstants.NAME_BOND_TR,
      GUIConstants.TOOLTIP_BOND_TR,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_BOND_TR),
      3,
      false);
   
   	/** The "Remove Bond" action used in menus. and toolbars **/
      private ActionAddOrRemoveBond removeBond = new ActionAddOrRemoveBond(this, 
      GUIConstants.NAME_BOND_REMOVE,
      GUIConstants.TOOLTIP_BOND_REMOVE,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_BOND_REMOVE),
      -1,
      true);
   
   	/** The "Render" action used in menus. and toolbars **/
      private ActionRenderOrRearrange render = new ActionRenderOrRearrange(this, 
      GUIConstants.NAME_RENDER,
      GUIConstants.TOOLTIP_RENDER,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_RENDER),
      false);
   
   	/** The "Refresh" action used in menus. and toolbars **/
      private ActionRenderOrRearrange rearrange = new ActionRenderOrRearrange(this, 
      GUIConstants.NAME_REARRANGE,
      GUIConstants.TOOLTIP_REARRANGE,
      KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK),
      new ImageIcon(GUIConstants.PATH_BUTTONS_PICS +  GUIConstants.PIC_REARRANGE),
      true);
   
   
   
   	/** Returns an instance of ToolBar Window (create it if necessary).<br>
   	* It is then not allowed to run several instances of <code>ToolBarWindow</code> at the same time.
   	@return - the main window of EasyMol	
   	**/
      public static ToolBarWindow getToolBarWindow() {
         if (theOnlyOne == null) {
            theOnlyOne = new ToolBarWindow();
            }
         else System.out.println(GUIConstants.INFO_ALREADY_ONE_TBWIN);
         return theOnlyOne;
      
         }
   
   
   	/** The protected constructor of the Toolbar Window.
   	 **/
      public ToolBarWindow() {
         setJMenuBar(buildMenu());
         getContentPane().add(buildToolBar());
         setActionsState(false);
         currentCslWin.setMother(this);
         setTitle(GUIConstants.TITLE_TB_WIN);
         setSize(GUIConstants.SCREEN_WIDTH, GUIConstants.HEIGHT_TB);
         setLocation(GUIConstants.DESKTOP_UPPER_LEFT_CORNER);
         setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
         addWindowListener(
               new WindowAdapter(){
                  LinkedList winList = ToolBarWindow.this.getOpenedWins();
                  ConsoleWindow csl = ToolBarWindow.this.getConsoleWindow();
               
                  public void windowClosing(WindowEvent e){
                     LibGUI.allSaveCloseQuit(ToolBarWindow.this, true, true);
                     }
               
                  public void windowIconified(WindowEvent e){
                     for (int i=0; i< winList.size(); i++ ){ //Parsing the list of opened windows
                        VisualisationWindow oneAmongOthers = (VisualisationWindow) winList.get(i);
                        oneAmongOthers.setState(Frame.ICONIFIED);//Iconifying each VisualisationWindow
                        }
                     csl.setState(Frame.ICONIFIED);
                     ToolBarWindow.this.setState(Frame.ICONIFIED);}
               
                  public void windowDeiconified(WindowEvent e){
                     for (int i=0; i< winList.size(); i++ ){ //Parsing the list of opened windows
                        VisualisationWindow oneAmongOthers = (VisualisationWindow) winList.get(i);
                        oneAmongOthers.setState(Frame.NORMAL);//Deiconifying each VisualisationWindow
                        }
                     csl.setState(Frame.NORMAL);
                     ToolBarWindow.this.setState(Frame.NORMAL);}
                  }
            );
      
         show();}
   
   /** Gets the <code>ConsoleWindow</code>.
   *   @return the <code>ConsoleWindow</code> in which messages will be written.
   **/
      public ConsoleWindow getConsoleWindow() { 
         return currentCslWin;}
   
   /** Sets the <code>ConsoleWindow</code>.
   *   @param csl the <code>ConsoleWindow</code> in which messages will be written.
   **/
      public void setConsoleWindow(ConsoleWindow csl) { 
         currentCslWin = csl;}
   
   /** Check/Uncheck the check box in the "console" menu.
   *   @param state the state of the check box.
   **/
      public void toggleConsoleMenuCheckedBox(boolean state) {
         console.setState(state);}
   
   /** Gets the size of the list of opened windows.
   *   @return the number of windows opened.
   **/
      public int getOpenedWinsSize(){
         return openedWins.size();}
   
   /** Gets the list of opened windows.
   *   @return the <code>LinkedList</code> of opened windows.
   **/
      public LinkedList getOpenedWins(){
         return openedWins;}
   
   /** Adds a new window to the list of opened windows.
   *   @param visu the new window to add to the list of opened ones.
   **/
      public void addOpenedWindow(VisualisationWindow visu){
         openedWins.add(visu);}
   
   /** Remove  window from the list of opened windows.
   *   @param visu the window to be removed from the list of opened ones.
   **/
      public void removeOpenedWindow(VisualisationWindow visu){
         openedWins.remove(visu);}
   
   /** Gets the focused <code>VisualisationWindow</code>.
   *   @return the current focused window.
   **/
      public VisualisationWindow getFocusedWindow() {
         return currentFocused;}
   
   
   /** Sets the focused <code>VisualisationWindow</code>.
   *   @param visu a <code>VisualisationWindow</code> which is focused.
   **/
      public void setFocusedWindow(VisualisationWindow visu) { 
         currentFocused = visu;}
   
   /** Get the "New" action used in toolbars and menus.
   *   @return the "New" action used in toolbars and menus.
   **/
      public ActionNew getNewAction() {
         return newFile;}
   
   
   /** Get the "Load" action used in toolbars and menus.
   *   @return the "Load" action used in toolbars and menus.
   **/              
      public ActionLoad getLoadAction() {
         return loadFile;}
   
   
   /** Get the "Save" action used in toolbars and menus. 
   *   @return the "Save" action used in toolbars and menus.
   **/
      public ActionSave getSaveAction() {
         return save;}
   
   
   /** Get the "Save As" action used in toolbars and menus. 
   *   @return the "Save As" action used in toolbars and menus.
   **/
      public ActionSaveAs getSaveAsAction() {
         return saveAs;}
   
   
   /** Get the "Save All" action used in toolbars and menus. 
   *   @return the "Save All" action used in toolbars and menus.
   **/
      public ActionSaveAll getSaveAllAction() {
         return saveAllFiles;}
   
   
   /** Get the "Close All" action used in toolbars and menus. 
   *   @return the "Close All" action used in toolbars and menus.
   **/
      public ActionCloseAll getCloseAllAction() {
         return closeAllFiles;}
   
   
   /** Get the "Quit" action used in toolbars and menus. 
   *   @return the "Quit" action used in toolbars and menus.
   **/
      public ActionQuit getQuitAction() {
         return quit;}
   
   /** Get the "Cut" action used in toolbars and menus. 
   *   @return the "Cut" action used in toolbars and menus.
   **/
      public ActionCut getCutAction() {
         return cut;}
   
   /** Get the "Copy" action used in toolbars and menus. 
   *   @return the "Copy" action used in toolbars and menus.
   **/
      public ActionCopy getCopyAction() {
         return copy;}
   
   /** Get the "Paste" action used in toolbars and menus. 
   *   @return the "Paste" action used in toolbars and menus.
   **/
      public ActionPaste getPasteAction() {
         return paste;}
   
   
   /** Get the "Add Carbon Atom" action used in toolbars and menus. 
   *   @return the "Add Carbon Atom" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveAtom getAddCarbonAction() {
         return addCarbon;}
   
   /** Get the "Add Hydrogen Atom" action used in toolbars and menus. 
   *   @return the "Add Hydrogen Atom" action used in toolbars and menus.
   **/              
      public ActionAddOrRemoveAtom getAddHydrogenAction() {
         return addHydrogen;}
   
   /** Get the "Add Nitrogen Atom" action used in toolbars and menus. 
   *   @return the "Add Nitrogen Atom" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveAtom getAddNitrogenAction() {
         return addNitrogen;}
   
   /** Get the "Add Oxygen Atom" action used in toolbars and menus. 
   *   @return the "Add Oxygen Atom" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveAtom getAddOxygenAction() {
         return addOxygen;}
   
   /** Get the "Add Single Bond" action used in toolbars and menus. 
   *   @return the "Add Single Bond" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveBond getAddSgBondAction() {
         return addSingle;}
   
    /** Get the "Add Double Bond" action used in toolbars and menus. 
   *   @return the "Add Double Bond" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveBond getAddDbBondAction() {
         return addDouble;}
   
   /** Get the "Add Triple Bond" action used in toolbars and menus. 
   *   @return the "Add Triple Bond" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveBond getAddTrBondAction() {
         return addTriple;}
   
    /** Get the "Render" action used in toolbars and menus. 
   *   @return the "Render" action used in toolbars and menus.
   **/
      public ActionRenderOrRearrange getRenderAction() {
         return render;}
   
    /** Get the "Rearrange" action used in toolbars and menus. 
   *   @return the "Rearrange" action used in toolbars and menus.
   **/
      public ActionRenderOrRearrange getRearrangeAction() {
         return rearrange;}
   
   /** Get the "Remove Atom" action used in toolbars and menus. 
   *   @return the "Remove Atom" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveAtom getRemoveAtomAction() {
         return removeAtom;}
   
   /** Get the "Remove Bond" action used in toolbars and menus. 
   *   @return the "Reamove Bond" action used in toolbars and menus.
   **/
      public ActionAddOrRemoveBond getRemoveBondAction() {
         return removeBond;}
   
   /** Sets the state of all actions related to molecule edition.<br>
   *   These are Cut, Copy, Paste, Save, SaveAs, Save All and CloseAll.
   *   It also includes Adding and Removing atoms and Bonds, along with Rendering  Refreshing the views.
   *   @param state the state of the <code>Actions</code>.
   **/
      public void setActionsState(boolean state){
         //Edit actions
         getCutAction().setEnabled(state);
         getCopyAction().setEnabled(state);
         getPasteAction().setEnabled(state);
      
      	// File actions
         getSaveAction().setEnabled(state);
         getSaveAsAction().setEnabled(state);
         getSaveAllAction().setEnabled(state);
         getCloseAllAction().setEnabled(state);
      
      	//Atom actions
         getAddCarbonAction().setEnabled(state);
         getAddHydrogenAction().setEnabled(state);
         getAddNitrogenAction().setEnabled(state);
         getAddOxygenAction().setEnabled(state);
         getRemoveAtomAction().setEnabled(state);
      
      	//Bonds actions
         getAddSgBondAction().setEnabled(state);
         getAddDbBondAction().setEnabled(state);
         getAddTrBondAction().setEnabled(state);
         getRemoveBondAction().setEnabled(state);
      
      	//Views actions
         getRenderAction().setEnabled(state);
         getRearrangeAction().setEnabled(state);
         }
   
   
   
   /** Builds the menu that will be put in the ToolBarWindow.
   *   @return the newly built menu.
   **/
      private JMenuBar buildMenu() {
         JMenuBar myBar = new JMenuBar();
      
      //The File Menu
         JMenu file = new JMenu(GUIConstants.TITLE_MENU_FILE);
         file.setMnemonic(java.awt.event.KeyEvent.VK_F);
         file.add(getNewAction());
      
         file.addSeparator();
         file.add(getLoadAction());
         file.add(getSaveAction());
         file.add(getSaveAsAction());
      
         file.addSeparator();
         file.add(getSaveAllAction());
         file.add(getCloseAllAction());
      
         file.addSeparator();
         file.add(getQuitAction());
      
      	//The Edit Menu
         JMenu edit = new JMenu(GUIConstants.TITLE_MENU_EDIT);
         edit.setMnemonic(java.awt.event.KeyEvent.VK_E);
         edit.add(getCutAction());
         edit.add(getCopyAction());
         edit.add(getPasteAction());
         edit.addSeparator();
         edit.add(getRemoveAtomAction());
         edit.add(getRemoveBondAction());
      
      	// The Insert Menu
         JMenu insert = new JMenu(GUIConstants.TITLE_MENU_INSERT);
         insert.setMnemonic(java.awt.event.KeyEvent.VK_I);
         insert.add(getAddCarbonAction());
         insert.add(getAddHydrogenAction());
         insert.add(getAddOxygenAction());
         insert.add(getAddNitrogenAction());
      
         insert.addSeparator();
      
         insert.add(getAddSgBondAction());
         insert.add(getAddDbBondAction());
         insert.add(getAddTrBondAction());
      
      // The Windows Menu
         JMenu windows = new JMenu(GUIConstants.TITLE_MENU_WINDOWS);
         windows.setMnemonic(java.awt.event.KeyEvent.VK_W);
         toggleConsoleMenuCheckedBox(false);
         windows.add(console);
         windows.addSeparator();
         windows.add(getRenderAction());
         windows.add(getRearrangeAction());
      
      
      //Implementation of EventsListeners for the Windows Menu
      
         console.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     if (console.getState()) {
                        if (!currentCslWin.isVisible()) 
                           currentCslWin.show();
                        }
                     else
                        currentCslWin.hide();
                     }
                  }
            );
      
      
      
      // The Help Menu
         JMenu helpmenu = new JMenu(GUIConstants.TITLE_MENU_HELP);
         JMenuItem help = new JMenuItem(GUIConstants.TITLE_MENU_ITEM_HELP);
         helpmenu.add(help);
         JMenuItem about = new JMenuItem(GUIConstants.TITLE_MENU_ITEM_ABOUT);
         helpmenu.add(about);
      
      //Implementation of EventsListeners for the Help Menu
         about.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     aboutScreen().show();
                     }
                  }
            );
      
      
      
      //Add all these menus. to the menu bar and return 
      
         myBar.add(file);
         myBar.add(edit);
         myBar.add(insert);
         myBar.add(windows);
         myBar.add(helpmenu);
      
         return myBar;
      
         }
   
   
   /** Builds the toolbar that will be put in the main easymol window.
   *   @return the newly built toolbar.
   **/
      private JToolBar buildToolBar() {
         JToolBar tbTb = new JToolBar();
         tbTb.add(getNewAction());
         tbTb.addSeparator();
         tbTb.add(getLoadAction());
         tbTb.add(getSaveAction());
         tbTb.add(getSaveAsAction());
         tbTb.addSeparator();
         tbTb.add(getSaveAllAction());
         tbTb.add(getCloseAllAction());
         tbTb.addSeparator();
         tbTb.add(getQuitAction());
      
         return tbTb;
         }
   
   
   /** Returns a Jframe containing the about stuff of EasyMol.
   *   @return the newly built "about" window.
   **/
      private JFrame aboutScreen() {
      
      	//For compatibility with inner class
         final JFrame aboutScreen = new JFrame(GUIConstants.TITLE_ABOUT);
      
      	//Setting the usual JFrame parameters
         aboutScreen.setSize(760,350);
         aboutScreen.setLocation((GUIConstants.SCREEN_WIDTH - aboutScreen.getWidth())/2, (GUIConstants.SCREEN_HEIGHT - aboutScreen.getHeight())/2);
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      
      	//Building the panel that will hold everything (using the ContentPane)
         JPanel aboutContainer = (JPanel) aboutScreen.getContentPane();
         aboutContainer.setLayout(new BorderLayout());
      
      	//The Disclaimer and the Image will be put in a Jlabel
      
         JLabel disclaimerAndImg = new JLabel(GUIConstants.DISCLAIMER,new ImageIcon(GUIConstants.PATH_SPLASH_SCREEN + GUIConstants.NAME_SPLASH),SwingConstants.CENTER);
         disclaimerAndImg.setBorder(BorderFactory.createEtchedBorder());
      
      	//We then add the JLabel at the center of the ContentPane
         aboutContainer.add(disclaimerAndImg,BorderLayout.CENTER);
      
         //The row of buttons will be built using a box container
         Box buttons = new Box(BoxLayout.X_AXIS);
         JButton gpl = new JButton(GUIConstants.TITLE_BUTTON_GPL);
         buttons.add(gpl);
         buttons.add(Box.createHorizontalGlue()); //Adding a glue component to tell the space where to go :)
         JButton close = new JButton(GUIConstants.TITLE_BUTTON_ABOUT_CLOSE);
         buttons.add(close);
         aboutContainer.add(buttons, BorderLayout.SOUTH);
      
      	//Implementing the listeners
         close.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     aboutScreen.dispose();
                     }
                  }
            );
      
         gpl.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     JFrame gpl = new JFrame(GUIConstants.TITLE_GPL);
                     gpl.setSize(550,640);
                     gpl.setLocation((GUIConstants.SCREEN_WIDTH - gpl.getWidth())/2, (GUIConstants.SCREEN_HEIGHT - gpl.getHeight())/2);
                     JEditorPane gpltext = new JEditorPane();
                     gpltext.setEditable(false);
                     try {
                        gpltext.setPage((new File(GUIConstants.PATH_GPL)).toURL());
                        } 
                        catch (IOException ex) {
                           getConsoleWindow().write(GUIConstants.ERR_GPL_LOAD, true);
                           }
                  
                     JScrollPane	liftsAndText = new JScrollPane(gpltext);
                     gpl.setContentPane(liftsAndText);
                     gpl.show();
                     }
                  }
            );
      
      
         return aboutScreen;
         }
   
   
   /** Secured accessor to the path field of the <code>VisualisationWindow</code>.
   *   @return the path to the <code>VisualisationWindow</code> molecule if there is one, an empty <code>String/code> otherwise.
   **/
      public String getVisuWinPath(){
         if (getFocusedWindow() == null)
            return "";
         else
            return getFocusedWindow().getPath();}
   
      }
