/*
 * Created on 1 nov. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.components;

import java.awt.Font;

import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * @author avaughan
 *  
 */
public class EasyMolTheme extends DefaultMetalTheme {

    private FontUIResource font = new FontUIResource(new Font("Dialog",
            Font.PLAIN, 11));

    public EasyMolTheme() {
        super();
    }

    public FontUIResource getMenuTextFont() {
        return font;
    }

    public FontUIResource getControlTextFont() {
        return font;
    }

    public FontUIResource getSubTextFont() {
        return font;
    }

    public FontUIResource getSystemTextFont() {
        return font;
    }

    public FontUIResource getUserTextFont() {
        return font;
    }
}