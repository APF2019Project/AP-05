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
        onStart();
    }

    public void onStart() {
    }

    public void onEnd() {
    }

    public void showHand(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getUser().getPlayer().getCreaturesOnHand()) {
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void showCollection(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getUser().getUnlockedCreatures()) {
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void selectCard(InputCommand inputCommand) throws Exception {
        String cardName = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput()).group(1);
        Creature creature = menu.getUser().getUnlockedCreatureByName(cardName);
        if (creature == null) {
            throw new Exception("invalid cardName");
        }
        menu.getUser().getPlayer().addCreaturesOnHand(creature);
    }

    public void removeCard(InputCommand inputCommand) throws Exception {
        String cardName = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput()).group(1);
        Creature creature = menu.getUser().getUnlockedCreatureByName(cardName);
        if (creature == null) {
            throw new Exception("invalid cardName");
        }
        menu.getUser().getPlayer().removeCreaturesOnHand(creature);
    }

    public void play(InputCommand inputCommand) throws Exception {
        onEnd();
        menu.exit();
    }
}