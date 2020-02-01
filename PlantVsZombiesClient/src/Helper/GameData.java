package Helper;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class GameData {
    public final static int mapRowCount = 6;
    public final static int mapPlantColCount = 9;
    public final static int mapZombieColCount = 2;
    public final static int creatureOnHandSize = 7;
    public final static int speedConstant = 27;
    private final static HashMap<String, JSONObject> inputJsonForm = new HashMap<>();
    public static int slices = 10;//hatman zoj
    //public final static int mapColCount = mapPlantColCount * slices + slices / 2;

    static {
        JSONObject login = new JSONObject();
        login.put("menuFile", "InputForm");
        login.put("textField0.setPromptText", "username");
        login.put("textField1.setPromptText", "password");
        login.put("sendButton.setText", "Login");
        inputJsonForm.put("login", login);

        JSONObject createAccount = new JSONObject();
        createAccount.put("menuFile", "InputForm");
        createAccount.put("textField0.setPromptText", "Username");
        createAccount.put("textField1.setPromptText", "Password");
        createAccount.put("sendButton.setText", "Create Account");
        inputJsonForm.put("create account", createAccount);

        JSONObject changeUser = new JSONObject();
        changeUser.put("menuFile", "InputForm");
        changeUser.put("textField0.setPromptText", "Username");
        changeUser.put("textField1.setPromptText", "Password");
        changeUser.put("sendButton.setText", "Change");
        inputJsonForm.put("change user", changeUser);

        JSONObject deleteUser = new JSONObject();
        deleteUser.put("menuFile", "InputForm");
        deleteUser.put("textField0.setPromptText", "Username");
        deleteUser.put("textField1.setPromptText", "Password");
        deleteUser.put("sendButton.setText", "Delete");
        inputJsonForm.put("delete user", deleteUser);

        JSONObject rename = new JSONObject();
        rename.put("menuFile", "InputForm");
        rename.put("textField1.setVisible", false);
        rename.put("textField0.setPromptText", "Username");
        rename.put("sendButton.setText", "Rename");
        inputJsonForm.put("rename user", rename);
    }

    public static JSONObject getJson(String name) {
        JSONObject jsonObject = inputJsonForm.get(name);
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }
}
