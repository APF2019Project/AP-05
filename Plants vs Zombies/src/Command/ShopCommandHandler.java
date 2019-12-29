package Command;

import Main.*;
import Player.Player;

import java.util.regex.Pattern;

public class ShopCommandHandler extends CommandHandler {
    public Menu nextMenu;
    public CollectionMode collectionMode;

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
        for (Creature creature : menu.getUser().getLockedCreatures()) {
            stringBuilder.append(creature.getName()).append(" ").append(creature.getPrice()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void showCollection(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getUser().getUnlockedCreatures()) {
            stringBuilder.append(creature.getName()).append(" ").append(creature.getPrice()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void buy(InputCommand inputCommand) throws Exception {
        String cardName = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput()).group(1);
        if (menu.getUser().getUnlockedCreatureByName(cardName) != null) {
            throw new Exception("invalid cardName");
        }

    }

    public void money(InputCommand inputCommand) {
        Main.print(String.valueOf(menu.getUser().getCoin()));
    }
}
