package Player;

import Main.ActiveCard;
import Main.Creature;
import Main.Map;
import Main.User;

import java.util.ArrayList;
import java.util.Random;

public abstract class ZombiePlayer extends Player {
    public ZombiePlayer(Map map, User user) {
        super(map, user);
    }

    protected ArrayList<ActiveCard> zombieCardsInNextWave = new ArrayList<>();
    protected boolean isWaveRunning;

    public void startWave() throws Exception {
        isWaveRunning = true;
        for (ActiveCard activeCard : zombieCardsInNextWave) {
            getMap().addActiveCard(activeCard);
        }
    }

    public void doAction() throws Exception {
        if (isWaveRunning && !zombieCardsInNextWave.isEmpty()) {
            getMap().addActiveCard(zombieCardsInNextWave.get(0));
            zombieCardsInNextWave.remove(0);
        }
    }
}
