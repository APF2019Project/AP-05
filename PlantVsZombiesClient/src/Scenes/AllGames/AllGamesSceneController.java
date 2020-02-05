package Scenes.AllGames;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AllGamesSceneController implements Controller, Refreshable {
    @FXML
    private VBox gamesBox;

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

    public void showAllGames(Object object) {
        JSONArray jsonArray = (JSONArray) object;
        ArrayList<AnchorPane> arrayList = new ArrayList<>();
        for (Object object1 : jsonArray) {
            JSONObject jsonObject = (JSONObject) object1;
            try {
                AnchorPane anchorPane = MenuHandler.getAnchorPaneWithDefaultParametersHandler(
                        "Scenes/AllGames/GameInAllGames.fxml", jsonObject);
                arrayList.add(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            gamesBox.getChildren().clear();
            gamesBox.getChildren().addAll(arrayList);
        });
    }

    @Override
    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("show all games", null);
    }
}
