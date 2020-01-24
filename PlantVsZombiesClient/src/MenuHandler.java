import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class MenuHandler {
    private static Stage currentStage;
    private final static Client client = new Client("127.0.0.1", 5000);

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
        Platform.runLater(() -> {
            currentStage.setScene(scene);
            currentStage.show();
        });
    }

    public static Client getClient() {
        return client;
    }

    private static void openSceneWithoutPush(String menuName) throws IOException {
        System.err.println(menuName + "Scene.fxml");
        Parent parent = FXMLLoader.load(MenuHandler.class.getResource(menuName + "Scene.fxml"));
        openScene(new Scene(parent));
    }

    static void openScene(String menuName) throws IOException {
        Platform.runLater(() -> {
            sceneNames.add(menuName);
            try {
                openSceneWithoutPush(menuName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        String action = (String) messageJsonObject.get("action");
        JSONObject parameters = (JSONObject) messageJsonObject.get("parameters");
        if (action.equals("newMenu")) {
            openScene((String) parameters.get("menuName"));
            return;
        }
        if (action.equals("showMessage")) {
            String alertMessage = (String) parameters.get("message");
            String alertMessageType = (String) parameters.getOrDefault("messageType", "ERROR");
            Platform.runLater(() -> {
                MessageBox.show(alertMessage, Alert.AlertType.valueOf(alertMessageType));
                if (Alert.AlertType.valueOf(alertMessageType).equals(Alert.AlertType.ERROR)) {
                    System.exit(-1);
                }
            });
            return;
        }
        JSONObject result = (JSONObject) messageJsonObject.get("result");
    }

    static void sendToServer(JSONObject jsonObject) throws IOException {
        client.send(jsonObject.toJSONString());
    }
}


