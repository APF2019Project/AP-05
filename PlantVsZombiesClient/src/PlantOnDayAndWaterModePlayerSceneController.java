import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlantOnDayAndWaterModePlayerSceneController extends GameController implements Controller {
    private String selectedPlantName;

    //1 base
    void put(int x, int y) throws IOException {
        System.out.println("put request:");
        if (selectedPlantName != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("plantName", selectedPlantName);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            MenuHandler.getClient().send("plant", jsonObject);
        }
    }

    void selectCreature(Object object) {
        selectedPlantName = (String) object;
        if (selectedPlantName != null) {
            selectImageView.setOpacity(0.5);
        } else {
            selectImageView.setOpacity(0);
        }
        for (MemberInHandBoxController controller : onHandCardControllers) {
            if (!controller.getCreatureName().equals(selectedPlantName)) {
                controller.getToggleButton().setSelected(false);
            } else {
                controller.getToggleButton().setSelected(true);
            }
        }
    }
}
