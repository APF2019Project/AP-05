package Scenes.CustomCard;

import Helper.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MemberInCustomCardController implements Controller {
    @FXML
    private Label fieldNameLabel;
    @FXML
    private TextField textField;

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        Platform.runLater(() -> {
            fieldNameLabel.setText((String) jsonObject.get("fieldname"));
            textField.setPromptText(jsonObject.get("value").getClass().getSimpleName());
        });
    }

    public String getText() {
        return textField.getText();
    }

    public String getFieldnameLabel() {
        return fieldNameLabel.getText();
    }

    @Override
    public void initializeReOpen() throws IOException {

    }
}
