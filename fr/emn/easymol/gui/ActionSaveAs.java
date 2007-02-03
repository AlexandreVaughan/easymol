/*
 * 06/27/2002
 *
 * ActionSaveAs.java - The "Save As" action
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

   import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;


/** The class to register the action "Save As".<br>
*   It uses some methods in the <code>LibGUI</code> to perform its operations.
**/

   public class ActionSaveAs extends AbstractAction {
      private ToolBarWindow tb;
   
   /** Constructs an <code>AbstractAction</code> and sets additional fields. 
   *   @param toolB the <code>ToolBarWindow</code> in which this action will be created.
   *   @param name the label that will be put in menus.
   *   @param toolTip the tooltip which will appear on the buttons or in the menus.
   *   @param accelerator the keyboard shortcut.
   *   @param icon the beautiful icon for the button and the menu item.
   **/
      public ActionSaveAs(ToolBarWindow toolB, String name, String toolTip, KeyStroke accelerator, Icon icon){
         super(name,icon);
         super.putValue(Action.SHORT_DESCRIPTION, toolTip); 
         super.putValue(Action.ACCELERATOR_KEY, accelerator);
         tb = toolB;
         }
   
   /** The action connected to item "Save As" in menus or toolbars.
   *   @param e the <code>ActionEvent</code> fired by the listener that will be registered by the action.
   **/
      public void actionPerformed(ActionEvent e){
         LibGUI.save(tb.getFocusedWindow(), tb, false);
         }
   
      }