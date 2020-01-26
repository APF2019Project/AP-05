import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.json.simple.JSONObject;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class ProfileSceneController implements Controller {
    @FXML
    private Label usernameLabel;

    @FXML
    void initialize() {

    }

    @FXML
    void onDeleteAccountButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("delete user");
    }

    @FXML
    void onRenameButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("rename user");
    }

    @FXML
    void onChangeUserButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("change user");
    }

    @FXML
    void onCreateAccountButtonClicked() {
        MenuHandler.openSceneWithDefaultParameters("create account");
    }

    void showUser(Object object) {
        String username = (String) object;
        usernameLabel.setText(username);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show", null);
    }

    @Override
    public void initializeReOpen() {

    }
}
