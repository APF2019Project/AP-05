package Command;

import Main.Main;
import Main.PlantPlayer;
import Main.Player;
import Main.User;

public class LoginCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::createAccount, "create account", ""),
                new Command(this::login, "login", ""),
                new Command(this::leaderboard, "leaderboard", "")
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
