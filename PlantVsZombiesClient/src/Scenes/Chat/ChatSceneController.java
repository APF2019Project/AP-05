package Scenes.Chat;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
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
    private Button photoSelectButton;

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
        if (photoSelectButton.getAccessibleText()!=null && !photoSelectButton.getAccessibleText().isEmpty()) {
            jsonObject.put("photoPath", photoSelectButton.getAccessibleText());
        }
        //jsonObject.put("photoPath", photoSelectButton.getAccessibleText());
        textField.setText("");
        MenuHandler.getClient().send("send message", jsonObject);
        photoSelectButton.setAccessibleText("");
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        sendLoadRequest();
    }

    @Override
    public void initializeReOpen() throws IOException {
        sendLoadRequest();
    }

    public void onPhotoSelectButtonAction(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MenuHandler.getCurrentStage());
        if(file!=null && file.exists()) {
            try {
                photoSelectButton.setAccessibleText(file.toURI().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            photoSelectButton.setAccessibleText("");
        }
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
            } else
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

    public void onGunButtonMouseClicked(MouseEvent mouseEvent) {
    }
}
