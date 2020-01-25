import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class CreateAccountSceneController{
    @FXML
    private TextField username, password;
    @FXML
    private Label title;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onCreateAccountButtonMouseClicked() throws IOException {
        JSONObject messageJsonObject = new JSONObject();
        messageJsonObject.put("action", "create account");
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
