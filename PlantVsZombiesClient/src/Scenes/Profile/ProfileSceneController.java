package Scenes.Profile;

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

public class ProfileSceneController implements Controller {
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {

    }

    @FXML
    void onDeleteAccountButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("delete user");
    }

    @FXML
    void onRenameButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("rename user");
    }

    @FXML
    void onChangeUserButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("change user");
    }

    @FXML
    void onCreateAccountButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("create account");
    }

    @FXML
    void onChangeProfilePictureButtonMouseClicked() throws IOException {
        MenuHandler.openSceneWithDefaultParameters("change picture");
    }

    public void showUser(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        String username = (String) jsonObject.get("username");
        String imageAddress = (String) jsonObject.get("imageAddress");

        Platform.runLater(() -> {
            usernameLabel.setText(username);
            System.out.println(imageAddress);
            imageView.setImage(new Image(new File(imageAddress).toURI().toString()));
        });
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show", null);
        System.out.println(imageView.getImage().getUrl());
    }

    @Override
    public void initializeReOpen() throws IOException {
        MenuHandler.getClient().send("show", null);
    }
}
