package Command;

import Main.*;

import java.util.regex.Pattern;

public class ShopCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showShop, "show shop", ""),
                new Command(this::showCollection, "show collection", ""),
                new Command(this::buy, "buy (.+)", ""),
                new Command(this::money, "money", "")
        };
    }

    public Menu nextMenu;
    public CollectionMode collectionMode;

    public void showShop(String command) {

    }

    public void showCollection(String command) {
        StringBuilder stringBuilder=new StringBuilder();
        for(Creature creature:Player.getCurrentPlayer().getUser().getUnlockedCreatures()){
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void buy(String command) {
        String cardName= Pattern.compile("select (.+)").matcher(command).group(1);
        Creature creature=Player.getCurrentPlayer().getUser().getCreatureByName(cardName);
        Player.getCurrentPlayer().addCreaturesOnHand(creature);
    }

    public void money(String command) {
        String cardName=Pattern.compile("remove (.+)").matcher(command).group(1);
        Creature creature=Player.getCurrentPlayer().getUser().getCreatureByName(cardName);
        Player.getCurrentPlayer().removeCreaturesOnHand(creature);
    }
}
