import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MemberInShopListController implements Controller {
    @FXML
    private Label creatureNameLabel, priceLabel;
    @FXML
    private Button button;
    @FXML
    private ImageView boughtImageView;
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
    }

    @FXML
    void onButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("creatureName", creatureNameLabel.getText());
        MenuHandler.getClient().send("buy", jsonObject);
    }

    @Override
    public void initializeReOpen() {
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
        creatureNameLabel.setText((String) jsonObject.get("creature.getName"));
        priceLabel.setText(jsonObject.get("creature.getPriceInShop") + "$");
        if ((boolean) jsonObject.get("bought")) {
            button.setVisible(false);
        }
        else {
            boughtImageView.setVisible(false);
        }
        Image image = new Image(Main.getImageAddressByCreatureName(creatureNameLabel.getText()));
        if (image != null) {
            Platform.runLater(() -> {
                imageView.setImage(image);
            });
        }
    }
}
