import org.json.simple.JSONObject;

import java.io.IOException;

public class ZombiePlayerSceneController extends GameController {
    private String creatureName;

    void put(int x, int y) throws IOException {
        System.out.println("put request:");
        if (creatureName != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("zombieName", creatureName);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            MenuHandler.getClient().send("put", jsonObject);
        }
    }

    void selectCreature(Object object) {
        creatureName = (String) object;
        if (creatureName != null) {
            selectImageView.setOpacity(0.5);
        }
        else {
            selectImageView.setOpacity(0);
        }
        for (MemberInHandBoxController controller : onHandCardControllers) {
            if (!controller.getCreatureName().equals(creatureName)) {
                controller.getToggleButton().setSelected(false);
            }
            else {
                controller.getToggleButton().setSelected(true);
            }
        }
    }

}
