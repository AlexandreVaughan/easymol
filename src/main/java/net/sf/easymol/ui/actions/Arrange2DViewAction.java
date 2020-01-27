/*
 * Created on 9 juin 2005
 */
package net.sf.easymol.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import net.sf.easymol.main.EasyMolMainWindow;
import net.sf.easymol.ui.general.IconProvider;




/**
 * @author ava
 */
public class Arrange2DViewAction extends AbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private EasyMolMainWindow mainWin = null;

    public Arrange2DViewAction(EasyMolMainWindow mainWin) {
        this.mainWin = mainWin;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName(
                "arrange")); 
        putValue(Action.NAME, "Arrange 2D View");
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME)); 
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T,
                ActionEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        // FIXME move to an appropiate method in mainwindow? - mune
        if (mainWin.getCurrentPane() == null)
            return;

//        JGraphLayoutAlgorithm layout = new SpringEmbeddedLayoutAlgorithm();
//        //JGraphLayoutSettings settings = layout.createSettings();
//        JGraphLayoutAlgorithm
//                .applyLayout(mainWin.getCurrentPane().getGraph(), layout,
//                        mainWin.getCurrentPane().getGraph().getRoots());

    }

}
