package Player;

import Command.CollectionCommandHandler;
import Command.CollectionMode;
import Command.PlantOnDayAndWaterModePlayerCommandHandler;
import Main.Connection;
import Main.Menu;
import Objects.Creature;

import java.util.function.Supplier;

public class PlantOnDayAndWaterModeHumanPlayer extends PlantPlayer {
    public PlantOnDayAndWaterModeHumanPlayer(Connection connection) {
        super(connection);
    }

    @Override
    public void doAction(Supplier<Void> supplier) throws Exception {
        System.out.println("deh8uhfq");
        Menu plantOnDayAndWaterModePlayerMenu = new Menu(connection, new PlantOnDayAndWaterModePlayerCommandHandler(supplier));
        plantOnDayAndWaterModePlayerMenu.run();
    }


    @Override
    public void gameAction() {

    }

    public boolean pickCreature(Creature creature) throws Exception {
        if (getSunInGame() < creature.getPrice()) {
            return false;
        }
        setSunInGame(getSunInGame() - creature.getPrice());
        return true;
    }

    @Override
    public void pickCards(Supplier<Void> supplier) throws Exception {
        Menu collectionMenu = new Menu(connection, new CollectionCommandHandler(CollectionMode.plantsCollection, supplier));
        collectionMenu.run();
    }
}
