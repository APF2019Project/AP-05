package Command;

import Main.*;
import Player.Player;

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

    public CollectionCommandHandler() {
        super();
        onStart();
    }

    public void onStart() {
    }

    public void onEnd() {
    }

    public void showHand(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : Player.getCurrentPlayer().getCreaturesOnHand()) {
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void showCollection(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : Player.getCurrentPlayer().getUser().getUnlockedCreatures()) {
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void selectCard(String command) throws Exception {
        String cardName = Pattern.compile("select (.+)").matcher(command).group(1);
        Creature creature = Player.getCurrentPlayer().getUser().getUnlockedCreatureByName(cardName);
        if (creature == null) {
            throw new Exception("invalid cardName");
        }
        Player.getCurrentPlayer().addCreaturesOnHand(creature);
    }

    public void removeCard(String command) throws Exception {
        String cardName = Pattern.compile("remove (.+)").matcher(command).group(1);
        Creature creature = Player.getCurrentPlayer().getUser().getUnlockedCreatureByName(cardName);
        if (creature == null) {
            throw new Exception("invalid cardName");
        }
        Player.getCurrentPlayer().removeCreaturesOnHand(creature);
    }

    public void play(String command) {
        onEnd();
    }
}