package Command;

import Main.GameData;
import Main.Main;
import Objects.Creature;
import Objects.Plant;
import Objects.Zombie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionCommandHandler extends CommandHandler {
    public CollectionMode collectionMode;

    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", "show hand: To show your selected cards"),
                new Command(this::showCollection, "show collection", "show collection: " +
                        "To show your not selected cards"),
                new Command(this::selectCard, "select (.+)", "select [name]: To select " +
                        "a card with the given name"),
                new Command(this::removeCard, "remove (.+)", "remove [name]: To remove " +
                        "a card from your hand"),
                new Command(this::play, "play", "play: To start the game")
        };
    }

    public CollectionCommandHandler(CollectionMode collectionMode) {
        this.collectionMode = collectionMode;
    }

    public void showHand(InputCommand inputCommand) {
        Main.print("Your hand:");
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getConnection().getUser().getPlayer().getCreaturesOnHand()) {
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void showCollection(InputCommand inputCommand) {
        Main.print("Your collection:");
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getConnection().getUser().getUnlockedCreatures()) {
            if (menu.getConnection().getUser().getPlayer().getCreatureOnHandByName(creature.getName()) != null) {
                continue;
            }
            if ((creature instanceof Plant && CollectionMode.plantsCollection.equals(collectionMode))
                    || (creature instanceof Zombie && CollectionMode.zombiesCollection.equals(collectionMode))) {
                stringBuilder.append(creature.getName()).append('\n');
            }
        }
        Main.print(stringBuilder.toString());
    }

    public void selectCard(InputCommand inputCommand) throws Exception {
        String cardName = (String) inputCommand.getInputJsonObject().get("cardName");
        if (menu.getConnection().getUser().getPlayer().getCreatureOnHandByName(cardName) != null) {
            throw new Exception("you have already selected this plant");
        }
        Creature creature = menu.getConnection().getUser().getUnlockedCreatureByName(cardName);
        if (creature == null || (creature instanceof Plant && collectionMode.equals(CollectionMode.zombiesCollection))
                || (creature instanceof Zombie && collectionMode.equals(CollectionMode.plantsCollection))) {
            throw new Exception("invalid cardName");
        }
        menu.getConnection().getUser().getPlayer().addCreaturesOnHand(creature);
    }

    public void removeCard(InputCommand inputCommand) throws Exception {
        String cardName = (String) inputCommand.getInputJsonObject().get("cardName");
        Creature creature = menu.getConnection().getUser().getUnlockedCreatureByName(cardName);
        if (creature == null) {
            throw new Exception("invalid cardName");
        }
        menu.getConnection().getUser().getPlayer().removeCreaturesOnHand(creature);
    }

    public void play(InputCommand inputCommand) throws Exception {
        if (menu.getConnection().getUser().getPlayer().getCreaturesOnHand().size() != GameData.creatureOnHandSize) {
            throw new Exception("count of creatures on hand should be " + GameData.creatureOnHandSize);
        }
        menu.exit();
    }
}