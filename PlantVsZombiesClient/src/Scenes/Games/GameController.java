package Scenes.Games;

import Helper.Controller;
import Helper.GameData;
import Helper.Main;
import Helper.MenuHandler;
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
import javafx.scene.control.ProgressIndicator;
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

public abstract class GameController implements Controller, Refreshable {
    @FXML
    protected AnchorPane mainAnchorPane;
    @FXML
    protected ImageView selectImageView, backgroundImageView;
    @FXML
    protected VBox handBox;
    @FXML
    protected Pane shovelPane;

    protected MemberInHandBoxController[] onHandCardControllers =
            new MemberInHandBoxController[GameData.creatureOnHandSize + 1]; //+1 is for shovel
    protected ImageView[][] imageViews;
    protected ArrayList<Pane> freeCreaturesPaneArrayList = new ArrayList<>();
    @FXML
    protected AnchorPane gamePane;
    private int mapRowCount, mapColCount, mapColCountWithSlice;
    @FXML
    private Label sunLabel;
    private String creatureName;

    public GameController(int mapRowCount, int mapColCount) {
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

    public void onToggleButtonAction(MemberInHandBoxController memberInHandBoxController) throws IOException {
        if (memberInHandBoxController.isEnable()) {
            System.out.println("onToggleButtonAction");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("creatureName", memberInHandBoxController.getCreatureName());
            MenuHandler.getClient().send("select", jsonObject);
        }
    }

    public void showHand(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        JSONArray jsonArray = (JSONArray) jsonObject.get("cards");
        System.out.println(jsonObject.toJSONString());
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

    //1 base
    public abstract void put(int x, int y) throws IOException;

    public String getCreatureName() {
        return creatureName;
    }

    public void setCreatureName(String creatureName) {
        this.creatureName = creatureName;
    }

    public void sendLoadRequest() throws IOException {
        MenuHandler.getClient().send("show hand", null);
        MenuHandler.getClient().send("show lawn", null);
    }

    private void handleShovel() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("MemberInHandBox.fxml"));
        final AnchorPane anchorPane = fxmlLoader.load();
        onHandCardControllers[GameData.creatureOnHandSize] = fxmlLoader.getController();
        anchorPane.setOnMouseClicked((actionEvent -> {
            try {
                MenuHandler.getClient().send("select shovel", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        final JSONObject shovel = new JSONObject();
        shovel.put("name", "shovel");
        Platform.runLater(() -> {
            //System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
            mainAnchorPane.getChildren().add(anchorPane);
            onHandCardControllers[GameData.creatureOnHandSize].showHand(shovel);
            anchorPane.setLayoutX(97.0);
            anchorPane.setLayoutY(14.0);
        });
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        for (int i = 0; i < GameData.creatureOnHandSize + 1; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("MemberInHandBox.fxml"));
            final AnchorPane anchorPane = fxmlLoader.load();
            onHandCardControllers[i] = fxmlLoader.getController();
            if (i < GameData.creatureOnHandSize) {
                handBox.getChildren().add(anchorPane);
            }
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
        creatureName = (String) object;
        if (creatureName != null) {
            selectImageView.setOpacity(0.5);
        } else {
            selectImageView.setOpacity(0);
        }
        for (MemberInHandBoxController controller : onHandCardControllers) {
            if (!controller.getCreatureName().equals(creatureName)) {
                controller.getToggleButton().setSelected(false);
            } else {
                controller.getToggleButton().setSelected(true);
            }
        }
    }
}