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

    public void createAccount(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        menu.exitAndOpen(new Menu(new User(username, password), menu, new LoginCommandHandler()));
    }

    public void login(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        menu.exitAndOpen(new Menu(User.login(username, password), menu, new MainMenuCommandHandler()));
    }

    public void leaderboard(String command) throws Exception {
        User.getAllUsers().sort(Comparator.comparingInt(User::getKillingEnemyCount));
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : User.getAllUsers()) {
            stringBuilder.append(user.getUsername()).append(' ').append(user.getKillingEnemyCount()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }
}
