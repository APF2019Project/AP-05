package Player;

import Command.CollectionCommandHandler;
import Main.Main;
import Main.Menu;
import Main.Map;
import Main.User;
import Main.Creature;

public class PlantOnDayAndWaterModeHumanPlayer extends PlantPlayer {
    public PlantOnDayAndWaterModeHumanPlayer(Map map, User user) {
        super(map, user);
    }

    @Override
    public void doAction() {

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
        Menu collectionMenu = new Menu(user, new CollectionCommandHandler());
        collectionMenu.run();
    }
}
