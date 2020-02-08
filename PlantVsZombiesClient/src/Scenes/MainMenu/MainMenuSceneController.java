package Scenes.MainMenu;

import Helper.Controller;
import Helper.MenuHandler;
import Helper.PrettyLabel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MainMenuSceneController implements Controller {
    @FXML
    private Label title;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onAllUsersButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("show all users", null);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @FXML
    void onPlayButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("play", null);
    }

    @FXML
    void onProfileButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("profile", null);
    }
    @FXML
    void onAllGamesButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("show all games", null);
    }

    @FXML
    void onShopButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("shop", null);
    }

    @Override
    public void initializeReOpen() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {

    }

    @FXML
    public void onCustomButtonMouseClicked() throws IOException {
        MenuHandler.openSceneWithDefaultParameters("select type");
    }
}
