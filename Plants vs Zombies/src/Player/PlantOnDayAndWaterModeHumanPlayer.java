package Player;

import Command.CollectionCommandHandler;
import Command.PlantOnDayAndWaterModePlayerCommandHandler;
import Command.CollectionMode;
import Main.Connection;
import Main.Menu;
import Main.User;
import Objects.Creature;

import java.beans.EventHandler;
import java.util.function.Supplier;

public class PlantOnDayAndWaterModeHumanPlayer extends PlantPlayer {
    public PlantOnDayAndWaterModeHumanPlayer(Connection connection) {
        super(connection);
    }

    @Override
    public void doAction() throws Exception {
        Menu plantOnDayAndWaterModePlayerMenu = new Menu(connection, new PlantOnDayAndWaterModePlayerCommandHandler());
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
        Menu collectionMenu = new Menu(connection, new CollectionCommandHandler(CollectionMode.plantsCollection,supplier));
        collectionMenu.run();
    }
}
