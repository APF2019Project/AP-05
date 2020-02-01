package Scenes.Chat;

import Helper.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class MemberInChatController implements Controller {
    @FXML
    private ImageView imageView;
    @FXML
    private Label contentLabel;

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        String imageAddress = (String) jsonObject.get("senderImage");
        String content = (String) jsonObject.get("content");
        System.out.println(imageAddress + "\n" + content);
        Platform.runLater(() -> {
            imageView.setImage(new Image(new File(imageAddress).toURI().toString()));
            contentLabel.setText(content);
        });
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
