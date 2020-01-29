import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlantOnDayAndWaterModePlayerSceneController implements Controller {
    @FXML
    private ImageView selectImageView;
    @FXML
    private VBox handBox;

    private MemberInHandBoxController[] onHandCardControllers = new MemberInHandBoxController[GameData.creatureOnHandSize];
    private String selectedPlantName = null;
    private ImageView[][] imageViews = new ImageView[GameData.mapRowCount][];
    private ArrayList<Pane> zombiesPanes = new ArrayList<>();

    @FXML
    private Label sunLabel;

    @FXML
    private AnchorPane gamePane;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    private double getPlantWidth() {
        return gamePane.getPrefWidth() / GameData.mapPlantColCount;
    }

    private double getPlantHeight() {
        return gamePane.getPrefHeight() / GameData.mapRowCount;
    }

    private double getZombieWidth() {
        return gamePane.getPrefWidth() / GameData.mapColCount;
    }

    private double getZombieHeight() {
        return gamePane.getPrefHeight() / GameData.mapRowCount;
    }

    private double getZombieLayoutX(int x) {
        return x * getZombieWidth();
    }

    private double getZombieLayoutY(int y) {
        return y * getZombieHeight();

    }

    private double getPlantLayoutX(int x) {
        return x * getPlantWidth();

    }

    private double getPlantLayoutY(int y) {
        return y * getPlantHeight();
    }

    @FXML
    void initialize() throws IOException {
    }

    @Override
    public void initializeReOpen() {
    }

    public void onToggleButtonAction(MemberInHandBoxController memberInHandBoxController) throws IOException {
        if (memberInHandBoxController.isEnable()) {
            System.out.println("onToggleButtonAction");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("plantName", memberInHandBoxController.getPlantName());
            MenuHandler.getClient().send("select", jsonObject);
        }
    }

    public void showHand(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        JSONArray jsonArray = (JSONArray) jsonObject.get("cards");
        Platform.runLater(() -> {
            sunLabel.setText(jsonObject.get("sun").toString());
        });
        for (int i = 0; i < GameData.creatureOnHandSize; i++) {
            final int index = i;
            Platform.runLater(() -> {
                onHandCardControllers[index].showHand((JSONObject) jsonArray.get(index));
            });
        }
    }

    private Pane newPaneWithSize(double width, double height) {
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setMinHeight(pane.getPrefHeight());
        pane.setMaxHeight(pane.getPrefHeight());
        pane.setMinWidth(pane.getPrefWidth());
        pane.setMaxWidth(pane.getPrefWidth());
        return pane;
    }

    public void showLawn(Object object) {
        Platform.runLater(() -> {
            for (Pane pane : zombiesPanes) {
                pane.getChildren().clear();
                gamePane.getChildren().remove(pane);
            }
            JSONArray jsonArray = (JSONArray) object;
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                if (jsonObject.get("type").equals("Zombie")) {
                    System.out.println("ADDING ZOMBIE");
                    try {
                        Pane pane = newPaneWithSize(getZombieWidth(), getZombieHeight() + 10);
                        pane.setLayoutX(getZombieLayoutX(((Long) jsonObject.get("x")).intValue()));
                        pane.setLayoutY(getZombieLayoutY(((Long) jsonObject.get("y")).intValue()));
                        int speed = ((Long) jsonObject.get("speed")).intValue();
                        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
                                Main.getImageAddressByCreatureName((String) jsonObject.get("name")))));
                        imageView.setPreserveRatio(false);
                        imageView.setFitWidth(getPlantWidth());
                        imageView.setFitHeight(getPlantHeight() + 10);
                        zombiesPanes.add(pane);
                        pane.getChildren().add(imageView);
                        gamePane.getChildren().add(pane);

                        Timeline timeline = new Timeline();
                        timeline.getKeyFrames().addAll(
                                new KeyFrame(Duration.ZERO, // set start position at 0
                                        new KeyValue(pane.translateXProperty(),
                                                pane.getTranslateX()
                                        )
                                ), new KeyFrame(Duration.seconds(3),
                                        new KeyValue(pane.translateXProperty(),
                                                pane.getTranslateX() - speed * 30
                                        )
                                )
                        );
                        timeline.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (jsonObject.get("type").equals("GunShot")) {
                    System.out.println("ADDING Gun Shot");
                    try {
                        String gunName = (String) jsonObject.get("name");
                        Pane pane = newPaneWithSize(getZombieWidth(), getZombieHeight() + 10);
                        pane.setLayoutX(getZombieLayoutX(((Long) jsonObject.get("x")).intValue()));
                        pane.setLayoutY(getZombieLayoutY(((Long) jsonObject.get("y")).intValue()) + 30);
                        int speed = ((Long) jsonObject.get("speed")).intValue();
                        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
                                Main.getImageAddressByCreatureName(gunName))));// inja bayad avaz she
                        imageView.setPreserveRatio(false);
                        imageView.setFitWidth(getPlantWidth() / 3);
                        imageView.setFitHeight(getPlantHeight() / 3);
                        zombiesPanes.add(pane);
                        pane.getChildren().add(imageView);
                        gamePane.getChildren().add(pane);

                        Timeline timeline = new Timeline();
                        timeline.getKeyFrames().addAll(
                                new KeyFrame(Duration.ZERO, // set start position at 0
                                        new KeyValue(pane.translateXProperty(),
                                                pane.getTranslateX()
                                        )
                                ), new KeyFrame(Duration.seconds(3),
                                        new KeyValue(pane.translateXProperty(),
                                                pane.getTranslateX() + speed * 30
                                        )
                                )
                        );
                        timeline.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (jsonObject.get("type").equals("Plant")) {
                    System.out.println("ADDING PLANT");
                    try {
                        imageViews[((Long) jsonObject.get("y")).intValue()][((Long) jsonObject.get("x")).intValue() / GameData.slices]
                                .setImage(new Image(Objects.requireNonNull(Main.getImageAddressByCreatureName(
                                        (String) jsonObject.get("name")))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //1 base
    void put(int x, int y) throws IOException {
        System.out.println("put request:");
        if (selectedPlantName != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("plantName", selectedPlantName);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            MenuHandler.getClient().send("plant", jsonObject);
        }
    }

    void selectPlant(Object object) {
        selectedPlantName = (String) object;
        if (selectedPlantName != null) {
            selectImageView.setOpacity(0.5);
        } else {
            selectImageView.setOpacity(0);
        }
        for (MemberInHandBoxController controller : onHandCardControllers) {
            if (!controller.getPlantName().equals(selectedPlantName)) {
                controller.getToggleButton().setSelected(false);
            } else {
                controller.getToggleButton().setSelected(true);
            }
        }
    }

    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("show hand", null);
        MenuHandler.getClient().send("show lawn", null);
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        for (int i = 0; i < GameData.creatureOnHandSize; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler.class.getResource("MemberInHandBox.fxml"));
            final AnchorPane anchorPane = fxmlLoader.load();
            onHandCardControllers[i] = fxmlLoader.getController();
            handBox.getChildren().add(anchorPane);
            VBox.setMargin(anchorPane, new Insets(3, 3, 3, 3));
            final int index = i;
            anchorPane.setOnMouseClicked((actionEvent -> {
                try {
                    onToggleButtonAction(onHandCardControllers[index]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
        for (int i = 0; i < GameData.mapRowCount; i++) {
            imageViews[i] = new ImageView[GameData.mapPlantColCount];
            for (int j = 0; j < GameData.mapPlantColCount; j++) {
                imageViews[i][j] = new ImageView();
                final ImageView imageView = imageViews[i][j];
                imageView.setPreserveRatio(false);
                imageView.setFitWidth(getPlantWidth());
                imageView.setFitHeight(getPlantHeight() + 10);
                //final int finalI = i, finalJ = j;

                Pane pane = newPaneWithSize(getPlantWidth(), getZombieHeight() + 10);
                pane.setAccessibleText(i + "," + j);
                pane.setLayoutY(getPlantLayoutY(i));
                pane.setLayoutX(getPlantLayoutX(j));
                pane.setOnMouseClicked(mouseEvent -> {
                    try {
                        int finalI = Integer.parseInt(pane.getAccessibleText().split(",")[0]);
                        int finalJ = Integer.parseInt(pane.getAccessibleText().split(",")[1]);
                        put(finalJ * GameData.slices + GameData.slices / 2 + 1, finalI + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                pane.getChildren().add(imageView);
                gamePane.getChildren().add(pane);
            }
        }
    }
}
