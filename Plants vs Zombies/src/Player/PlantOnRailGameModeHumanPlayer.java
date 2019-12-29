package Player;

import Main.Creature;
import Main.Map;
import Main.User;

public class PlantOnRailGameModeHumanPlayer extends PlantPlayer {
    public PlantOnRailGameModeHumanPlayer(Map map, User user) {
        super(map, user);
    }

    @Override
    public void doAction() {

    }

    @Override
    public void gameAction() {

    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
        return true;
    }

    @Override
    public void pickCards() throws Exception {

    }
}
