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
        Parent parent=FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Plant Vs Zombies");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Files/icon.jpg")));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
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
