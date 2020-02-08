package Scenes.watchGame;

import Helper.Controller;
import Helper.GameData;
import Helper.MenuHandler;
import Scenes.Refreshable;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class watchGameSceneController extends GameController implements Controller {
    public watchGameSceneController() {
        super(GameData.mapRowCount, GameData.mapPlantColCount);
    }


    public void showHand(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        if ((boolean) jsonObject.get("hasWater")) {
            backgroundImageView.setImage(new Image(new File(
                    "src/Files/waterBackgroundCutSixLines.jpg").toURI().toString()));
        }
        else {
            backgroundImageView.setImage(new Image(new File(
                    "src/Files/dayBackgroundCutSixLines.jpg").toURI().toString()));
        }
        super.showHand(object);
    }
}
