package Scenes.Games.PlantOnRailModePlayer;

import Helper.Controller;
import Helper.GameData;
import Helper.Main;
import Helper.MenuHandler;
import Scenes.Games.GameController;
import Scenes.Games.MemberInHandBoxController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlantOnRailModePlayerSceneController implements Controller {
    @FXML
    protected AnchorPane mainAnchorPane;
    @FXML
    protected ImageView selectImageView, backgroundImageView;
    @FXML
    protected VBox handBox;

    protected MemberInHandBoxController[] onHandCardControllers = new MemberInHandBoxController[11];
    protected AnchorPane[] onHandCardAnchors = new AnchorPane[10];
    protected ImageView[][] imageViews;
    protected ArrayList<Pane> freeCreaturesPaneArrayList = new ArrayList<>();
    @FXML
    protected AnchorPane gamePane;
    private int mapRowCount, mapColCount, mapColCountWithSlice;
    @FXML
    private Label sunLabel;
    private int plantIndex = -1;

    public PlantOnRailModePlayerSceneController() {
        this.mapRowCount = GameData.mapRowCount;
        this.mapColCount = GameData.mapPlantColCount;
        mapColCountWithSlice = mapColCount * GameData.slices + GameData.slices / 2;
        imageViews = new ImageView[mapColCount][];
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitDoubleMenu", null);
        MenuHandler.closeDoubleScene();
    }

    protected double getNormalWidth() {
        return gamePane.getPrefWidth() / mapColCount;
    }

    protected double getNormalHeight() {
        return gamePane.getPrefHeight() / mapRowCount;
    }

    protected double getZombieWidth() {
        return gamePane.getPrefWidth() / mapColCountWithSlice;
    }

    protected double getZombieHeight() {
        return gamePane.getPrefHeight() / mapRowCount;
    }

    protected double getZombieLayoutX(int x) {
        return x * getZombieWidth();
    }

    protected double getZombieLayoutY(int y) {
        return y * getZombieHeight();

    }

    protected double getPlantLayoutX(int x) {
        return x * getNormalWidth();

    }

    protected double getPlantLayoutY(int y) {
        return y * getNormalHeight();
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
            jsonObject.put("plantIndex", memberInHandBoxController.getIndex());
            MenuHandler.getClient().send("select", jsonObject);
        }
    }

    public void showHand(Object object) throws Exception {
        JSONObject jsonObject = (JSONObject) object;
        JSONArray jsonArray = (JSONArray) jsonObject.get("cards");
        System.out.println(jsonObject.toJSONString());
        Platform.runLater(() -> {
            sunLabel.setText(jsonObject.get("record").toString());
        });
        setAllNotVisible();
        System.out.println(jsonArray.size() + " HEREEEEEEEEEEEEEEEEEEEEEEE");
        for (int i = 0; i < jsonArray.size(); i++) {
            String name = (String) jsonArray.get(i);
            final int index = i;
            Platform.runLater(() -> {
                onHandCardAnchors[index].setVisible(true);
                onHandCardControllers[index].showHandOnRailMode(name);
            });
        }
    }

    protected Pane newPaneWithSize(double width, double height) {
        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setMinHeight(pane.getPrefHeight());
        pane.setMaxHeight(pane.getPrefHeight());
        pane.setMinWidth(pane.getPrefWidth());
        pane.setMaxWidth(pane.getPrefWidth());
        return pane;
    }

    void creatureFreeAdd(JSONObject jsonObject, double sizeRatio, int dx, int dy) {
        try {
            int x = ((Long) jsonObject.get("x")).intValue();
            int y = ((Long) jsonObject.get("y")).intValue();
            Pane pane = newPaneWithSize(getNormalWidth() * sizeRatio, (getNormalHeight() + 10) * sizeRatio);
            pane.setLayoutX(getZombieLayoutX(x) + dx);
            pane.setLayoutY(getZombieLayoutY(y) + dy);

            int speed = ((Long) jsonObject.getOrDefault("speed", 0L)).intValue();
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
                    Main.getImageAddressByCreatureName((String) jsonObject.get("name")))));
            imageView.setPreserveRatio(false);
            imageView.setFitWidth(getNormalWidth() * sizeRatio);
            imageView.setFitHeight((getNormalHeight() + 10) * sizeRatio);
            freeCreaturesPaneArrayList.add(pane);
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
                                    pane.getTranslateX() - speed * 28
                            )
                    )
            );
            timeline.play();
            if (jsonObject.get("type").equals("Plant") && !jsonObject.get("name").equals("lawnmower")) {
                pane.setAccessibleText(y + "," + x);
                pane.setOnMouseClicked(mouseEvent -> {
                    try {
                        int finalI = Integer.parseInt(pane.getAccessibleText().split(",")[0]);
                        int finalJ = Integer.parseInt(pane.getAccessibleText().split(",")[1]);
                        put(finalJ + 1, finalI + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLawn(Object object) {
        Platform.runLater(() -> {
            for (Pane pane : freeCreaturesPaneArrayList) {
                pane.getChildren().clear();
                gamePane.getChildren().remove(pane);
            }
            for (int i = 0; i < mapRowCount; i++) {
                for (int j = 0; j < mapColCount; j++) {
                    imageViews[i][j].setImage(null);
                }
            }
            JSONArray jsonArray = (JSONArray) object;
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                if (jsonObject.get("name").equals("lawnmower")) {
                    System.out.println("ADDING lawnmower");
                    creatureFreeAdd(jsonObject, 0.66, -45, 45);
                }
                else if (jsonObject.get("name").equals("lily pad")) {
                    System.out.println("ADDING lily pad");
                    creatureFreeAdd(jsonObject, 0.90, -10, 45);
                }
                else if (jsonObject.get("type").equals("Zombie")) {
                    System.out.println("ADDING ZOMBIE");
                    creatureFreeAdd(jsonObject, 1, 0, 0);
                }
                else if (jsonObject.get("type").equals("GunShot")) {
                    System.out.println("ADDING Gun Shot");
                    creatureFreeAdd(jsonObject, 0.33, 0, 30);
                }
                else if (jsonObject.get("type").equals("Plant")) {
                    System.out.println("ADDING PLANT");
                    try {
                        creatureFreeAdd(jsonObject, 1, -20, 0);
                        /*
                        imageViews[((Long) jsonObject.get("y")).intValue()][((Long) jsonObject.get("x")).intValue() / GameData.slices]
                                .setImage(new Image(Objects.requireNonNull(Main.getImageAddressByCreatureName(
                                        (String) jsonObject.get("name")))));*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("show hand", null);
        MenuHandler.getClient().send("show lawn", null);
    }

    private void setAllNotVisible() {
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Platform.runLater(() -> {
                onHandCardAnchors[index].setVisible(false);
            });
        }
    }

    private void handleShovel() throws IOException {
        final int i = 10;
        FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("MemberInHandBox.fxml"));
        final AnchorPane anchorPane = fxmlLoader.load();
        onHandCardControllers[i] = fxmlLoader.getController();
        onHandCardControllers[i].setIndex(i);
        anchorPane.setOnMouseClicked((actionEvent -> {
            try {
                onToggleButtonAction(onHandCardControllers[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        Platform.runLater(() -> {
            System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
            mainAnchorPane.getChildren().add(anchorPane);
            onHandCardControllers[i].showHandOnRailMode("shovel");
            anchorPane.setLayoutX(97.0);
            anchorPane.setLayoutY(14.0);
        });
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("MemberInHandBox.fxml"));
            final AnchorPane anchorPane = fxmlLoader.load();
            onHandCardAnchors[i] = anchorPane;
            onHandCardControllers[i] = fxmlLoader.getController();
            onHandCardControllers[i].setIndex(i);
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
        handleShovel();
        setAllNotVisible();
        for (int i = 0; i < mapRowCount; i++) {
            imageViews[i] = new ImageView[mapColCount];
            for (int j = 0; j < mapColCount; j++) {
                imageViews[i][j] = new ImageView();
                final ImageView imageView = imageViews[i][j];
                imageView.setPreserveRatio(false);
                imageView.setFitWidth(getNormalWidth());
                imageView.setFitHeight(getNormalHeight() + 10);
                //final int finalI = i, finalJ = j;

                Pane pane = newPaneWithSize(getNormalWidth(), getNormalHeight() + 10);
                pane.setLayoutY(getPlantLayoutY(i));
                pane.setLayoutX(getPlantLayoutX(j));
                pane.setAccessibleText(i + "," + j);
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
        sendLoadRequest();
    }

    public void selectCreature(Object object) {
        plantIndex = ((Long) object).intValue();
        if (plantIndex != -1) {
            selectImageView.setOpacity(0.5);
        }
        else {
            selectImageView.setOpacity(0);
        }
        for (int i = 0; i <= 10; i++) {
            MemberInHandBoxController controller = onHandCardControllers[i];
            if (i != plantIndex) {
                controller.getToggleButton().setSelected(false);
            }
            else {
                controller.getToggleButton().setSelected(true);
            }
        }
    }

    //1 base
    public void put(int x, int y) throws IOException {
        System.out.println("put request:");
        if (plantIndex != -1) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            MenuHandler.getClient().send("put", jsonObject);
        }
    }
}
