import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MenuHandler.setStage(primaryStage);
        MenuHandler.openScene("first");
        MediaPlayer.playBackgroundMusic();
        primaryStage.setOnCloseRequest(windowEvent -> {
            MediaPlayer.closeBackgroundMusic();
            System.exit(0);
        });
    }


    public static void main(String[] args) throws CannotRealizeException, IOException, NoPlayerException {
        launch(args);
    }
}
