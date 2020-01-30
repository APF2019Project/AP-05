import org.json.simple.JSONObject;

import java.io.IOException;

public class PlantOnDayAndWaterModePlayerSceneController extends GameController implements Controller {
    public PlantOnDayAndWaterModePlayerSceneController() {
        super(GameData.mapRowCount, GameData.mapPlantColCount);
    }

    //1 base
    void put(int x, int y) throws IOException {
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
