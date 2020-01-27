/*
 * Created on 1 nov. 2004
 *
 */
package net.sf.easymol.main;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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
 
        // Box
        Box testBox = new Box(5, 5, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));
        testBox.setDrawMode(DrawMode.LINE);
 
        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -15));
 
        // Build the Scene Graph
        Group root = new Group();       
        root.getChildren().add(camera);
        root.getChildren().add(testBox);
 
        // Use a SubScene       
        SubScene subScene = new SubScene(root, 300,300);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        return group;
    }
    
    

}