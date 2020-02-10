package Scenes.SellOrGift;

import Helper.Controller;
import Helper.MenuHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SellOrGiftSceneController implements Controller {
    @FXML
    private VBox creatureList,userList;
    @FXML
    private Button backButton;
    @FXML
    private Button sellToShop;
    @FXML
    private ScrollPane creatureScrollPane,userScrollPane;

    @FXML
    void onBackButtonMouseClicked() throws IOException {
        MenuHandler.getClient().send("exitMenu", null);
        MenuHandler.closeScene();
    }
    static public SellOrGiftSceneController lastSellOrGiftSceneController ;

    @FXML
    void onSellToServerMouseClicked() throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("creatureName", getSelectedName());
        }catch (Exception e){
            System.out.println("INA KE MA MIBINIM");
            e.printStackTrace();
        }
        MenuHandler.getClient().send("sell creature", jsonObject);
    }

    @FXML
    void initialize() throws IOException {
    }

    @Override
    public void initializeReOpen() {
    }


    public SellOrGiftSceneController() {
        lastSellOrGiftSceneController=this;
//        userScrollPane.setVisible(false);
    }

    public String getSelectedName() throws Exception{
        for(Node a:creatureList.getChildren()){
            BorderPane b=(BorderPane )a;
            Label c=(Label)(b.getCenter());
            VBox d=(VBox)(b.getRight());
            CheckBox e=(CheckBox)(d.getChildren().get(0));
            System.out.println(c.getText()+":"+e.isSelected());
            if(e.isSelected()){
                return c.getText();
            }
        }
        throw  new Exception("no creature was selected");
    }
    public void show(Object object) throws IOException {
        JSONObject jsonObject = (JSONObject) object;
        JSONArray userJsonArray = (JSONArray)jsonObject.get("all users");
        JSONArray cardJsonArray = (JSONArray)jsonObject.get("all cards");
        for (Object objectForCreature : cardJsonArray) {
            JSONObject jsonObjectForCreature = (JSONObject) objectForCreature;
            jsonObjectForCreature.put("selected", false);
            BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                    "Scenes/SellOrGift/MemberInCollectionList.fxml", jsonObjectForCreature);
            Platform.runLater(() -> {
                creatureList.getChildren().add(borderPane);
            });
        }
        for (Object objectForUser : userJsonArray) {
            JSONObject jsonObjectForUser = (JSONObject) objectForUser;
            BorderPane borderPane = MenuHandler.getPaneWithDefaultParametersHandler(
                    "Scenes/SellOrGift/userMember.fxml", jsonObjectForUser);
            Platform.runLater(() -> {
                userList.getChildren().add(borderPane);
            });
        }
    }

    @Override
    public void initJsonInput(JSONObject jsonObject) throws IOException {
        MenuHandler.getClient().send("show", null);
    }
}
