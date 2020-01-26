package Command;

import Main.Main;
import Main.User;

public class ProfileCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::changeUsernameAndPassword, "change", "change: To change your account." +
                        "Enter a username and its password in tow different lines after this command."),
                new Command(this::deleteUser, "delete", "delete: To delete a user. " +
                        "Enter a username and its password in two different lines after this command."),
                new Command(this::renameUser, "rename", "rename: To rename your current username. " +
                        "Enter a username in a different line."),
                new Command(this::createUser, "create account", "create account: To create " +
                        "a new user. Enter a username and a password in two different lines after this command"),
                new Command(this::showUser, "show", "show: To see your username."),
        };
    }

    public void changeUsernameAndPassword(InputCommand inputCommand) throws Exception {
        String username = (String) inputCommand.getInputJsonObject().get("username");
        String password = (String) inputCommand.getInputJsonObject().get("password");
        menu.getConnection().getUser().changeUsername(username);
        menu.getConnection().getUser().changePassword(password);
        menu.getConnection().send("showLog","changeUsernameAndPassword successful");
        menu.run();
    }

    public void deleteUser(InputCommand inputCommand) throws Exception {
        String username = (String) inputCommand.getInputJsonObject().get("username");
        String password = (String) inputCommand.getInputJsonObject().get("password");
        User.deleteUser(username, password);
        menu.getConnection().send("showLog","deleteUser successful");
        menu.run();
    }

    public void renameUser(InputCommand inputCommand) throws Exception {
        String username = (String) inputCommand.getInputJsonObject().get("username");
        menu.getConnection().getUser().changeUsername(username);
        menu.run();
    }

    public void createUser(InputCommand inputCommand) throws Exception {
        String username = (String) inputCommand.getInputJsonObject().get("username");
        String password = (String) inputCommand.getInputJsonObject().get("password");
        new User(username, password);
        menu.getConnection().send("showLog","createUser successful");
        menu.run();
        // inja bayad current user avaz she???
    }

    public void showUser(InputCommand inputCommand) throws Exception {
        menu.getConnection().send("showUser", menu.getConnection().getUser().getUsername());
    }
}
