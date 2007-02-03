/*
 * 06/27/2002
 *
 * XMLFileFilter.java - A FileFilter for XML files
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

/** The class to filter XML files in <code>JFileChooser</code>s.**/

   public class XMLFileFilter extends javax.swing.filechooser.FileFilter {
   
   /** If file is accepted through the filter
   *   @param f a file
   **/
      public boolean accept(File f){
         if (f.isDirectory())
            return true;
      
         return LibGUI.isFileTypeCorrect(f);
         }
   
   /** Gets the description of the file type
   *   @return the description of the fiels accepted by the filter i.e XML files
   **/
      public String getDescription(){
         return "XML files";}
   
      }
