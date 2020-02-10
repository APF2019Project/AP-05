package Scenes.CustomCard;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class CustomCardController implements Controller {
    @FXML
    private VBox fieldBox;
    private JSONObject jsonObject;
    private ArrayList<MemberInCustomCardController> arrayList = new ArrayList<>();
    private String address;

    void copy(File source, File dest) throws IOException {
        dest.createNewFile();
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    @FXML
    void onCreateButtonMouseClicked() throws IOException {
        for (MemberInCustomCardController controller : arrayList) {
            jsonObject.put(controller.getFieldnameLabel(), controller.getText());
        }
        System.out.println(address);
        jsonObject.put("imageAddress", address);
        File file=new File(address);
        new File(("../Gallery/")+jsonObject.get("name")).mkdir();
        copy(file,new File(("../Gallery/")+jsonObject.get("name")+"/"+file.getName()));
        jsonObject.put("imageAddress", address);
        MenuHandler.getClient().send("create", jsonObject);
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show fields", null);
    }

    @Override
    public void initializeReOpen() throws IOException {

    }

    public void showFields(Object object) throws IOException {
        JSONObject jsonObject = (JSONObject) object;
        this.jsonObject = jsonObject;
        for (Object obj : jsonObject.keySet().toArray()) {
            String fieldName = (String) obj;
            if (jsonObject.get(obj) == null)
                continue;
            JSONObject memberJsonObject = new JSONObject();
            memberJsonObject.put("fieldname", fieldName);
            memberJsonObject.put("value", jsonObject.get(obj));
            FXMLLoader fxmlLoader = new FXMLLoader(MenuHandler
                    .class.getResource("../Scenes/CustomCard/MemberInCustomCard.fxml"));
            BorderPane borderPane = fxmlLoader.load();
            MemberInCustomCardController controller = fxmlLoader.getController();
            controller.initJsonInput(memberJsonObject);
            Platform.runLater(() -> fieldBox.getChildren().add(borderPane));
            arrayList.add(controller);
        }
        addPictureField();
    }

    private void addPictureField() {
        MenuButton menuButton = new MenuButton();
        File gallery = new File(("../Gallery/"));
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for (File directory : Objects.requireNonNull(gallery.listFiles())) {
            if (directory.isDirectory()) {
                for (File file : Objects.requireNonNull(directory.listFiles())) {
                    ImageView img = new ImageView(new Image(file.toURI().toString()));
                    MenuItem menuItem = new MenuItem();

                    menuItem.setGraphic(img);
                    menuItem.setOnAction(actionEvent -> address = file.getPath());
                    img.setFitHeight(70);
                    img.setFitWidth(70);
                    menuItems.add(menuItem);
                }
            }
        }
        Platform.runLater(() -> {
            menuButton.getItems().addAll(menuItems);
            fieldBox.getChildren().add(menuButton);
        });
    }
}
