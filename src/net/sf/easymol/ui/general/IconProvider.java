/**
 * Singleton icon provider
 *
 */
package net.sf.easymol.ui.general;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * @author stefano borini - munehiro
 */
public class IconProvider {

    public static final String ICON_PATH = "resources/Buttons/";

    private static IconProvider _instance = null;

    protected IconProvider() {
        // perform preloading ?
    }

    public static IconProvider getInstance() {
        if (_instance == null) {
            _instance = new IconProvider();
        }
        return _instance;
    }

    public ImageIcon getIconByName(String name) {
        File file = new File(ICON_PATH + name + ".gif");
        if (file.exists())
            return new ImageIcon(ICON_PATH + name + ".gif");
        else
            return new ImageIcon(ICON_PATH + name + ".png");
    }

}
