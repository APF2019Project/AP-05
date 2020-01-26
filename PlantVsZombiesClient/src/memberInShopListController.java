import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class memberInShopListController implements Controller {
    @FXML
    private Label creatureNameLabel, priceLabel;
    @FXML
    private Button button;

    @FXML
    void initialize() {
    }

    @FXML
    void onButtonMouseClicked() throws IOException {

    }

    @Override
    public void initializeReOpen() {
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
        creatureNameLabel.setText((String)jsonObject.get("creature.getName"));
        priceLabel.setText(String.valueOf(jsonObject.get("creature.getPriceInShop")));
        System.out.println(creatureNameLabel.getText());
        System.out.println(priceLabel.getText());
    }
}
