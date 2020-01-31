package Scenes.Games.PlantOnDayAndWaterModePlayer;

import Helper.Controller;
import Helper.GameData;
import Helper.MenuHandler;
import Scenes.Games.GameController;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class PlantOnDayAndWaterModePlayerSceneController extends GameController implements Controller {
    public PlantOnDayAndWaterModePlayerSceneController() {
        super(GameData.mapRowCount, GameData.mapPlantColCount);
    }


    public void showHand(Object object){
        JSONObject jsonObject = (JSONObject) object;
        if((boolean)jsonObject.get("hasWater")) {
            backgroundImageView.setImage(new Image(new File(
                    "src/Files/waterBackgroundCutSixLines.jpg").toURI().toString()));
        }else {
            backgroundImageView.setImage(new Image(new File(
                    "src/Files/dayBackgroundCutSixLines.jpg").toURI().toString()));
        }
        super.showHand(object);
    }

    //1 base
    public void put(int x, int y) throws IOException {
        System.out.println("put request:");
        if (getCreatureName() != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("plantName", getCreatureName());
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            MenuHandler.getClient().send("plant", jsonObject);
        }
    }
}
