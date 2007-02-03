/*
 * Created on 1 nov. 2004
 *
 * TODO Document
 */
package fr.emn.easymol.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import fr.emn.easymol.algorithms.SymbolicTranslatorAlgorithm;
import fr.emn.easymol.main.EasyMolMainWindow;
import fr.emn.easymol.main.Molecule3DWindow;
import fr.emn.easymol.ui.components.IconProvider;
import fr.emn.easymol.ui.components.Molecule3DPane;

/**
 * @author avaughan
 */
public class Display3DViewAction extends AbstractAction {

    private EasyMolMainWindow mainWin = null;

    private Molecule3DWindow currentView = null;

    public Display3DViewAction(EasyMolMainWindow mainWin) {
        this.mainWin = mainWin;
        putValue(Action.SMALL_ICON, IconProvider.getInstance().getIconByName("render"));
        putValue(Action.NAME, "Display 3D View");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R,
                ActionEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
		// FIXME move to mainwindow ? - mune
        if (currentView != null) {
            currentView.dispose();
        }
        if (mainWin.getCurrentPane() == null)
        	return;
        SymbolicTranslatorAlgorithm algo = new SymbolicTranslatorAlgorithm();
        algo.setData(mainWin.getCurrentPane().getMolecule());
        algo.setTranslationType(SymbolicTranslatorAlgorithm.TO_SYMBOL);
        algo.compute();
        Molecule3DPane pane = new Molecule3DPane(mainWin.getCurrentPane()
                .getMolecule());
        mainWin.getCurrentPane().getMolecule().clearObservers();
        mainWin.getCurrentPane().getMolecule().addObserver(pane);
        currentView = new Molecule3DWindow(pane);
        currentView.setVisible(true);

    }

}
