import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class shopSceneController implements Controller {
    @FXML
    private VBox creatureList;
    @FXML
    private Button backButton;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.closeScene();
    }

    @Override
    public void initializeReOpen() {
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        JSONArray creatureListJsonArray = (JSONArray) jsonObject.get("creatureList");
        for (Object objectForCreature : creatureListJsonArray) {
            JSONObject jsonObjectForCreature = (JSONObject) objectForCreature;
            creatureList.getChildren().add(MenuHandler.getPaneWithDefaultParametersHandler("memberInShopList.fxml",jsonObjectForCreature));
        }
    }
}
