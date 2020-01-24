package Command;

import Main.Main;
import Main.Menu;
import Main.User;
import org.json.simple.JSONObject;

import java.util.Comparator;

public class LoginCommandHandler extends CommandHandler {
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
        JSONObject jsonObject = new JSONObject();
        JSONObject parametersJsonObject = new JSONObject();
        jsonObject.put("action", "showMessage");
        parametersJsonObject.put("message", "You created your account");
        parametersJsonObject.put("messageType", "INFORMATION");
        jsonObject.put("parameters", parametersJsonObject);
        menu.getConnection().send(jsonObject.toJSONString());
    }

    public void login(InputCommand inputCommand) throws Exception {
        String username = (String) inputCommand.getInputJsonObject().get("username");
        String password = (String) inputCommand.getInputJsonObject().get("password");
        menu.getConnection().setUser(User.login(username, password));
        new Menu(menu.getConnection(), new MainMenuCommandHandler()).run();
    }

    public void leaderboard(InputCommand inputCommand) throws Exception {
        User.getAllUsers().sort(Comparator.comparingInt(User::getKillingEnemyCount));
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : User.getAllUsers()) {
            stringBuilder.append(user.getUsername()).append(' ').append(user.getKillingEnemyCount()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }
}