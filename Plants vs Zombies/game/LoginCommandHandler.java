package game;
import java.util.regex.*;

public class LoginCommandHandler extends CommandHandler {
    public static final String createAccount = "create account";
    public static final String login = "login";
    public static final String leaderboard = "leaderboard";

    private LoginCommandHandler()
    {
        commands.add(createAccount);
        commands.add(login);
        commands.add(leaderboard);
    }

    boolean matchAndDo(String command) {
        if(Pattern.matches(createAccount, command)) {

        }
        else if(Pattern.matches(login, command)) {

        }
        else if(Pattern.matches(leaderboard, command)) {

        }
    }
}
