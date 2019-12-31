package Player;

import Main.*;

import java.util.ArrayList;
import java.util.Random;

public abstract class ZombiePlayer extends Player {
    public ZombiePlayer(User user) {
        super(user);
    }

    protected ArrayList<ActiveCard> zombieCardsInThisWave = new ArrayList<>();
    protected boolean isWaveRunning;

    public void startWave() throws Exception {
        getMap().startWave();
        isWaveRunning = true;
        for (ActiveCard activeCard : zombieCardsInThisWave) {
            getMap().addActiveCard(activeCard);
        }
    }

    public void doAction() throws Exception {
        if(zombieCardsInThisWave.isEmpty()){
            isWaveRunning=false;
            return;
        }
        if (isWaveRunning) {
            boolean[] added =new boolean[getMap().getRow()];
            for(ActiveCard zombieCardInThisWave:zombieCardsInThisWave) {
                if(!added[zombieCardInThisWave.getY()]){
                    added[zombieCardInThisWave.getY()]=true;
                    getMap().addActiveCard(zombieCardInThisWave);
                    zombieCardsInThisWave.remove(zombieCardInThisWave);
                }
            }
        }
    }
}
