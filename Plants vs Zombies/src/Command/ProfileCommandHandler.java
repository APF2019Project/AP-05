package Command;

import Main.Main;
import Main.Menu;
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
        menu.getUser().changeUsername(username);
        menu.getUser().changePassword(password);
    }

    public void deleteUser(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        User.deleteUser(username, password);
    }

    public void renameUser(String command) throws Exception {
        String username = Main.scanLine();
        menu.getUser().changeUsername(username);
    }

    public void createUser(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        new User(username, password);
        // inja bayad current user avaz she???
    }

    public void showUser(String command) {
        Main.print(menu.getUser().getUsername());
    }
}
