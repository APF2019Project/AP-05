import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class FirstSceneController {
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
        MenuHandler.openScene("login");
    }

    @FXML
    void onCreateAccountButtonMouseClicked() throws IOException {
        MenuHandler.openScene("createAccount");
    }

    @FXML
    void onLeaderboardButtonMouseClicked() throws IOException {
        MenuHandler.openScene("leaderboard");
    }
}
