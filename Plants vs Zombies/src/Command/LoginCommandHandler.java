package Command;

import Main.Main;
import Main.Menu;
import Main.User;

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
        Main.print("Enter your username:");
        String username = Main.scanLine();
        Main.print("Enter your password:");
        String password = Main.scanLine();
        new User(username, password);
    }

    public void login(InputCommand inputCommand) throws Exception {
        Main.print("Enter your username:");
        String username = Main.scanLine();
        Main.print("Enter your password:");
        String password = Main.scanLine();
        new Menu(User.login(username, password), new MainMenuCommandHandler()).run();
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