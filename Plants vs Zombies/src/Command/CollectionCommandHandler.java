package Command;

import Main.GameData;
import Objects.Creature;
import Objects.Plant;
import Objects.Zombie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.function.Supplier;

public class CollectionCommandHandler extends CommandHandler {
    public CollectionMode collectionMode;
    private Supplier<Void> supplier;

    {
        this.commands = new Command[]{
                new Command(this::showHandSize, "show hand size", "show hand size"),
                new Command(this::showHand, "show hand", "show hand: To show your selected cards"),
                new Command(this::showCollection, "show collection", "show collection: " +
                        "To show your not selected cards"),
                new Command(this::selectCard, "select", "select [name]: To select " +
                        "a card with the given name"),
                new Command(this::removeCard, "remove", "remove [name]: To remove " +
                        "a card from your hand"),
                new Command(this::play, "play", "play: To start the game")
        };
    }

    public CollectionCommandHandler(CollectionMode collectionMode, Supplier<Void> supplier) {
        this.collectionMode = collectionMode;
        this.supplier = supplier;
    }

    public void showHand(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getPlayer().getCreaturesOnHand()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("creature.getName", creature.getName());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("showHand", jsonArray);
    }

    public void showHandSize(InputCommand inputCommand) throws Exception {
        menu.getConnection().send("showHandSize", menu.getConnection().
                getUser().getPlayer().getCreaturesOnHand().size());
    }

    public void showCollection(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getUnlockedCreatures()) {
            if (menu.getConnection().getUser().getPlayer().getCreatureOnHandByName(creature.getName()) != null) {
                continue;
            }
            if ((creature instanceof Plant && CollectionMode.plantsCollection.equals(collectionMode))
                    || (creature instanceof Zombie && CollectionMode.zombiesCollection.equals(collectionMode))) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("creature.getName", creature.getName());
                jsonArray.add(jsonObject);
            }
        }
        menu.getConnection().send("showCollection", jsonArray);
    }

    public void selectCard(InputCommand inputCommand) throws Exception {
        String creatureName = (String) inputCommand.getInputJsonObject().get("creatureName");
        if (menu.getConnection().getUser().getPlayer().getCreatureOnHandByName(creatureName) != null) {
            throw new Exception("you have already selected this plant");
        }
        Creature creature = menu.getConnection().getUser().getUnlockedCreatureByName(creatureName);
        if (creature == null || (creature instanceof Plant && collectionMode.equals(CollectionMode.zombiesCollection))
                || (creature instanceof Zombie && collectionMode.equals(CollectionMode.plantsCollection))) {
            throw new Exception("invalid creatureName");
        }
        menu.getConnection().getUser().getPlayer().addCreaturesOnHand(creature);
        showHandSize(null);
    }

    public void removeCard(InputCommand inputCommand) throws Exception {
        String creatureName = (String) inputCommand.getInputJsonObject().get("creatureName");
        Creature creature = menu.getConnection().getUser().getUnlockedCreatureByName(creatureName);
        if (creature == null) {
            throw new Exception("invalid creatureName");
        }
        menu.getConnection().getUser().getPlayer().removeCreaturesOnHand(creature);
        showHandSize(null);
    }

    public void play(InputCommand inputCommand) throws Exception {
        if (menu.getConnection().getUser().getPlayer().getCreaturesOnHand().size() != GameData.creatureOnHandSize) {
            throw new Exception("count of creatures on hand should be " + GameData.creatureOnHandSize);
        }
        supplier.get();
    }
}