package Scenes.Chat;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ChatSceneController implements Controller, Refreshable {
    @FXML
    private Label label;

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
    }

    @Override
    public void initializeReOpen() throws IOException {

    }

    @Override
    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("show chat", null);
    }

    public void showChat(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        label.setText((String) jsonObject.get("username"));
    }
}
