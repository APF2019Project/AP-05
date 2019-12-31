package Player;

import Main.ActiveCard;
import Main.Creature;
import Main.Map;
import Main.User;

import java.util.ArrayList;
import java.util.Random;

public abstract class ZombiePlayer extends Player {
    public ZombiePlayer(User user) {
        super(user);
    }

    protected ArrayList<ActiveCard> zombieCardsInNextWave = new ArrayList<>();
    protected boolean isWaveRunning;

    public void startWave() throws Exception {
        getMap().startWave();
        isWaveRunning = true;
        for (ActiveCard activeCard : zombieCardsInNextWave) {
            getMap().addActiveCard(activeCard);
        }
    }

    public void doAction() throws Exception {
        if(zombieCardsInNextWave.isEmpty()){
            isWaveRunning=false;
            return;
        }
        if (isWaveRunning) {
            getMap().addActiveCard(zombieCardsInNextWave.get(0));
            zombieCardsInNextWave.remove(0);
        }
    }
}
