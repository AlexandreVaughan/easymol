package net.sf.easymol.ui.general;

import javax.swing.*;

/**
 * Simple dialog that show a message.
 * 
 * @author locusf
 */

public class EasyMolDialog {
    /**
     * Construct a dialog with specified string
     * 
     * @param s
     *            A string to show
     */
    public EasyMolDialog(String s) {
        JOptionPane.showMessageDialog(null, s, "EasyMol",
                JOptionPane.ERROR_MESSAGE);

    }

    public EasyMolDialog(String s, String title) {
        JOptionPane
                .showMessageDialog(null, title,  s,JOptionPane.ERROR_MESSAGE);

    }
}