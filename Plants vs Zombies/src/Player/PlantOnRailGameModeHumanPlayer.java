package Player;

import Command.PlantOnRailModePlayerCommandHandler;
import Main.Connection;
import Main.Menu;
import Objects.Creature;
import Objects.Plant;

import java.util.Random;
import java.util.function.Supplier;

public class PlantOnRailGameModeHumanPlayer extends PlantPlayer {
    private int remainTurnToAddCard = 0;

    public PlantOnRailGameModeHumanPlayer(Connection connection) {
        super(connection);
    }

    @Override
    public void doAction(Supplier<Void> supplier) throws Exception {
        Menu PlantOnRailModePlayerMenu = new Menu(connection, new PlantOnRailModePlayerCommandHandler(supplier));
        PlantOnRailModePlayerMenu.run();
    }

    @Override
    public void gameAction() {
        Random random = new Random();
        if (remainTurnToAddCard == 0) {
            if (this.getCreaturesOnHand().size() != 10) {
                remainTurnToAddCard = 1 + random.nextInt(3);
                Plant plant = Plant.getAllPlants().get(random.nextInt(Plant.getAllPlants().size()));
                this.addCreaturesOnHand(plant);
            }
        }
        else {
            remainTurnToAddCard--;
        }
    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
        return true;
    }

    @Override
    public void pickCards(Supplier<Void> supplier) throws Exception {
        supplier.get();
        //nothing
    }
}
