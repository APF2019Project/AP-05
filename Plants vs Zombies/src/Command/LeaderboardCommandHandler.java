package Command;

import Main.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LeaderboardCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::leaderboard, "leaderboard", "")
        };
    }

    public void leaderboard(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        User.getAllUsers().sort((user0, user1) -> user1.getKillingEnemyCount() - user0.getKillingEnemyCount());
        for (User user : User.getAllUsers()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user.getUsername", user.getUsername());
            jsonObject.put("user.getKillingEnemyCount", user.getKillingEnemyCount());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("leaderboard", jsonArray);
    }
}
