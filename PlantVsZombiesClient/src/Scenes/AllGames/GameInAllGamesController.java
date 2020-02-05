package Scenes.AllGames;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;


public class GameInAllGamesController implements Controller {
    @FXML
    private ImageView firstPlayerImageView;
    @FXML
    private Label firstPlayerUserName;
    @FXML
    private ImageView secondPlayerImageView;
    @FXML
    private Label secondPlayerUserName;

    @FXML
    public void onWatchGameMouseClicked() throws IOException {

    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        JSONObject plantPlayerObj=(JSONObject) jsonObject.get("plant player");
        JSONObject zombiePlayerObj=(JSONObject) jsonObject.get("zombie player");

        String firstUsername = (String)plantPlayerObj.get("username");
        String firstImageAddress = (String)plantPlayerObj.get("imageAddress");

        String secondUsername = (String)zombiePlayerObj.get("username");
        String secondImageAddress = (String)zombiePlayerObj.get("imageAddress");

        File file1 = new File(firstImageAddress);
        File file2 = new File(secondImageAddress);
        Platform.runLater(() -> {
            firstPlayerUserName.setText(firstUsername);
            secondPlayerUserName.setText(secondUsername);
            firstPlayerImageView.setImage(new Image(file1.toURI().toString()));
            secondPlayerImageView.setImage(new Image(file2.toURI().toString()));
        });
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
