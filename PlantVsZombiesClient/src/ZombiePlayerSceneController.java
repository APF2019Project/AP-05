import org.json.simple.JSONObject;

import java.io.IOException;

public class ZombiePlayerSceneController extends GameController {
    void put(int x, int y) throws IOException {
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
