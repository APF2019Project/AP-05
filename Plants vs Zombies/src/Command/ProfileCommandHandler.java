package Command;

import Main.Main;
import Player.Player;
import Main.User;

public class ProfileCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::changeUsernameAndPassword, "change", ""),
                new Command(this::deleteUser, "delete", "delete: To delete a user. " +
                        "Enter a username and its password in two different lines."),
                new Command(this::renameUser, "rename", "rename: To rename your current username. " +
                        "Enter a username in a different line."),
                new Command(this::createUser, "create", "create account: To create " +
                        "a new user. Enter a username and a password in two different lines after this command"),
                new Command(this::showUser, "show", "show: To see your username."),
        };
    }

    public void changeUsernameAndPassword(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        Player.getCurrentPlayer().getUser().changeUsername(username);
        Player.getCurrentPlayer().getUser().changePassword(password);
        Player.getCurrentPlayer().getUser().setCurrentMenu(Main.profileMenu);
    }

    public void deleteUser(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        User.deleteUser(username, password);
        Player.getCurrentPlayer().setCurrentMenu(Main.profileMenu);
    }

    public void renameUser(String command) throws Exception {
        String username = Main.scanLine();
        Player.getCurrentPlayer().getUser().changeUsername(username);
        Player.getCurrentPlayer().setCurrentMenu(Main.profileMenu);
    }

    public void createUser(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        new User(username, password);
        Player.getCurrentPlayer().setCurrentMenu(Main.profileMenu);
    }

    public void showUser(String command) {
        Main.print(Player.getCurrentPlayer().getUser().getUsername());
        Player.getCurrentPlayer().setCurrentMenu(Main.profileMenu);
    }
}
