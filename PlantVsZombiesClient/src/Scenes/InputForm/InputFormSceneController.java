package Scenes.InputForm;

import Helper.Controller;
import Helper.MenuHandler;
import Helper.PrettyLabel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class InputFormSceneController implements Controller {
    @FXML
    private TextField textField0, textField1;
    @FXML
    private Button sendButton;
    @FXML
    private Label title;
    @FXML
    private Button backButton;
    @FXML
    private MenuButton menuButton;
    @FXML
    private ImageView imageView;
    private String address;

    @FXML
    void initialize() {
        PrettyLabel.playBackgroundColorAnimation(title);
    }

    @FXML
    void onSendButtonMouseClicked() throws IOException {
        JSONObject data = new JSONObject();
        if (textField0.isVisible()) {
            data.put(textField0.getPromptText().toLowerCase(), textField0.getText());
        }
        if (textField1.isVisible()) {
            data.put(textField1.getPromptText().toLowerCase(), textField1.getText());
        }
        if(menuButton.isVisible()) {
            data.put("imageAddress", address);
        }
        MenuHandler.getClient().send(sendButton.getText().toLowerCase(), data);
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.closeScene();
    }

    @Override
    public void initializeReOpen() {
        PrettyLabel.playBackgroundColorAnimation(title);
        try {
            if(!title.isVisible())
                MenuHandler.getClient().send("show", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUser(Object object) {
        JSONObject jsonObject = (JSONObject) object;
        address = (String) jsonObject.get("imageAddress");
        Platform.runLater(() -> {
            imageView.setImage(new Image(new File(address).toURI().toString()));
        });
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) {
        File profilePics = new File("../Profile Pictures");
        System.out.println(profilePics.getAbsolutePath());
        ArrayList<MenuItem> arrayList = new ArrayList<>();
        for (File file : Objects.requireNonNull(profilePics.listFiles())) {
            ImageView img = new ImageView(new Image(file.toURI().toString()));
            MenuItem menuItem = new MenuItem();
            menuItem.setGraphic(img);
            menuItem.setOnAction(actionEvent -> {
                imageView.setImage(img.getImage());
                address = file.getPath();
            });
            img.setFitHeight(70);
            img.setFitWidth(70);
            arrayList.add(menuItem);
        }
        Platform.runLater(() -> {
            menuButton.getItems().addAll(arrayList);
        });

        menuButton.setVisible((boolean) jsonObject.getOrDefault("menuButton.setVisible", false));
        imageView.setVisible((boolean) jsonObject.getOrDefault("menuButton.setVisible", false));
        title.setVisible(!(boolean) jsonObject.getOrDefault("menuButton.setVisible", false));
        if (jsonObject.containsKey("textField0.setPromptText")) {
            textField0.setPromptText((String) jsonObject.get("textField0.setPromptText"));
        }
        if (jsonObject.containsKey("textField1.setPromptText")) {
            textField1.setPromptText((String) jsonObject.get("textField1.setPromptText"));
        }
        if (jsonObject.containsKey("textField0.setVisible")) {
            textField0.setVisible((boolean) jsonObject.get("textField0.setVisible"));
        }
        if (jsonObject.containsKey("textField1.setVisible")) {
            textField1.setVisible((boolean) jsonObject.get("textField1.setVisible"));
        }
        if (jsonObject.containsKey("sendButton.setText")) {
            sendButton.setText((String) jsonObject.get("sendButton.setText"));
        }

        try {
            if(!title.isVisible())
                MenuHandler.getClient().send("show", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
