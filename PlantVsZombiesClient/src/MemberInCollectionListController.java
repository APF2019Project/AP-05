import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class MemberInCollectionListController implements Controller {
    @FXML
    private Label creatureNameLabel;
    @FXML
    private CheckBox checkBox;
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
    }

    @FXML
    void onCheckBoxAction() throws IOException {
        String command = checkBox.isSelected() ? "select" : "remove";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("creatureName", creatureNameLabel.getText());
        MenuHandler.getClient().send(command, jsonObject);
    }

    @Override
    public void initializeReOpen() {
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
        creatureNameLabel.setText((String) jsonObject.get("creature.getName"));
        if ((boolean) jsonObject.get("selected")) {
            checkBox.setSelected(true);
        }
        File directory = new File("../Gallery/");
        for (File dir : directory.listFiles()) {
            if (dir.getName().toLowerCase().equals(creatureNameLabel.getText())) {
                for (File file : dir.listFiles()) {
                    if (file.exists() && file.getName().contains("HD")) {
                        System.out.println(file.getPath());
                        Platform.runLater(() -> {
                            imageView.setImage(new Image(file.toURI().toString()));
                        });
                        break;
                    }
                }
            }
        }
    }
}
