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

public class CollectionSceneController implements Controller {
    @FXML
    private VBox creatureList;
    @FXML
    private Label infoLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button startGameButton;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }
    @FXML
    void onStartGameMouseClicked() throws IOException {
        MenuHandler.getClient().send("play", null);
        MenuHandler.closeScene();
    }

    @FXML
    void initialize() throws IOException {
    }

    @Override
    public void initializeReOpen() {
    }

    public void showHand(Object object) throws IOException {
        JSONArray jsonArray = (JSONArray) object;
        Platform.runLater(() -> {
            if (jsonArray.size() < 7) {
                infoLabel.setText("You have to select " + (7 - jsonArray.size()) + " more creatures");
                startGameButton.setVisible(false);
            } else if (jsonArray.size() > 7) {
                infoLabel.setText("You have to unselect " + (jsonArray.size() - 7) + " creatures");
                startGameButton.setVisible(false);
            } else {
                infoLabel.setText("You can start the game now");
                startGameButton.setVisible(true);
            }
        });
        for (Object objectForCreature : jsonArray) {
            JSONObject jsonObjectForCreature = (JSONObject) objectForCreature;
            jsonObjectForCreature.put("selected", true);
            BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler("MemberInCollectionList.fxml", jsonObjectForCreature);
            Platform.runLater(() -> {
                creatureList.getChildren().add(borderPane);
            });
        }
    }

    public void showCollection(Object object) throws IOException {
        JSONArray jsonArray = (JSONArray) object;
        for (Object objectForCreature : jsonArray) {
            JSONObject jsonObjectForCreature = (JSONObject) objectForCreature;
            jsonObjectForCreature.put("selected", false);
            BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler("MemberInCollectionList.fxml", jsonObjectForCreature);
            Platform.runLater(() -> {
                creatureList.getChildren().add(borderPane);
            });
        }
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show hand", null);
        MenuHandler.getClient().send("show collection", null);
    }
}
