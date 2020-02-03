package Helper;

import Scenes.Games.GameController;
import Scenes.Games.PlantOnRailModePlayer.PlantOnRailModePlayerSceneController;
import Scenes.Refreshable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MenuHandler {
    private final static Client client = new Client("127.0.0.1", 5000);
    private static Stage currentStage;
    private static ArrayList<Scene> scenes = new ArrayList<>();
    private static ArrayList<Controller> controllers = new ArrayList<>();

    public static void closeScene() throws IOException {
        if (scenes.size() <= 1) {
            System.exit(0);
        }
        scenes.remove(scenes.size() - 1);
        openSceneWithoutPush(scenes.get(scenes.size() - 1));
        controllers.remove(controllers.size() - 1);
        controllers.get(controllers.size() - 1).initializeReOpen();
        currentStage.show();
    }

    public static void closeDoubleScene() throws IOException {
        if (scenes.size() <= 2) {
            System.exit(0);
        }
        scenes.remove(scenes.size() - 1);
        scenes.remove(scenes.size() - 1);
        openSceneWithoutPush(scenes.get(scenes.size() - 1));
        controllers.remove(controllers.size() - 1);
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

    public static BorderPane getPaneWithDefaultParametersHandler(String fileName, JSONObject parameters) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler.class.getResource("../" + fileName));
        BorderPane borderPane = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.initJsonInput(parameters);
        return borderPane;
    }

    private static void openSceneWithDefaultParametersHandler(String menuName, JSONObject parameters) throws IOException {
        System.out.println(!controllers.isEmpty() && getCurrentController() instanceof Refreshable);
        if (!controllers.isEmpty() && getCurrentController() instanceof Refreshable &&
                getCurrentController().getClass().getSimpleName().equals(menuName + "SceneController")) {
            System.out.println("HEEEEEEEEEEEEYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYy");
            ((Refreshable) getCurrentController()).sendLoadRequest();
            return;
        }
        else if(!controllers.isEmpty())
            System.out.println(getCurrentController().getClass().getSimpleName() + " XXX " + menuName);
        String prefix = "../Scenes/";
        if (menuName.contains("Player")) {
            prefix = "../Scenes/Games/";
            currentStage.close();
            currentStage = new Stage();
            currentStage.setResizable(false);
            currentStage.setOnCloseRequest((event) -> System.exit(0));
        }
        String menuFile = (String) parameters.getOrDefault("menuFile", menuName);
        System.err.println(menuFile + "Scene.fxml");
        System.err.println(prefix + menuFile + "/" + menuFile + "Scene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler.class.getResource(prefix + menuFile + "/" + menuFile + "Scene.fxml"));
        Parent parent = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        scenes.add(scene);
        controllers.add(controller);
        controller.initJsonInput(parameters);
        openSceneWithoutPush(scene);
    }


    public static void openSceneWithDefaultParameters(String menuName) {
        Platform.runLater(() -> {
            try {
                openSceneWithDefaultParametersHandler(menuName, GameData.getJson(menuName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    static int cnt = 50;

    static synchronized void receive(String message) throws ParseException {
        System.err.println("message" + message);
        JSONObject messageJsonObject = (JSONObject) new JSONParser().parse(message);
        String command = (String) messageJsonObject.get("command");
        Object data = messageJsonObject.get("data");
        if (command.equals("runMenu")) {
            openSceneWithDefaultParameters((String) data);
            return;
        }
        if (command.equals("popMenu")) {
            try {
                closeScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (command.equals("popDoubleMenu")) {
            try {
                closeDoubleScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (command.equals("showLog")) {
            System.out.println("LOG: " + data);
            return;
        }
        if (command.equals("showMessage")) {
            cnt--;
            JSONObject parameters = (JSONObject) data;
            String alertMessage = (String) parameters.get("message");
            String alertMessageType = (String) parameters.getOrDefault("messageType", "ERROR");
            if(cnt == 0)
                System.exit(0);
            Platform.runLater(() -> {
                MessageBox.show(alertMessage, Alert.AlertType.valueOf(alertMessageType));
                if (Alert.AlertType.valueOf(alertMessageType).equals(Alert.AlertType.ERROR)) {
                    System.exit(-1);
                }
            });
            return;
        }
        try {
            System.err.println(getCurrentController().getClass().getSimpleName());
            System.err.println(command);
            Method method = getDeclaredMethod(getCurrentController().getClass(), command, Object.class);
            method.invoke(getCurrentController(), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method getDeclaredMethod(Class<?> cls, String name, Class<?>... parameters) {
        while (cls != null) {
            try {
                return cls.getDeclaredMethod(name, parameters);
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }

        return null;
    }
}


