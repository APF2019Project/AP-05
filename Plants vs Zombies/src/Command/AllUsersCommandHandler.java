package Command;

import Main.Menu;
import Main.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AllUsersCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showAllUsers, "show all users", ""),
                new Command(this::EnterChat, "enter chat", ""),
                new Command(this::endTurn, "end turn", ""),
        };
    }

    public void endTurn(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), this).run();
    }

    public void showAllUsers(InputCommand inputCommand) {
        ArrayList<User> allUsers = User.getAllUsers();
        JSONArray jsonArray = new JSONArray();
        for (User user : allUsers) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.getUsername());
            jsonObject.put("imageAddress", user.getImageAddress());
            jsonArray.add(jsonObject);
        }

        menu.getConnection().send("showAllUsers", jsonArray);
    }

    public void EnterChat(InputCommand inputCommand) throws Exception {
        String otherUsername = (String) inputCommand.getInputJsonObject().get("username");
        User otherUser = User.getUserByUsername(otherUsername);
        if (otherUser == null) {
            throw new Exception("user doesn't exist anymore!");
        }

        new Menu(menu.getConnection(), new ChatCommandHandler(menu.getConnection().getUser(), otherUser)).run();
    }
}
