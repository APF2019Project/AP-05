package Command;

import Main.Connection;
import Main.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PVPLobbyCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::show, "show", ""),
                new Command(this::wantEnterGame, "wantEnterGame", "")
        };
    }

    public void show(InputCommand inputCommand) throws Exception {
        HashMap<String, Boolean> allUserState = new HashMap<String, Boolean>();
        for (User user : User.getAllUsers()) {
            allUserState.put(user.getUsername(), false);
        }
        for (Connection connection : Connection.getAllConnection()) {
            User user = connection.getUser();
            if (user != null) {
                allUserState.put(user.getUsername(), true);
            }
        }
        JSONObject a = new JSONObject();
        for (String username : allUserState.keySet()) {
            if (allUserState.get(username)) {
                User user=User.getUserByUsername(username);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", user.getUsername());
                    jsonObject.put("imageAddress", user.getImageAddress());



                menu.getConnection().send("showAllUsers", jsonArray);
                System.out.println(username + ": Online");
            }
            else {
                System.out.println(username + ": OffLine");
            }
        }

    }

    public void wantEnterGame(InputCommand inputCommand) throws Exception {

    }
}
