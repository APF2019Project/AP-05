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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class PlantOnDayAndWaterModePlayerSceneController implements Controller {
    @FXML
    private ImageView selectImageView;
    @FXML
    private VBox handBox;

    private MemberInHandBoxController[] onHandCardControllers = new MemberInHandBoxController[GameData.creatureOnHandSize];
    private String selectedPlantName = null;
    private ImageView[][] imageViews = new ImageView[GameData.mapRowCount][];

    @FXML
    private AnchorPane gamePane;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
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
        JSONArray jsonArray = (JSONArray) object;
        for (int i = 0; i < GameData.creatureOnHandSize; i++) {
            final int index = i;
            Platform.runLater(() -> {
                onHandCardControllers[index].showHand((JSONObject) jsonArray.get(index));
            });
        }
    }

    public void showLawn(Object object) {
        JSONArray jsonArray = (JSONArray) object;
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            imageViews[((Long) jsonObject.get("y")).intValue()][((Long) jsonObject.get("x")).intValue() / 2]
                    .setImage(new Image(Main.getImageAddressByCreatureName((String) jsonObject.get("name"))));
        }
    }

    //1 base
    void put(int x, int y) throws IOException {
        System.out.println("put request:");
        if (selectedPlantName != null) {
            JSONObject jsonObject = new JSONObject();
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
                imageView.setFitWidth(gamePane.getPrefWidth() / GameData.mapPlantColCount);
                imageView.setFitHeight(gamePane.getPrefHeight() / GameData.mapRowCount + 10);
                //final int finalI = i, finalJ = j;

                Pane pane = new Pane();
                pane.setAccessibleText(i + "," + j);
                pane.setPrefWidth(gamePane.getPrefWidth() / GameData.mapPlantColCount);
                pane.setPrefHeight(gamePane.getPrefHeight() / GameData.mapRowCount + 10);
                pane.setMinHeight(pane.getPrefHeight());
                pane.setMaxHeight(pane.getPrefHeight());
                pane.setMinWidth(pane.getPrefWidth());
                pane.setMaxWidth(pane.getPrefWidth());
                pane.setLayoutY(i * (gamePane.getPrefHeight() / GameData.mapRowCount));
                pane.setLayoutX(j * gamePane.getPrefWidth() / GameData.mapPlantColCount);
                pane.setOnMouseClicked(mouseEvent -> {
                    try {
                        int finalI = Integer.parseInt(pane.getAccessibleText().split(",")[0]);
                        int finalJ = Integer.parseInt(pane.getAccessibleText().split(",")[1]);
                        put(finalJ * 2 + 2, finalI + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                pane.getChildren().add(imageView);
                gamePane.getChildren().add(pane);
            }
        }
        MenuHandler.getClient().send("show hand", null);
        MenuHandler.getClient().send("show lawn", null);
    }
}
