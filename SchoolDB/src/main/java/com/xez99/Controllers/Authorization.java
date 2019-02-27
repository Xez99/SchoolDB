package com.xez99.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.prefs.Preferences;

/**
 * Controller for Authorization.fxml. Also provides connection to DataBase, see {@link Authorization#getConnection()}
 * @author https://github.com/Xez99
 * @version 0.1.0
 */
public class Authorization {

    /** Controller logger */
    private static final Logger logger = LogManager.getLogger(Authorization.class.getName());

    /** Store preferences of application*/
    private Preferences preferences;

    /**
     * This method set preferences of App in controller, fill textFields and request focus
     * @param preferences preferences of application
     */
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;

        //Trying to load parameters from config.properties
        login.setText(preferences.get("login", ""));
        ip.setText(preferences.get("ip", ""));
        port.setText(preferences.get("port", ""));
        SETTINGS = preferences.get("connectionSettings", "useSSL=false&serverTimezone=UTC");

        //Requesting focus on field what needed
        Platform.runLater(() -> (login.getText().equals("") ? login : password).requestFocus());
    }

    /** Store connection to DataBase */
    private static Connection connection;
    /** Store ip address of DataBase server */
    private static String IP;
    /** Store port of DataBase server */
    private static String PORT;
    /** Store user login */
    private static String LOGIN;
    /** Store user password */
    private static String PASSWORD;
    /** Store server connection settings */
    private static String SETTINGS;
    /** Store last SQLException */
    private static SQLException lastSQLException;

    /**
     * This method returns opened connection to DataBase or open new connection if connection does't exist
     * @return connection to DataBase
     * @throws SQLException when can't connect to DataBase via presented log/pass/ip/port/settings
     */
    public static Connection getConnection() throws SQLException{

        if(connection == null || !isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + IP + ':' + PORT + "/SchoolDB?" + SETTINGS, LOGIN, PASSWORD);
            logger.info("Trying to get connection");
        }
        return connection;
    }

    /**
     * This method checks connection to DataBase.
     * @return true if connection work, false if not
     */
    private static boolean isConnected(){
        try{
            return getConnection().createStatement().execute("SELECT 1");
        }catch (SQLException sqle){
            lastSQLException = sqle;
        }catch (NullPointerException ignored){}
        return false;
    }

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;


    /**
     * Connect button method
     * @param event button clicked event
     */
    @FXML
    void connect(ActionEvent event) {
        LOGIN = login.getText();
        PASSWORD = password.getText();
        IP = ip.getText();
        PORT = port.getText();

        if(isConnected()){
            //Saving parameters
            if (preferences != null){
                preferences.put("login", LOGIN);
                preferences.put("ip", IP);
                preferences.put("port", PORT);
            }

            //Load new FXML form
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("Views/MainWindow.fxml")
                );
                Parent root = loader.load();
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("SchoolDB");
                stage.getScene().setRoot(root);
                ((MainWindow)loader.getController()).setPreferences(preferences);
                stage.show();
            }catch (IOException ioe){
                logger.error("Can't initialize MainWindow.fxml");
            }
        }else {
            //Log
            logger.info("Authorization... Access denied");
            //Show alert message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Не удалось подключиться к удаленному серверу!\n"
                    + lastSQLException.getLocalizedMessage()+"\n Код SQL ошибки: " + lastSQLException.getErrorCode());
            alert.showAndWait();
        }
    }

    /**
     * Init method of JavaFX form
     */
    @FXML
    void initialize() {
        //Adding listeners to control max length of fields;
        login.textProperty().addListener(new MaxLengthListener(login, 30));
        password.textProperty().addListener(new MaxLengthListener(password, 30));
        ip.textProperty().addListener(new MaxLengthListener(ip, 12));
        port.textProperty().addListener(new MaxLengthListener(port, 5));
    }

}
