package Scenes.watchGame;

import Helper.Controller;
import Helper.GameData;
import Helper.Main;
import Helper.MenuHandler;
import Scenes.Games.GameController;
import Scenes.Games.MemberInHandBoxController;
import Scenes.Refreshable;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class watchGameSceneController implements Controller, Refreshable {
    @FXML
    protected AnchorPane mainAnchorPane;
    @FXML
    protected ImageView selectImageView, backgroundImageView;

    protected ImageView[][] imageViews;
    protected ArrayList<Pane> freeCreaturesPaneArrayList = new ArrayList<>();
    @FXML
    protected AnchorPane gamePane;
    private int mapRowCount, mapColCount, mapColCountWithSlice;

    private String creatureName;

    public watchGameSceneController() {
        int mapRowCount=GameData.mapRowCount;
        int mapColCount=GameData.mapPlantColCount;
        this.mapRowCount = mapRowCount;
        this.mapColCount = mapColCount;
        mapColCountWithSlice = mapColCount * GameData.slices + GameData.slices / 2;
        imageViews = new ImageView[mapColCount][];
    }
    public watchGameSceneController(int mapRowCount, int mapColCount) {
        this.mapRowCount = mapRowCount;
        this.mapColCount = mapColCount;
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

    void creatureFreeAdd(JSONObject jsonObject, double sizeRatio, int dx, int dy, boolean showHP) {
        try {
            int x = ((Long) jsonObject.get("x")).intValue();
            int y = ((Long) jsonObject.get("y")).intValue();
            String name = (String) jsonObject.get("name");
            Pane pane = newPaneWithSize(getNormalWidth() * sizeRatio, (getNormalHeight() + 10) * sizeRatio);
            pane.setLayoutX(getZombieLayoutX(x) + dx);
            pane.setLayoutY(getZombieLayoutY(y) + dy);

            int speed = ((Long) jsonObject.getOrDefault("speed", 0L)).intValue();
            ImageView imageView;
            if (name.endsWith("gun")) {
                imageView = new ImageView(new Image(Objects.requireNonNull(
                        Main.getImageAddressByCreatureName("peashooter gun"))));
            } else {
                imageView = new ImageView(new Image(Objects.requireNonNull(
                        Main.getImageAddressByCreatureName(name))));
            }
            imageView.setPreserveRatio(false);
            imageView.setFitWidth(getNormalWidth() * sizeRatio);
            imageView.setFitHeight((getNormalHeight() + 10) * sizeRatio);
            freeCreaturesPaneArrayList.add(pane);
            pane.getChildren().add(imageView);
            if (showHP) {
                ProgressBar progressBar = new ProgressBar();
                progressBar.setProgress(1.0 * ((Long) jsonObject.getOrDefault("remaining hp", 1L)) / ((Long) jsonObject.getOrDefault("full hp", 1L)));
                progressBar.setLayoutY(100);
                progressBar.setPrefWidth(80);
                progressBar.setPrefHeight(10);
                pane.getChildren().add(progressBar);
            }
            gamePane.getChildren().add(pane);

            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(pane.translateXProperty(),
                                    pane.getTranslateX()
                            )
                    ), new KeyFrame(Duration.seconds(3),
                            new KeyValue(pane.translateXProperty(),
                                    pane.getTranslateX() - speed * GameData.speedConstant
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
                    creatureFreeAdd(jsonObject, 0.66, -45, 45, false);
                } else if (jsonObject.get("name").equals("lily pad")) {
                    System.out.println("ADDING lily pad");
                    creatureFreeAdd(jsonObject, 0.90, -10, 45, false);
                } else if (jsonObject.get("type").equals("Zombie")) {
                    System.out.println("ADDING ZOMBIE");
                    creatureFreeAdd(jsonObject, 1, 0, 0, true);
                } else if (jsonObject.get("type").equals("GunShot")) {
                    System.out.println("ADDING Gun Shot");
                    creatureFreeAdd(jsonObject, 0.33, 0, 30, false);
                } else if (jsonObject.get("type").equals("Plant")) {
                    System.out.println("ADDING PLANT");
                    try {
                        creatureFreeAdd(jsonObject, 1, -20, 0, true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //1 base
    public abstract void put(int x, int y) throws IOException;

    public String getCreatureName() {
        return creatureName;
    }

    public void setCreatureName(String creatureName) {
        this.creatureName = creatureName;
    }

    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("show lawn", null);
    }


    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
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

}