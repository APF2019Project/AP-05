package Scenes.Notification;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class NotificationSceneController implements Controller {
    @FXML
    private Label usernameLabel, contentLabel;
    @FXML
    private TextField textField;
    private JSONObject message;
    private Stage stage;

    @FXML
    void onSendButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", textField.getText());
        jsonObject.put("message", message);
        textField.setText("");
        MenuHandler.getClient().send("send notification message", jsonObject);
        stage.close();
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
        message = jsonObject;
        String username = (String) jsonObject.get("senderUsername");
        String content = (String) jsonObject.get("content");
        stage = (Stage) jsonObject.get("stage");
        message.remove("stage");

        Platform.runLater(() -> {
            usernameLabel.setText(username);
            contentLabel.setText(content);
        });
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
