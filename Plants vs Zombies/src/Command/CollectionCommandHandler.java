package Command;

import Main.CollectionMode;
import Main.Main;
import Main.Player;
import Main.Menu;
import Main.ActiveCard;
import Main.Creature;

import java.util.regex.Pattern;

public class CollectionCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", ""),
                new Command(this::showCollection, "show collection", ""),
                new Command(this::selectCard, "select (.+)", ""),
                new Command(this::removeCard, "remove (.+)", ""),
                new Command(this::play, "play", "")
        };
    }

    CollectionCommandHandler(){
        super();
        onStart();
    }

    public void onStart() {
    }

    public void onEnd(){
    }

    public Menu nextMenu;
    public CollectionMode collectionMode;

    public void showHand(String command) {
        StringBuilder stringBuilder=new StringBuilder();
        for(Creature creature:Player.getCurrentPlayer().getCreaturesOnHand()){
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void showCollection(String command) {
        StringBuilder stringBuilder=new StringBuilder();
        for(Creature creature:Player.getCurrentPlayer().getUser().getUnlockedCreatures()){
            stringBuilder.append(creature.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    public void selectCard(String command) throws Exception {
        String cardName=Pattern.compile("select (.+)").matcher(command).group(1);
        Creature creature=Player.getCurrentPlayer().getUser().getUnlockedCreatureByName(cardName);
        if(creature==null){
            throw new Exception("invalid cardName");
        }
        Player.getCurrentPlayer().addCreaturesOnHand(creature);
    }

    public void removeCard(String command) throws Exception {
        String cardName=Pattern.compile("remove (.+)").matcher(command).group(1);
        Creature creature=Player.getCurrentPlayer().getUser().getUnlockedCreatureByName(cardName);
        if(creature==null){
            throw new Exception("invalid cardName");
        }
        Player.getCurrentPlayer().removeCreaturesOnHand(creature);
    }

    public void play(String command){
        onEnd();
        Player.getCurrentPlayer().setCurrentMenu(nextMenu);
    }
}