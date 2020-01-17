package Player;

import Command.PlantOnRailModePlayerCommandHandler;
import Main.Menu;
import Main.User;
import Objects.Creature;
import Objects.Plant;

import java.util.Random;

public class PlantOnRailGameModeHumanPlayer extends PlantPlayer {
    public PlantOnRailGameModeHumanPlayer(User user) {
        super(user);
    }
    private int remainTurnToAddCard=0;
    @Override
    public void doAction() throws Exception {
        Menu PlantOnRailModePlayerMenu = new Menu(user, new PlantOnRailModePlayerCommandHandler());
        PlantOnRailModePlayerMenu.run();
    }

    @Override
    public void gameAction() {
        Random random=new Random();
        if(remainTurnToAddCard==0){
            if(this.getCreaturesOnHand().size()!=10) {
                remainTurnToAddCard = 1 + random.nextInt(3);
                Plant plant = Plant.getAllPlants().get(random.nextInt(Plant.getAllPlants().size()));
                this.addCreaturesOnHand(plant);
            }
        }else{
            remainTurnToAddCard--;
        }
    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
        return true;
    }

    @Override
    public void pickCards() throws Exception {
        //nothing
    }
}
