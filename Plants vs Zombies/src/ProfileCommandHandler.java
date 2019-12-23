import java.util.Comparator;

public class ProfileCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::changeUsernameAndPassword, "change", ""),
                new Command(this::deleteUser, "delete", ""),
                new Command(this::renameUser, "rename", ""),
                new Command(this::createUser, "create", ""),
                new Command(this::showUser, "show", ""),
        };
    }

    public void changeUsernameAndPassword(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        Main.currentUser.changeUsername(username);
        Main.currentUser.changePassword(password);
        Main.currentUser.setCurrentMenu(Main.profileMenu);
    }

    public void deleteUser(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        User.deleteUser(username, password);
        Main.currentUser.setCurrentMenu(Main.profileMenu);
    }

    public void renameUser(String command) throws Exception {
        String username = Main.scanLine();
        Main.currentUser.changeUsername(username);
        Main.currentUser.setCurrentMenu(Main.profileMenu);
    }

    public void createUser(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        new User(username, password);
        Main.currentUser.setCurrentMenu(Main.profileMenu);
    }

    public void showUser(String command) {
        Main.print(Main.currentUser.getUsername());
        Main.currentUser.setCurrentMenu(Main.profileMenu);
    }
}
