package Scenes.Shop;

import Helper.Controller;
import Helper.Main;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class MemberInShopListController implements Controller {
    @FXML
    private Label creatureNameLabel, priceLabel,remainNumber;
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
        if(Integer.valueOf(jsonObject.get("creature.remainingInShop").toString())<0){
            remainNumber.setText("basic creature(yo cant buy it)");
        }else {
            remainNumber.setText(jsonObject.get("creature.remainingInShop").toString() + " number left");
        }
        if ((boolean) jsonObject.get("bought")) {
            button.setVisible(false);
        }
        else {
            boughtImageView.setVisible(false);
        }
        if (Main.getImageAddressByCreatureName(creatureNameLabel.getText()) != null) {
            Image image = new Image(Objects.requireNonNull(Main.getImageAddressByCreatureName(creatureNameLabel.getText())));
            Platform.runLater(() -> {
                imageView.setImage(image);
            });
        }
    }
}
