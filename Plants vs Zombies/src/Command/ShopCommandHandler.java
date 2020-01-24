package Command;

import Main.Main;
import Main.Menu;
import Objects.Creature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showShop, "show shop", "show shop: To see your " +
                        "not bought cards."),
                new Command(this::showCollection, "show collection", "show collection: To see your " +
                        "bought cards."),
                new Command(this::buy, "buy (.+)", "buy [name]: To buy card with given name."),
                new Command(this::money, "money", "money: To see your amount of coins.")
        };
    }

    public void showShop(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of your not bought cards:\n");
        for (Creature creature : menu.getUser().getLockedCreatures()) {
            stringBuilder.append("\nName: ").append(creature.getName())
                    .append("\nPrice: ").append(creature.getPriceInShop()).append("\n");
        }
        Main.print(stringBuilder.toString());
    }

    public void showCollection(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of your bought cards:\n");
        for (Creature creature : menu.getUser().getUnlockedCreatures()) {
            stringBuilder.append("\nName: ").append(creature.getName())
                    .append("\nPrice: ").append(creature.getPriceInShop()).append("\n");
        }
        Main.print(stringBuilder.toString());
    }

    public void buy(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in shopCommandHandler buy method");
        }
        String creatureName = matcher.group(1);
        Creature creature = Creature.getCreatureByName(creatureName);
        if (creature == null || menu.getUser().getUnlockedCreatureByName(creatureName) != null) {
            throw new Exception("invalid cardName");
        }
        if (!menu.getUser().buyCreatureFromShop(creature)) {
            throw new Exception("you don't have enough money");
        }
    }

    public void money(InputCommand inputCommand) {
        Main.print("Money: " + menu.getUser().getCoinForShop() + "$");
    }
}