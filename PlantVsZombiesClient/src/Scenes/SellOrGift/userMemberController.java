package Scenes.SellOrGift;

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

public class userMemberController implements Controller {
    @FXML
    private ImageView imageView;
    @FXML
    private Label usernameLabel;

    @FXML
    public void onSendButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("herUserName", usernameLabel.getText());
        jsonObject.put("creatureName", SellOrGiftSceneController.lastSellOrGiftSceneController.getSelectedName());
        MenuHandler.getClient().send("send gift card", jsonObject);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        String username = (String)jsonObject.get("username");
        String imageAddress = (String)jsonObject.get("imageAddress");

        File file = new File(imageAddress);
        Platform.runLater(() -> {
            usernameLabel.setText(username);
            imageView.setImage(new Image(file.toURI().toString()));
        });
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
