package Scenes.SelectTypeOfCustom;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;

import java.io.IOException;

public class SelectTypeOfCustomController implements Controller {
    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {

    }

    @Override
    public void initializeReOpen() throws IOException {

    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.closeScene();
    }

    @FXML
    public void onZombieButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Zombie");
        MenuHandler.getClient().send("custom card", jsonObject);
    }

    @FXML
    public void onShieldButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Shield");
        MenuHandler.getClient().send("custom card", jsonObject);
    }

    @FXML
    public void onGunButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Gun");
        MenuHandler.getClient().send("custom card", jsonObject);
    }

    @FXML
    public void onPlantButtonMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Plant");
        MenuHandler.getClient().send("custom card", jsonObject);
    }
}
