import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class shopSceneController implements Controller {
    @FXML
    private VBox creatureList;
    @FXML
    private Button backButton;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu",null);
        MenuHandler.closeScene();
    }

    @FXML
    void initialize() throws IOException {
        //scrollPane.setStyle("-fx-background: rgb(255,255,255);\n -fx-background-color: rgb(255,255,255)");
        MenuHandler.getClient().send("show shop", null);
    }

    @Override
    public void initializeReOpen() {
    }

    public void showShop(Object object) throws IOException {
        JSONArray jsonArray = (JSONArray) object;
        for (Object objectForCreature : jsonArray) {
            JSONObject jsonObjectForCreature = (JSONObject) objectForCreature;
            BorderPane borderPane=MenuHandler.getPaneWithDefaultParametersHandler("memberInShopList.fxml", jsonObjectForCreature);
            Platform.runLater(()->{
                creatureList.getChildren().add(borderPane);
            });
        }
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
    }
}
