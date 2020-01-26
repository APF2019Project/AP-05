import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Random;

public class MemberInLeaderboardListController implements Controller {
    @FXML
    private Label killCountLabel, usernameLabel;
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
    }

    @Override
    public void initializeReOpen() {
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
        Random random = new Random();
        usernameLabel.setText((String) jsonObject.get("user.getUsername"));
        killCountLabel.setText(String.valueOf(jsonObject.get("user.getKillingEnemyCount")));
        File file = new File("../Profile Pictures/profile" + ((random.nextInt() % 6 + 6) % 6 + 1) + ".png");
        Platform.runLater(() -> {
            imageView.setImage(new Image(file.toURI().toString()));
        });
    }
}
