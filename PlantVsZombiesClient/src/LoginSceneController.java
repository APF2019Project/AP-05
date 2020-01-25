import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class LoginSceneController {
    @FXML
    private TextField textField0, textField1;
    @FXML
    private Label title;
    @FXML
    private Button button;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onLoginButtonMouseClicked() throws IOException {
        JSONObject messageJsonObject = new JSONObject();
        messageJsonObject.put("action", button.getText().toLowerCase());
        JSONObject parametersJsonObject = new JSONObject();
        if (textField0.isVisible()) {
            parametersJsonObject.put(textField0.getPromptText().toLowerCase(), textField0.getText());
        }
        parametersJsonObject.put(textField1.getPromptText().toLowerCase(), textField1.getText());
        messageJsonObject.put("parameters", parametersJsonObject);
        MenuHandler.sendToServer(messageJsonObject);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.closeScene();
    }
}
