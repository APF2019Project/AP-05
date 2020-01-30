package Scenes.Games.PlantOnDayAndWaterModePlayer;

import Helper.Controller;
import Helper.GameData;
import Helper.MenuHandler;
import Scenes.Games.GameController;
import org.json.simple.JSONObject;

import java.io.IOException;

public class PlantOnDayAndWaterModePlayerSceneController extends GameController implements Controller {
    public PlantOnDayAndWaterModePlayerSceneController() {
        super(GameData.mapRowCount, GameData.mapPlantColCount);
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
