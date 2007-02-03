/*
 * 04/18/2002
 *
 * EasyRun.java - To run EasyMol in text mode
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
	
	package fr.emn.easymol.main;

// Input/output utilities
   import java.io.*;
	
// GUI utilities
   import javax.swing.*;
   import java.awt.event.*;

// EasyMol utilities
   import fr.emn.easymol.core.*;
   import fr.emn.easymol.gui.*;
   import fr.emn.easymol.xml.*;

   public class EasyRun {
      public static void main(String [] args) {
         if (args.length != 0) {
            String fileName = args[0];
            try {
               MoleculeXMLReader mxr = new MoleculeXMLReader(fileName);
               Molecule m = mxr.getMolecule();
               int h = mxr.getHashFirst();
               NavigationWindow nw = new NavigationWindow(m,h);
               JButton closeWin = new JButton("Close");
               nw.getContentPane().add("South", closeWin);
               closeWin.addActionListener(
                     new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                           System.exit(0);
                        }});
            
               nw.show();
            }
               catch (IOException ioe) {
                  System.err.println("Wrong filename");
               }
         }
         else {
            System.out.println("Usage : EasyRun <filename.xml>");
         }
      }
   }