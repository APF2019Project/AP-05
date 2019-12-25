package Player;

import Main.User;
import Player.Player;

public class ZombiePlayer extends Player {

    public ZombiePlayer(User user) {
        super(user);
    }

    void doAction() {
        super.setMeCurrentPlayer();
    }

    @Override
    void pickCards() {
        super.setMeCurrentPlayer();
    }
}
