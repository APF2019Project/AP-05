package Scenes.CustomCardSubclasses;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class CustomCardSubclassesController implements Controller {
    @FXML
    private VBox buttonBox;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show classes", null);
    }

    @Override
    public void initializeReOpen() throws IOException {

    }

    public void showClasses(Object object) throws IOException {
        JSONArray jsonArray = (JSONArray) object;
        for(Object obj : jsonArray) {
            String name = (String) obj;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                    "Scenes/CustomCardSubclasses/MemberInCustomCardSubclasses.fxml", jsonObject);
            Platform.runLater(() -> buttonBox.getChildren().add(borderPane));
        }
    }
}
