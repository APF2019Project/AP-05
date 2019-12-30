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
        String creatureName = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput()).group(1);
        Creature creature=Creature.getCreatureByName(creatureName);
        if (creature==null || menu.getUser().getUnlockedCreatureByName(creatureName) != null) {
            throw new Exception("invalid cardName");
        }
        if(!menu.getUser().buyCreatureFromShop(creature)){
            throw new Exception("you don't have enough money");
        }
    }

    public void money(InputCommand inputCommand) {
        Main.print(String.valueOf(menu.getUser().getCoinForShop()));
    }
}
