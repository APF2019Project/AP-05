import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class memberInShopListController implements Controller {
    @FXML
    private Label creatureNameLabel, priceLabel;
    @FXML
    private ImageView imageView;
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
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        creatureNameLabel.setText((String) jsonObject.get("creature.getName"));
        priceLabel.setText(String.valueOf(jsonObject.get("creature.getPriceInShop")));

        File directory = new File("../Gallery/");
        for (File dir : directory.listFiles()) {
            if (dir.getName().toLowerCase().equals(creatureNameLabel.getText())) {
                for (File file : dir.listFiles()) {
                    if (file.exists()) {
                        System.out.println(file.getPath());
                        Platform.runLater(()->{
                            imageView.setImage(new Image(file.toURI().toString()));
                        });
                        break;
                    }
                }
            }
        }
    }
}
