package Command;

import Main.GameData;
import Main.Main;
import Main.User;
import Objects.Creature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Comparator;

public class LeaderboardCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::leaderboard, "leaderboard", "")
        };
    }

    public void leaderboard(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        User.getAllUsers().sort((user0, user1) -> user1.getKillingEnemyCount()-user0.getKillingEnemyCount());
        for (User user : User.getAllUsers()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user.getUsername", user.getUsername());
            jsonObject.put("user.getKillingEnemyCount", user.getKillingEnemyCount());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("leaderboard", jsonArray);
    }
}
