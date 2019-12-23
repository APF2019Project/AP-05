import java.util.Comparator;

public class ProfileCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::changeUsernameAndPassword, "change", ""),
                new Command(this::deleteUser, "delete", ""),
                new Command(this::showLeaderboard, "rename", ""),
                new Command(this::showLeaderboard, "create", ""),
                new Command(this::showLeaderboard, "show", ""),
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
        User
        Main.currentUser.setCurrentMenu(Main.profileMenu);
    }
}
