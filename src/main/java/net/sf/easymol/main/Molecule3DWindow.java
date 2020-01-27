/*
 * Created on 1 nov. 2004
 *
 */
package net.sf.easymol.main;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JFrame;
import net.sf.easymol.ui.comp3d.IMolecule3DPane;

import net.sf.easymol.ui.comp3d.vsepr.VSEPRMolecule3DPane;


/**
 * @author avaughan
 */
public class Molecule3DWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    IMolecule3DPane internalPane;
    

    public Molecule3DWindow(VSEPRMolecule3DPane myPane) {
       final JFXPanel fxPanel = new JFXPanel();
       internalPane = myPane;
       this.setSize(300, 300);
       add(fxPanel);
       
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }
    
    
    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private Scene createScene() {

        Scene scene = new Scene(createContent());
        
        
        return scene;
    }
    
    public Parent createContent() {

        return internalPane.constructScene();

    }
    
    

}