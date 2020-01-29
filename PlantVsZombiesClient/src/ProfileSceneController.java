import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ProfileSceneController implements Controller {
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView imageView;

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

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show", null);
        System.out.println(imageView.getImage().getUrl());
    }

    @Override
    public void initializeReOpen() throws IOException {
        MenuHandler.getClient().send("show", null);
    }
}
