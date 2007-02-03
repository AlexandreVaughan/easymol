 /*
 * 06/27/2002
 *
 * ConsoleWindow.java - EasyMol Console
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
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/** The class to implement the console of EasyMol.<br><br>
*   A reference to the <code>ToolBarWindow</code> is kept.<br>
*   The messages are held in a JList. We use a DefaultListModel because we only need a basic implementation of dynamic JList behaviour.<br>
*   Moreover, the DefaultListModel provides all the Vector APIs so it's easy to use...
**/

   public class ConsoleWindow extends JFrame implements GUIConstants{
      private static int CAPACITY = 100;
      private static ToolBarWindow tbWin;
      private static DefaultListModel messages = new DefaultListModel();
   	// The Jlist that will hold our messages
      private static JList textZone = new JList(messages);
   
   
   /** Sets the reference to the mother window of the current <code>ConsoleWindow</code>.
   *   @param tb the <code>ToolBarWindow</code> you want to be the mother of the <code>ConsoleWindow</code>.
   **/
      public void setMother(ToolBarWindow tb){
         tbWin = tb;}
   
   /** Gets the reference to the mother window.
   *   @return the mother window of the <code>ConsoleWindow</code>.
   **/
      public ToolBarWindow getMother() {
         return tbWin; }
   
   
   /** Constructs a ConsoleWindow. **/
      public ConsoleWindow() {
         buildTextZoneAndButton();
         setTitle(GUIConstants.TITLE_CONSOLE);
         setSize(GUIConstants.SCREEN_WIDTH, GUIConstants.HEIGHT_CONSOLE);
         setLocation(0,GUIConstants.SCREEN_HEIGHT - GUIConstants.HEIGHT_CONSOLE);
         addWindowListener( 
               new WindowAdapter() {
                  public void windowClosing(WindowEvent e){
                     ConsoleWindow.this.getMother().toggleConsoleMenuCheckedBox(false);
                     }
                  }
            );
         }
   
   
   /** Build the panel in which we will write in the console.
   *   @return the newly built console panel.
   **/
      private JPanel buildTextZoneAndButton() {
      
      	//We ensure the capacity matches the requirements (given by SIZE)
         messages.ensureCapacity(CAPACITY);
         messages.addElement(GUIConstants.INFO_WELCOME);
         JScrollPane liftsAndText = new JScrollPane(textZone);
      	//We then build the Panel that will hold the JScrollPane and the JButton
         JPanel textZoneAndButton = (JPanel) ConsoleWindow.this.getContentPane();
         textZoneAndButton.setLayout(new BorderLayout());
      	//We center the button using a GridLayout
         JPanel buttonZone = new JPanel(new GridLayout(1,1));
         JButton clearButton = new JButton(GUIConstants.TITLE_BUTTON_CONSOLE_CLEAR);
         buttonZone.add(clearButton);
      	//Finally, we put all the components we built in the Panel
         textZoneAndButton.add(liftsAndText,BorderLayout.CENTER);
         textZoneAndButton.add(buttonZone,BorderLayout.SOUTH);
         clearButton.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     ConsoleWindow.this.clear();
                     }
                  }
            );
      
      
      
         return textZoneAndButton;
         }
   
   
   /** Writes in the ConsoleWindow. Makes it visible if disabled. Beeps if it is an error message.
   *   @param text the text to write.
   *   @param error if it is an error message.
   **/
      public void write(String text, boolean error) {
         ConsoleWindow.this.getMother().toggleConsoleMenuCheckedBox(true);
      
         if (!isVisible())
            show();
         if (!messages.isEmpty()) {
            Object lastMessage = messages.lastElement();
            int lastMessagePosition= messages.lastIndexOf(lastMessage);
         
            if (lastMessagePosition == CAPACITY - 1) {
               messages.clear();
               }
            }
         messages.addElement(text);
         if (error) {
            toFront();
            Toolkit.getDefaultToolkit().beep();
            }
         }
   
   /** Removes a line from the console. **/
      public void removeLine() {
         Object lastMessage = messages.lastElement();
         int lastMessagePosition = messages.lastIndexOf(lastMessage);
         messages.remove(lastMessagePosition);
         }
   
   
   /** Clears the console. **/
      public void clear() {
         messages.clear();
         }      
   
   
      }