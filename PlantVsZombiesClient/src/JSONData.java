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

        JSONObject changeUsername = new JSONObject();
        changeUsername.put("menuFile", "InputForm");
        changeUsername.put("textField0.setPromptText", "username");
        changeUsername.put("textField1.setPromptText", "password");
        changeUsername.put("sendButton.setText", "Change Username");
        inputJsonForm.put("change username", changeUsername);
    }

    public static JSONObject get(String name) {
        JSONObject jsonObject=inputJsonForm.get(name);
        if(jsonObject==null){
            jsonObject=new JSONObject();
        }
        return jsonObject;
    }
}
