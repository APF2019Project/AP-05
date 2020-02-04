package Scenes.Chat;

import Helper.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class MemberInChatController implements Controller {
    @FXML
    private ImageView imageView;
    @FXML
    private Label contentLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label repliedUsernameLabel;
    @FXML
    private Label repliedMessage;
    @FXML
    private HBox topHbox;

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        String imageAddress = (String) jsonObject.get("senderImage");
        String content = (String) jsonObject.get("content");
        String username = (String) jsonObject.get("senderUsername");
        Platform.runLater(() -> {
            imageView.setImage(new Image(new File(imageAddress).toURI().toString()));
            contentLabel.setText(content);
            usernameLabel.setText(username);
            if(jsonObject.containsKey("repliedUsername")) {
                repliedUsernameLabel.setText((String) jsonObject.get("repliedUsername"));
                repliedMessage.setText((String) jsonObject.get("repliedMessage"));
            }
            else
                topHbox.setVisible(false);
        });
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
