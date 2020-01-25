import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class InputFormSceneController implements Controller {
    @FXML
    private TextField textField0, textField1;
    @FXML
    private Label title;
    @FXML
    private Button sendButton;
    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onSendButtonMouseClicked() throws IOException {
        JSONObject messageJsonObject = new JSONObject();
        messageJsonObject.put("action", sendButton.getText().toLowerCase());
        JSONObject parametersJsonObject = new JSONObject();
        if (textField0.isVisible()) {
            parametersJsonObject.put(textField0.getPromptText().toLowerCase(), textField0.getText());
        }
        parametersJsonObject.put(textField1.getPromptText().toLowerCase(), textField1.getText());
        messageJsonObject.put("parameters", parametersJsonObject);
        MenuHandler.sendToServer(messageJsonObject);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.closeScene();
    }

    @Override
    public void initializeReOpen() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
        if (jsonObject.containsKey("textField0#setPromptText")) {
            textField0.setPromptText((String) jsonObject.get("textField0#setPromptText"));
        }
        if (jsonObject.containsKey("textField1#setPromptText")) {
            textField1.setPromptText((String) jsonObject.get("textField1#setPromptText"));
        }
        if (jsonObject.containsKey("textField0#setVisible")) {
            textField0.setVisible((boolean) jsonObject.get("textField0#setVisible"));
        }
        if (jsonObject.containsKey("textField1#setVisible")) {
            textField1.setVisible((boolean) jsonObject.get("textField1#setVisible"));
        }
        if (jsonObject.containsKey("sendButton#setText")) {
            sendButton.setText((String) jsonObject.get("sendButton#setText"));
        }
    }
}
