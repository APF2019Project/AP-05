import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class PlantOnDayAndWaterModePlayerSceneController implements Controller {
    @FXML
    private ImageView selectImageView;
    @FXML
    private VBox handBox;

    private ToggleButton[] toggleButtons = new ToggleButton[7];
    private int selectedToggleIndex=-1;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @FXML
    void initialize() throws IOException {
    }

    @Override
    public void initializeReOpen() {
    }

    public void onToggleButtonAction(ToggleButton toggleButton) {
        System.out.println("onToggleButtonAction");
        if (toggleButton.isSelected()) {
            selectImageView.setOpacity(0.5);
            for (ToggleButton anotherToggleButton : toggleButtons) {
                if (anotherToggleButton.equals(toggleButton)) {
                    anotherToggleButton.setSelected(false);
                }
            }
        } else {
            selectImageView.setOpacity(0);
        }
    }

    public void showHand(Object object) {
        JSONArray jsonArray = (JSONArray) object;
        for (int i = 0; i < 7; i++) {
            final int index = i;
            Platform.runLater(() -> {
                toggleButtons[index].setGraphic(new ImageView(new Image(Main.getImageAddressByCreatureName(
                        (String) ((JSONObject) jsonArray.get(index)).get("name")), 50, 50, false, false)));
            });
        }
    }


    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        for (int i = 0; i < 7; i++) {
            toggleButtons[i] = new ToggleButton("");
            final ToggleButton toggleButton = toggleButtons[i];
            //toggleButton.setMnemonicParsing(false);
            handBox.getChildren().add(toggleButton);
            VBox.setMargin(toggleButton, new Insets(3, 3, 3, 3));
            toggleButton.setOnAction((actionEvent -> {
                onToggleButtonAction(toggleButton);
            }));
        }
        MenuHandler.getClient().send("show hand", null);
        // MenuHandler.getClient().send("show collection", null);
        // MenuHandler.getClient().send("show shop", null);
    }
}
