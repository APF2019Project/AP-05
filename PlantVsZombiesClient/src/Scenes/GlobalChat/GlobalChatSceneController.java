package Scenes.GlobalChat;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GlobalChatSceneController implements Controller,Refreshable {
    @FXML
    private VBox chatBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField textField;

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
        JSONArray jsonArray = (JSONArray) object;
        ArrayList<BorderPane> arrayList = new ArrayList<>();
        for(Object otherObject : jsonArray) {
            JSONObject jsonObject = (JSONObject) otherObject;
            try {
                BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                        "Scenes/Chat/MemberInChat.fxml", jsonObject);
                arrayList.add(borderPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            chatBox.getChildren().clear();
            chatBox.getChildren().addAll(arrayList);
            scrollPane.setVvalue(1.0);
        });
    }

    @Override
    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("show chat", null);
    }
}
