package Scenes.Shop;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ShopSceneController implements Controller {
    @FXML
    private VBox creatureList;
    @FXML
    private Label moneyLabel;
    @FXML
    private Button backButton;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @FXML
    void initialize() throws IOException {
    }

    @Override
    public void initializeReOpen() {
    }

    public void money(Object object) {
        moneyLabel.setText("You have " + object + "$");
    }

    public void showShop(Object object) throws IOException {
        JSONArray jsonArray = (JSONArray) object;
        for (Object objectForCreature : jsonArray) {
            JSONObject jsonObjectForCreature = (JSONObject) objectForCreature;
            jsonObjectForCreature.put("bought", false);
            BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                    "Scenes/Shop/MemberInShopList.fxml", jsonObjectForCreature);
            Platform.runLater(() -> {
                creatureList.getChildren().add(borderPane);
            });
        }
    }

    public void showCollection(Object object) throws IOException {
        JSONArray jsonArray = (JSONArray) object;
        for (Object objectForCreature : jsonArray) {
            JSONObject jsonObjectForCreature = (JSONObject) objectForCreature;
            jsonObjectForCreature.put("bought", true);
            BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                    "Scenes/Shop/MemberInShopList.fxml", jsonObjectForCreature);
            Platform.runLater(() -> {
                creatureList.getChildren().add(borderPane);
            });
        }
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("money", null);
        MenuHandler.getClient().send("show collection", null);
        MenuHandler.getClient().send("show shop", null);
    }
}
