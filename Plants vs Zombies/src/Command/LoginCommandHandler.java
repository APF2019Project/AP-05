package Command;

import Main.Main;
import PlantPlayer;
import Player.Player;
import Main.User;

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
        new User(username, password);
        Player.getCurrentPlayer().setCurrentMenu(Main.loginMenu);
    }

    public void login(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        Player.setCurrentPlayer(new PlantPlayer(User.login(username, password)));
        Player.getCurrentPlayer().setCurrentMenu(Main.mainMenu);
    }

    public void leaderboard(String command) throws Exception {
        Player.getCurrentPlayer().setCurrentMenu(Main.leaderboard);
    }
}
