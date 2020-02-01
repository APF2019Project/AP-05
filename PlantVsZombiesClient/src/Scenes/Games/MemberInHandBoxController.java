package Scenes.Games;

import Helper.Controller;
import Helper.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.json.simple.JSONObject;

public class MemberInHandBoxController implements Controller {
    @FXML
    private Label priceLabel;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private ImageView imageView, coolDownImageView;
    @FXML
    private BorderPane borderPane;
    private String creatureName;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @FXML
    void initialize() {
    }

    public String getCreatureName() {
        return creatureName;
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    @Override
    public void initializeReOpen() {
    }

    public boolean isEnable() {
        return coolDownImageView.getScaleY() == 0;
    }

    void showHand(JSONObject jsonObject) {
        creatureName = (String) jsonObject.get("name");
        Platform.runLater(() -> {
            imageView.setImage(new Image(Main.getImageAddressByCreatureName((creatureName))));
            imageView.setPreserveRatio(false);
            if(jsonObject.containsKey("price")) {
                priceLabel.setText(jsonObject.get("price") + "$");
                borderPane.setVisible(true);
            }else{
                borderPane.setVisible(false);
                imageView.setFitHeight(imageView.getFitHeight()+15);
            }
            if ((Long) jsonObject.getOrDefault("remaining cool down", 0L) != 0) {
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, // set start position at 0
                                new KeyValue(coolDownImageView.scaleYProperty(),
                                        (1.0 * (Long) jsonObject.getOrDefault("remaining cool down", 0L)
                                                / (Long) jsonObject.getOrDefault("cool down", 1L))
                                )
                        ), new KeyFrame(Duration.seconds(6 *
                                (Long) jsonObject.getOrDefault("remaining cool down", 0L)),
                                new KeyValue(coolDownImageView.scaleYProperty(), 0)
                        )
                );
                timeline.play();
            }
        });
    }

    public void showHandOnRailMode(String creatureName) {
        this.creatureName = creatureName;
        Platform.runLater(() -> {
            imageView.setImage(new Image(Main.getImageAddressByCreatureName((creatureName))));
            //priceLabel.setVisible(false);
            borderPane.setVisible(false);
            imageView.setFitHeight(imageView.getFitHeight()+15);
        });
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
    }
}
