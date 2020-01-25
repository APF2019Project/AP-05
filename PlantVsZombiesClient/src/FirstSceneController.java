import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.json.simple.JSONObject;

import java.io.IOException;

public class FirstSceneController implements Controller {
    @FXML
    private Label title;
    @FXML
    private Button loginButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button leaderboardButton;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onLoginButtonMouseClicked() throws IOException {
        MenuHandler.openSceneWithDefaultParameters("login");
    }

    @FXML
    void onCreateAccountButtonMouseClicked() throws IOException {
        MenuHandler.openSceneWithDefaultParameters("create account");
    }

    @FXML
    void onLeaderboardButtonMouseClicked() throws IOException {
        MenuHandler.openSceneWithDefaultParameters("shop");
    }

    @Override
    public void initializeReOpen() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {

    }
}
