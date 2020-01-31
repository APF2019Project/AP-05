package Scenes.Games.ZombiePlayer;

import Helper.GameData;
import Helper.MenuHandler;
import Scenes.Games.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class ZombiePlayerSceneController extends GameController {
    public ZombiePlayerSceneController() {
        super(GameData.mapRowCount, GameData.mapPlantColCount + GameData.mapZombieColCount);
    }

    boolean isWaveRunning, canStart;
    @FXML
    private Button startWaveButton;

    @FXML
    public void onStartWaveButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("start", null);
    }

    public void showWaveStatus(Object object) {
        isWaveRunning = (boolean) object;
        startWaveButton.setVisible(!isWaveRunning);
    }

    public void showCanStart(Object object) {
        canStart = (boolean) object;
        startWaveButton.setVisible(canStart);
    }

    public void showHand(Object object){
        JSONObject jsonObject = (JSONObject) object;
        if((boolean)jsonObject.get("hasWater")) {
            backgroundImageView.setImage(new Image(new File(
                    "src/Files/waterZombieBackgroundCutSixLines.jpg").toURI().toString()));
        }else {
            backgroundImageView.setImage(new Image(new File(
                    "src/Files/dayZombieBackgroundCutSixLines.jpg").toURI().toString()));
        }
        super.showHand(object);
    }

    @Override
    public void sendLoadRequest() throws IOException {
        super.sendLoadRequest();
        MenuHandler.getClient().send("show wave status", null);
    }

    public void put(int x, int y) throws IOException {
        if (x > GameData.mapPlantColCount * GameData.slices) {
            System.out.println("put request:");
            if (getCreatureName() != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("zombieName", getCreatureName());
                jsonObject.put("x", x);
                jsonObject.put("y", y);
                MenuHandler.getClient().send("put", jsonObject);
            }
        }
    }
}
