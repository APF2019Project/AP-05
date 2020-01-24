import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class MenuHandler {
    private static Stage currentStage;
    private final static Client client = new Client("wfep78yt7oj.com", 1000);

    private static ArrayList<String> sceneNames = new ArrayList<>();

    static void closeScene() throws IOException {
        if (sceneNames.size() <= 1) {
            System.exit(0);
        }
        sceneNames.remove(sceneNames.size() - 1);
        openSceneWithoutPush(sceneNames.get(sceneNames.size() - 1));
        currentStage.show();
    }

    static void setStage(Stage stage) {
        currentStage = stage;
    }


    private static void openScene(Scene scene) {
        currentStage.setScene(scene);
        currentStage.show();
    }

    private static void openSceneWithoutPush(String menuName) throws IOException {
        Parent parent = FXMLLoader.load(MenuHandler.class.getResource(menuName + "Scene.fxml"));
        openScene(new Scene(parent));
    }

    static void openScene(String menuName) throws IOException {
        sceneNames.add(menuName);
        openSceneWithoutPush(menuName);
    }

    /*
    static void openSceneInNewStageAndCloseCurrentStage(Scene scene) {
        if (currentStage != null) {
            currentStage.close();
        }
        currentStage = new Stage();
        currentStage.setTitle("Plant Vs Zombies");
        currentStage.getIcons().add(new Image(MenuHandler.class.getResourceAsStream("Files/icon.jpg")));
        currentStage.setScene(scene);
        currentStage.show();
    }
    */

    static void receive(String message) throws ParseException, IOException {
        JSONObject messageJsonObject = (JSONObject) new JSONParser().parse(message);
        boolean successful = (boolean) messageJsonObject.get("successful");
        if (messageJsonObject.containsKey("newMenu")) {
            openScene((String) messageJsonObject.get("newMenu"));
        }
        if (!successful) {
            String errorMessage = (String) messageJsonObject.getOrDefault("error", "Error!");
            boolean needToExit = (boolean) messageJsonObject.getOrDefault("needToExit", true);
            if (needToExit) {
                MessageBox.showErrorAndExit(errorMessage);
            } else {
                MessageBox.showError(errorMessage);
            }
            return;
        }
        JSONObject result = (JSONObject) messageJsonObject.get("result");
    }

    static void sendToServer(JSONObject jsonObject) throws IOException {
        client.send(jsonObject.toJSONString());
    }
}


