package Scenes.PVPLobby;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PVPLobbySceneController implements Controller, Refreshable {
    @FXML
    private VBox usersBox;

    @FXML
    private TextField userNameTextField;

    @FXML
    public void onStartFightMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("enemy username", userNameTextField.getText());
        MenuHandler.getClient().send("enter game", jsonObject);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }
    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        sendLoadRequest();
    }

    @Override
    public void initializeReOpen() throws IOException {
        sendLoadRequest();
    }

    public void showInLobbyUsers(Object object) {
        JSONArray jsonArray = (JSONArray) object;
        ArrayList<BorderPane> arrayList = new ArrayList<>();
        for (Object object1 : jsonArray) {
            JSONObject jsonObject = (JSONObject) object1;
            try {
                BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                        "Scenes/PVPLobby/MemberInAllUsers.fxml", jsonObject);
                arrayList.add(borderPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            usersBox.getChildren().clear();
            usersBox.getChildren().addAll(arrayList);
        });
    }

    @Override
    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("showInLobbyUsers", null);
    }
}
