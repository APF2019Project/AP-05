package Scenes.AllUsers;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AllUsersSceneController implements Controller, Refreshable {
    @FXML
    private VBox usersBox;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }
    @FXML
    void onGlobalChatButtonMouseClicked(MouseEvent mouseEvent) throws IOException {
        MenuHandler.getClient().send("enter global chat", null);
    }
    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        sendLoadRequest();
    }

    @Override
    public void initializeReOpen() throws IOException {
        sendLoadRequest();
    }

    public void showAllUsers(Object object) {
        JSONArray jsonArray = (JSONArray) object;
        ArrayList<BorderPane> arrayList = new ArrayList<>();
        for (Object object1 : jsonArray) {
            JSONObject jsonObject = (JSONObject) object1;
            try {
                BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                        "Scenes/AllUsers/MemberInAllUsers.fxml", jsonObject);
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
        MenuHandler.getClient().send("show all users", null);
    }
}
