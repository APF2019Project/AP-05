import org.json.simple.JSONObject;

import java.util.HashMap;

public class JSONData {
    private final static HashMap<String, JSONObject> inputJsonForm = new HashMap<>();

    static {
        JSONObject login = new JSONObject();
        login.put("menuFile", "InputForm");
        login.put("textField0.setPromptText", "username");
        login.put("textField1.setPromptText", "password");
        login.put("sendButton.setText", "Login");
        inputJsonForm.put("login", login);

        JSONObject createAccount = new JSONObject();
        createAccount.put("menuFile", "InputForm");
        createAccount.put("textField0.setPromptText", "username");
        createAccount.put("textField1.setPromptText", "password");
        createAccount.put("sendButton.setText", "Create Account");
        inputJsonForm.put("create account", createAccount);

        JSONObject changeUser = new JSONObject();
        changeUser.put("menuFile", "InputForm");
        changeUser.put("textField0.setPromptText", "username");
        changeUser.put("textField1.setPromptText", "password");
        changeUser.put("sendButton.setText", "Change Username");
        inputJsonForm.put("change user", changeUser);

        JSONObject deleteUser = new JSONObject();
        deleteUser.put("menuFile", "InputForm");
        deleteUser.put("textField0.setPromptText", "username");
        deleteUser.put("textField1.setPromptText", "password");
        deleteUser.put("sendButton.setText", "Delete User");
        inputJsonForm.put("delete user", deleteUser);

        JSONObject rename = new JSONObject();
        rename.put("menuFile", "InputForm");
        rename.put("textField1.setVisible", false);
        rename.put("textField0.setPromptText", "username");
        rename.put("sendButton.setText", "Rename");
        inputJsonForm.put("rename user", rename);
    }

    public static JSONObject get(String name) {
        JSONObject jsonObject=inputJsonForm.get(name);
        if(jsonObject==null){
            jsonObject=new JSONObject();
        }
        return jsonObject;
    }
}
