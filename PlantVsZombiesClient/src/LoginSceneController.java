import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class LoginSceneController {
    @FXML
    private TextField username, password;
    @FXML
    private Label title;
    @FXML
    private Button loginButton;
    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onLoginButtonMouseClicked() throws IOException {
        JSONObject messageJsonObject = new JSONObject();
        messageJsonObject.put("action", "login");
        JSONObject parametersJsonObject = new JSONObject();
        parametersJsonObject.put("username", username.getText());
        parametersJsonObject.put("password", password.getText());
        messageJsonObject.put("parameters", parametersJsonObject);
        MenuHandler.sendToServer(messageJsonObject);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.closeScene();
    }
}
