package Command;

import Main.Main;
import Objects.Creature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ShopCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showShop, "show shop", "show shop: To see your " +
                        "not bought cards."),
                new Command(this::showCollection, "show collection", "show collection: To see your " +
                        "bought cards."),
                new Command(this::buy, "buy", "buy [name]: To buy card with given name."),
                new Command(this::money, "money", "money: To see your amount of coins.")
        };
    }

    public void showShop(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getLockedCreatures()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("creature.getName", creature.getName());
            jsonObject.put("creature.getPriceInShop", creature.getPriceInShop());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("showShop", jsonArray);
    }

    public void showCollection(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getUnlockedCreatures()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("creature.getName", creature.getName());
            jsonObject.put("creature.getPriceInShop", creature.getPriceInShop());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("showCollection", jsonArray);
    }

    public void buy(InputCommand inputCommand) throws Exception {
        String creatureName = (String) inputCommand.getInputJsonObject().get("creatureName");
        Creature creature = Creature.getCreatureByName(creatureName);
        if (creature == null || menu.getConnection().getUser().getUnlockedCreatureByName(creatureName) != null) {
            throw new Exception("invalid cardName");
        }
        if (!menu.getConnection().getUser().buyCreatureFromShop(creature)) {
            throw new Exception("you don't have enough money");
        }
        menu.run();
    }

    public void money(InputCommand inputCommand) throws Exception {
        menu.getConnection().send("money", menu.getConnection().getUser().getCoinForShop());
    }
}