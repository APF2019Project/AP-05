package Scenes.Chat;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ChatSceneController implements Controller, Refreshable {
    @FXML
    private VBox chatBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField textField;

    @FXML
    private SplitPane replyPane;
    @FXML
    private Label replyUsernameLabel, replyContentLabel;

    @FXML
    void onRemoveReplyButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("remove reply", null);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @FXML
    void onSendButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", textField.getText());
        textField.setText("");
        MenuHandler.getClient().send("send message", jsonObject);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        sendLoadRequest();
    }

    @Override
    public void initializeReOpen() throws IOException {
        sendLoadRequest();
    }

    public void showChat(Object object) {
        JSONObject chat = (JSONObject) object;
        JSONArray jsonArray = (JSONArray) chat.get("chat");
        ArrayList<BorderPane> arrayList = new ArrayList<>();
        for (Object otherObject : jsonArray) {
            JSONObject jsonObject = (JSONObject) otherObject;
            try {
                BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                        "Scenes/Chat/repliedMemberInChat.fxml", jsonObject);
                arrayList.add(borderPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            if (chat.containsKey("replyMessage")) {
                JSONObject jsonObject = (JSONObject) chat.get("replyMessage");
                replyPane.setVisible(true);
                replyUsernameLabel.setText((String) jsonObject.get("senderUsername"));
                replyContentLabel.setText((String) jsonObject.get("content"));
            }
            else
                replyPane.setVisible(false);
            chatBox.getChildren().clear();
            chatBox.getChildren().addAll(arrayList);
            scrollPane.setVvalue(1.0);
        });
    }

    @Override
    public void sendLoadRequest() throws IOException {
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
        MenuHandler.getClient().send("show", null);
    }
}
