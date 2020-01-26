import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.json.simple.JSONObject;

import java.io.IOException;

public class PlaySceneController implements Controller {
    @FXML
    private Label title;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onDayButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("day", null);
    }

    @FXML
    void onWaterButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("water", null);
    }

    @FXML
    void onRailButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("rail", null);
    }

    @FXML
    void onZombieButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("zombie", null);
    }

    @FXML
    void onPVPButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("pvp", null);
    }

    @Override
    public void initializeReOpen() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {

    }
}
