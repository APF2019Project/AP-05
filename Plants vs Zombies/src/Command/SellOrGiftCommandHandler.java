package Command;

import Main.Menu;
import Main.User;
import Objects.Creature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class SellOrGiftCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showAll, "show", ""),
                new Command(this::endTurn, "end turn", ""),
                new Command(this::sellCard, "sell creature", ""),
                new Command(this::sendGiftCard,"send gift card","")
        };
    }

    public void endTurn(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), this).run();
    }
    public void showAll(InputCommand inputCommand) throws Exception {
        ArrayList<User> allUsers = User.getAllUsers();
        JSONArray jsonArray1 = new JSONArray();
        for (User user : allUsers) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.getUsername());
            jsonObject.put("imageAddress", user.getImageAddress());
            jsonArray1.add(jsonObject);
        }
        JSONArray jsonArray2 = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getUnlockedCreatures()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("creature.getName", creature.getName());
            jsonObject.put("creature.getPriceInShop", creature.getPriceInShop());
            jsonObject.put("creature.remainingInShop",creature.getRemainInShop());
            jsonArray2.add(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("all users",jsonArray1);
        jsonObject.put("all cards",jsonArray2);
        menu.getConnection().send("showAll", jsonObject);
    }
    public void sendGiftCard(InputCommand inputCommand) throws Exception {
        String creatureName = (String) inputCommand.getInputJsonObject().get("creatureName");
        String herUserName=(String) inputCommand.getInputJsonObject().get("herUserName");
        User user=User.getUserByUsername(herUserName);
        if(user==null || user==menu.getConnection().getUser()){
            throw new Exception("you can't send gift to your self");
        }
        Creature creature = Creature.getCreatureByName(creatureName);
        if (creature == null || menu.getConnection().getUser().getUnlockedCreatureByName(creatureName) == null) {
            throw new Exception("invalid cardName");
        }
        menu.getConnection().getUser().moveCreature(creature,user);
        menu.run();
    }
    public void sellCard(InputCommand inputCommand) throws Exception {
        String creatureName = (String) inputCommand.getInputJsonObject().get("creatureName");
        Creature creature = Creature.getCreatureByName(creatureName);
        if (creature == null || menu.getConnection().getUser().getUnlockedCreatureByName(creatureName) == null) {
            throw new Exception("invalid cardName");
        }
        menu.getConnection().getUser().sellCreatureToShop(creature);
        menu.run();
    }
}
