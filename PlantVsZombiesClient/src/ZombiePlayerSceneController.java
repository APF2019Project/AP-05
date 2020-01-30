import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.json.simple.JSONObject;

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

    void showWaveStatus(Object object) {
        isWaveRunning = (boolean) object;
        startWaveButton.setVisible(!isWaveRunning);
    }

    void showCanStart(Object object) {
        canStart = (boolean) object;
        startWaveButton.setVisible(canStart);
    }

    @Override
    public void sendLoadRequest() throws IOException {
        super.sendLoadRequest();
        MenuHandler.getClient().send("show wave status", null);
    }

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
