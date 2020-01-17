package Player;

import Command.CollectionCommandHandler;
import Command.PlantOnDayAndWaterModePlayerCommandHandler;
import Main.CollectionMode;
import Main.Menu;
import Main.User;
import Objects.Creature;

public class PlantOnDayAndWaterModeHumanPlayer extends PlantPlayer {
    public PlantOnDayAndWaterModeHumanPlayer(User user) {
        super(user);
    }

    @Override
    public void doAction() throws Exception {
        Menu plantOnDayAndWaterModePlayerMenu = new Menu(user, new PlantOnDayAndWaterModePlayerCommandHandler());
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
    public void pickCards() throws Exception {
        Menu collectionMenu = new Menu(user, new CollectionCommandHandler(CollectionMode.plantsCollection));
        collectionMenu.run();
    }
}
