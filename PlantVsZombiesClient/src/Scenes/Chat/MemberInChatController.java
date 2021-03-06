package Scenes.Chat;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class MemberInChatController implements Controller {
    @FXML
    private BorderPane inBorder;
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
    private ImageView dataImageView;
    @FXML
    private HBox topHBox;
    private int id;

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        String imageAddress = (String) jsonObject.get("senderImage");
        String content = (String) jsonObject.get("content");
        String username = (String) jsonObject.get("senderUsername");
        this.id = ((Long) jsonObject.get("id")).intValue();
        Platform.runLater(() -> {
            if(jsonObject.containsKey("photoPath") && jsonObject.get("photoPath")!=null) {
                String dataImageAddress = (String) jsonObject.get("photoPath");
                System.out.println("dataImage"+dataImageAddress);
                Image image=new Image(dataImageAddress);
                if(dataImageView!=null)
                dataImageView.setImage(image);
            }else {
                inBorder.setTop(null);
            }

            System.out.println("SenderImage"+new File(imageAddress).toURI().toString());
            imageView.setImage(new Image(new File(imageAddress).toURI().toString()));
            contentLabel.setText(content);
            usernameLabel.setText(username);
            if(jsonObject.containsKey("repliedUsername")) {
                repliedUsernameLabel.setText((String) jsonObject.get("repliedUsername"));
                repliedMessage.setText((String) jsonObject.get("repliedMessage"));
            }
            else {
                inBorder.setCenter(null);
//                topHbox.setVisible(false);
            }
        });
    }

    @FXML
    private void onReplyButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("replyId", id);
        MenuHandler.getClient().send("reply", jsonObject);
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
