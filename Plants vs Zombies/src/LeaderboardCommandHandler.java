import java.util.Comparator;

public class LeaderboardCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showLeaderboard, "leaderboard", ""),
        };
    }

    public void showLeaderboard(String command) throws Exception {
        User.getAllUsers().sort(Comparator.comparingInt(User::getKillingEnemyCount));
        StringBuilder stringBuilder=new StringBuilder();
        for(User user:User.getAllUsers()){
            stringBuilder.append(user.getUsername()).append(' ').append(user.getKillingEnemyCount()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }
}
