package Scenes.AllUsers;

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

public class MemberInAllUsersController implements Controller {
    @FXML
    private ImageView imageView;
    @FXML
    private Label usernameLabel;

    @FXML
    public void onChatButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", usernameLabel.getText());
        MenuHandler.getClient().send("enter chat", jsonObject);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        String username = (String)jsonObject.get("username");
        String imageAddress = (String)jsonObject.get("imageAddress");

        Platform.runLater(() -> {
            usernameLabel.setText(username);
            imageView.setImage(new Image(new File(imageAddress).toURI().toString()));
        });
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
