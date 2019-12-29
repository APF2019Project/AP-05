package Player;

import Main.Creature;
import Main.Map;
import Main.User;

public class ZombieHumanPlayer extends ZombiePlayer {
    public ZombieHumanPlayer(Map map, User user) {
        super(map, user);
    }

    @Override
    public void doAction() throws Exception {
        super.doAction();

    }

    @Override
    public void gameAction() {

    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
        if (getSunInGame() < creature.getPrice()) {
            return false;
        }
        setSunInGame(getSunInGame() - creature.getPrice());
        return true;
    }

    @Override
    public void pickCards() throws Exception {

    }
}
