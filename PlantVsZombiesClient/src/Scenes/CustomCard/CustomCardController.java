package Scenes.CustomCard;

import Helper.Controller;
import Helper.MenuHandler;
import Scenes.Games.MemberInHandBoxController;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CustomCardController implements Controller {
    @FXML
    private VBox fieldBox;
    private JSONObject jsonObject;
    private ArrayList<MemberInCustomCardController> arrayList = new ArrayList<>();

    @FXML
    void onCreateButtonMouseClicked() throws IOException {
        for(MemberInCustomCardController controller : arrayList) {
            jsonObject.put(controller.getFieldnameLabel(), controller.getText());
        }
        MenuHandler.getClient().send("create", jsonObject);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show fields", null);
    }

    @Override
    public void initializeReOpen() throws IOException {

    }

    public void showFields(Object object) throws IOException {
        this.jsonObject = jsonObject;
        JSONObject jsonObject = (JSONObject) object;
        for(Object obj : jsonObject.keySet().toArray()) {
            String fieldName = (String) obj;
            if(jsonObject.get(obj) == null)
                continue;
            JSONObject memberJsonObject = new JSONObject();
            memberJsonObject.put("fieldname", fieldName);
            memberJsonObject.put("value", jsonObject.get(obj));
            FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler
                    .class.getResource("../Scenes/CustomCard/MemberInCustomCard.fxml"));
            BorderPane borderPane = fxmlLoader.load();
            MemberInCustomCardController controller = fxmlLoader.getController();
            controller.initJsonInput(memberJsonObject);
            Platform.runLater(() -> fieldBox.getChildren().add(borderPane));
            arrayList.add(controller);
        }
    }
}
