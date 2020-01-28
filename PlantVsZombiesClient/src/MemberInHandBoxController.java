import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MemberInHandBoxController implements Controller {
    @FXML
    private Label priceLabel;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private ImageView imageView, coolDownImageView;
    private String plantName;

    @FXML
    void initialize() {
    }

    public String getPlantName() {
        return plantName;
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    @Override
    public void initializeReOpen() {
    }

    public boolean isEnable(){
        return coolDownImageView.getScaleY()==0;
    }

    void showHand(JSONObject jsonObject) {
        plantName = (String) jsonObject.get("name");
        Platform.runLater(() -> {
            imageView.setImage(new Image(Main.getImageAddressByCreatureName((plantName))));
            priceLabel.setText(jsonObject.get("price") + "$");
            System.out.println("EEE"+((1.0 * (Long) jsonObject.get("remaining cool down") / (Long) jsonObject.get("cool down"))));
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(coolDownImageView.scaleYProperty(),
                                    (1.0 * (Long) jsonObject.get("remaining cool down") / (Long) jsonObject.get("cool down"))
                            )
                    ), new KeyFrame(Duration.seconds(3*(Long) jsonObject.get("remaining cool down")),
                            new KeyValue(coolDownImageView.scaleYProperty(), 0)
                    )
            );
            timeline.play();
        });
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
    }
}
