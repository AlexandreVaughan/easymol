/*
 * Created on 1 nov. 2004
 *
 */
package net.sf.easymol.main;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.JFrame;

import net.sf.easymol.ui.comp3d.vsepr.VSEPRMolecule3DPane;


/**
 * @author avaughan
 */
public class Molecule3DWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    

    public Molecule3DWindow(VSEPRMolecule3DPane myPane) {
       final JFXPanel fxPanel = new JFXPanel();
       this.setSize(300, 250);
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
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);
        root.getChildren().add(btn);
        
        return scene;
    }
    
    

}