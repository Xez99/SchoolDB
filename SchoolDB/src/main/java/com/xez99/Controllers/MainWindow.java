package com.xez99.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Controller for MainWindow.fxml
 * @author https://github.com/Xez99
 * @version 0.1.0
 */
public class MainWindow {
    /** Controller logger */
    private static final Logger logger = LogManager.getLogger(MainWindow.class.getName());

    /** Store preferences of application */
    private Preferences preferences;
    /**
     * This method set preferences of App in controller, fill textFields and request focus
     * @param preferences preferences of application
     */
    public void setPreferences(Preferences preferences){
        this.preferences = preferences;
    }

    /**
     * Edit button method
     * @param event button clicked event
     */
    @FXML
    void edit(ActionEvent event) {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("Views/EditWindow.fxml")
            );
            Parent root = loader.load();
            stage.getScene().setRoot(root);
            stage.setMinWidth(650);
            stage.setMinHeight(550);
            ((MainWindow)loader.getController()).setPreferences(preferences);
            stage.show();
        }catch (IOException ioe){
            logger.error("Can't initialize EditWindow.fxml");
        }
    }

    /**
     * Info button method
     * @param event button clicked event
     */
    @FXML
    void info(ActionEvent event) {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("Views/InfoWindow.fxml")
            );
            Parent root = loader.load();
            stage.getScene().setRoot(root);
            stage.setMinWidth(400);
            stage.setMinHeight(600);
            ((MainWindow)loader.getController()).setPreferences(preferences);
            stage.show();
        }catch (IOException ioe){
            logger.error("Can't initialize InfoWindow.fxml");
        }
    }
}
