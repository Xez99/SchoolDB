import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Start extends Application {
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new AnchorPane(), 200, 200));
        stage.setTitle("Hello World!");
        stage.show();
    }
}
