package Scenes.Games;

import Helper.Controller;
import Helper.GameData;
import Helper.Main;
import Helper.MenuHandler;
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

public abstract class GameController implements Controller {
    @FXML
    protected ImageView selectImageView;
    @FXML
    protected VBox handBox;

    protected MemberInHandBoxController[] onHandCardControllers = new MemberInHandBoxController[GameData.creatureOnHandSize];
    protected ImageView[][] imageViews;
    protected ArrayList<Pane> zombiesPanes = new ArrayList<>();

    private int mapRowCount, mapColCount, mapColCountWithSlice;

    public GameController(int mapRowCount, int mapColCount) {
        this.mapRowCount = mapRowCount;
        this.mapColCount = mapColCount;
        mapColCountWithSlice = mapColCount * GameData.slices + GameData.slices / 2;
        imageViews = new ImageView[mapColCount][];
    }

    @FXML
    private Label sunLabel;

    @FXML
    protected AnchorPane gamePane;
    private String creatureName;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitDoubleMenu", null);
        MenuHandler.closeDoubleScene();
    }

    protected double getPlantWidth() {
        return gamePane.getPrefWidth() / mapColCount;
    }

    protected double getPlantHeight() {
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
        return x * getPlantWidth();

    }

    protected double getPlantLayoutY(int y) {
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

    public void showLawn(Object object) {
        Platform.runLater(() -> {
            for (Pane pane : zombiesPanes) {
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
                        Pane pane = newPaneWithSize(getZombieWidth() / 3, getZombieHeight() / 3 + 10);
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

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        for (int i = 0; i < GameData.creatureOnHandSize; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("MemberInHandBox.fxml"));
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
        for (int i = 0; i < mapRowCount; i++) {
            imageViews[i] = new ImageView[mapColCount];
            for (int j = 0; j < mapColCount; j++) {
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