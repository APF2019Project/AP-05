import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MainMenuSceneController implements Controller {
    @FXML
    private Label title;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onPlayButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("play", null);
    }

    @FXML
    void onProfileButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("profile", null);
    }

    @FXML
    void onShopButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("shop", null);
    }

    @Override
    public void initializeReOpen() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {

    }
}
