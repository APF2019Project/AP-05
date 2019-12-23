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
        Main.currentUser.setCurrentMenu(Main.loginMenu);
    }

    public void login(String command) throws Exception {
        String username = Main.scanLine();
        String password = Main.scanLine();
        Main.currentUser = User.login(username, password);
        Main.currentUser.setCurrentMenu(Main.mainMenu);
    }

    public void leaderboard(String command) throws Exception{
        Main.currentUser.setCurrentMenu(Main.leaderboard);
    }
}
