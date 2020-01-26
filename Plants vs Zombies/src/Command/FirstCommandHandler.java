package Command;

import Main.Menu;
import Main.User;
import org.json.simple.JSONObject;

public class FirstCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::createAccount, "create account", "create account: To create " +
                        "a new account. Enter a username and a password in two different lines after this command"),
                new Command(this::login, "login", "login: To login in an account. " +
                        "Enter a username and a password in two different lines after this command."),
                new Command(this::leaderboard, "leaderboard", "leaderboard: To see " +
                        "leaderboard of all users.")
        };
    }

    public void createAccount(InputCommand inputCommand) throws Exception {
        String username = (String) inputCommand.getInputJsonObject().get("username");
        String password = (String) inputCommand.getInputJsonObject().get("password");
        menu.getConnection().setUser(new User(username, password));
        new Menu(menu.getConnection(), new MainMenuCommandHandler()).run();
        JSONObject data = new JSONObject();
        data.put("message", "You created your account");
        data.put("messageType", "INFORMATION");
        menu.getConnection().send("showMessage", data);
    }

    public void login(InputCommand inputCommand) throws Exception {
        String username = (String) inputCommand.getInputJsonObject().get("username");
        String password = (String) inputCommand.getInputJsonObject().get("password");
        menu.getConnection().setUser(User.login(username, password));
        new Menu(menu.getConnection(), new MainMenuCommandHandler()).run();
        menu.getConnection().send("showLog", "login successful");
    }

    public void leaderboard(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), new LeaderboardCommandHandler()).run();
    }
}