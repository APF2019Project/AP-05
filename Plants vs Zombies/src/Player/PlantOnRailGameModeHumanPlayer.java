package Player;

import Main.Creature;
import Main.Map;
import Main.User;

public class PlantOnRailGameModeHumanPlayer extends PlantPlayer {
    public PlantOnRailGameModeHumanPlayer(User user) {
        super(user);
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
