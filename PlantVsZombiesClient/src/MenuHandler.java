import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MenuHandler {
    private static Stage currentStage;
    private final static Client client = new Client("127.0.0.1", 5000);

    private static ArrayList<Scene> scenes = new ArrayList<>();
    private static ArrayList<Controller> controllers = new ArrayList<>();

    static void closeScene() throws IOException {
        if (scenes.size() <= 1) {
            System.exit(0);
        }
        scenes.remove(scenes.size() - 1);
        openSceneWithoutPush(scenes.get(scenes.size() - 1));
        controllers.remove(controllers.size() - 1);
        controllers.get(controllers.size() - 1).initializeReOpen();
        currentStage.show();
    }

    static Controller getCurrentController() {
        return controllers.get(controllers.size() - 1);
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

    public static Pane getPaneWithDefaultParametersHandler(String fileName, JSONObject parameters) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler.class.getResource(fileName));
        Pane pane = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.initJsonInput(parameters);
        return pane;
    }

    private static void openSceneWithDefaultParametersHandler(String menuName, JSONObject parameters) throws IOException {
        String menuFile = (String) parameters.getOrDefault("menuFile", menuName);
        System.err.println(menuFile + "Scene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler.class.getResource(menuFile + "Scene.fxml"));
        Parent parent = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.initJsonInput(parameters);
        Scene scene = new Scene(parent);
        scenes.add(scene);
        controllers.add(controller);
        openSceneWithoutPush(scene);
    }

    static void openSceneWithDefaultParameters(String menuName) {
        Platform.runLater(() -> {
            try {
                openSceneWithDefaultParametersHandler(menuName, JSONData.get(menuName));
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

    static void receive(String message) throws ParseException, IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JSONObject messageJsonObject = (JSONObject) new JSONParser().parse(message);
        String command = (String) messageJsonObject.get("command");
        Object data = messageJsonObject.get("data");
        if (command.equals("newMenu")) {
            openSceneWithDefaultParameters((String) data);
            return;
        }
        if (command.equals("showLog")) {
            System.out.println((String) data);
            return;
        }
        if (command.equals("showMessage")) {
            JSONObject parameters = (JSONObject) data;
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
        Method method = getCurrentController().getClass().getDeclaredMethod(command, Object.class);
        method.invoke(getCurrentController(), data);
    }

    static void sendToServer(JSONObject jsonObject) throws IOException {
        client.send(jsonObject.toJSONString());
    }
}


