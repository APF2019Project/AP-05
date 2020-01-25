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

    private static ArrayList<Scene> scenes = new ArrayList<>();

    static void closeScene() throws IOException {
        if (scenes.size() <= 1) {
            System.exit(0);
        }
        scenes.remove(scenes.size() - 1);
        openSceneWithoutPush(scenes.get(scenes.size() - 1));
        currentStage.show();
    }

    static void setStage(Stage stage) {
        currentStage = stage;
    }

    public static Client getClient() {
        return client;
    }

    private static void openSceneWithoutPush(Scene scene) {
        currentStage.setScene(scene);
        currentStage.show();
    }

    private static Scene getScene(String menuName, JSONObject parameters) throws IOException {
        String menuFile = (String) parameters.getOrDefault("menuFile", menuName);
        System.err.println(menuFile + "Scene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler.class.getResource(menuFile + "Scene.fxml"));
        Parent parent = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.initJsonInput(parameters);
        return new Scene(parent);
    }

    static void openSceneWithDefaultParameters(String menuName) {
        Platform.runLater(() -> {
            try {
                Scene scene = getScene(menuName, JSONData.get(menuName));
                scenes.add(scene);
                openSceneWithoutPush(scene);
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
            openSceneWithDefaultParameters((String) parameters.get("menuName"));
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


