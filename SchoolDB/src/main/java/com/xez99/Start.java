package com.xez99;

import com.xez99.Controllers.Authorization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * com.xez99.Start class for launching application
 * @author https://github.com/Xez99
 * @version 0.1.0
 */
public class Start extends Application {

    /** Store preferences of application */
    private Preferences preferences;

    /** Root logger */
    private static final Logger logger = LogManager.getLogger(Start.class.getName());

    /**
     * Launching start method of GUI
     * @param args launching args (ignores)
     */
    public static void main(String[] args) {launch(args);}

    /**
     * Launch GUI method
     * @param stage main stage of application
     */
    @Override
    public void start(Stage stage){

        // Initialise preferences path
        preferences = Preferences.userRoot().node("SchoolDB");

        // Now pressing ENTER on focused button will "click" it
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER
                            && stage.getScene().getFocusOwner().getClass() == Button.class)
                ((Button) stage.getScene().getFocusOwner()).fire();
        }));

        //Load FXML form
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("Views/Authorization.fxml")
            );
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            ((Authorization)loader.getController()).setPreferences(preferences);
            stage.setTitle("Авторизация");
            stage.setMinWidth(450);
            stage.setMinHeight(350);
            stage.show();
        }catch (IOException ioe){
            logger.fatal("Can't initialize Authorization.fxml and launch application");
        }
    }
}