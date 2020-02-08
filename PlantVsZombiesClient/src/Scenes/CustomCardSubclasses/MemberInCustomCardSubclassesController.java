package Scenes.CustomCardSubclasses;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MemberInCustomCardSubclassesController implements Controller {
    @FXML
    private Button subclassButton;
    @FXML
    void onButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", subclassButton.getText());
        MenuHandler.getClient().send("select", jsonObject);
    }
    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        Platform.runLater(() -> subclassButton.setText((String) jsonObject.get("name")));
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
