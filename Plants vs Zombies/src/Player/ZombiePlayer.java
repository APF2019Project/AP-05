package Player;

import Main.ActiveCard;
import Main.Connection;
import Objects.Creature;

import java.util.ArrayList;

public abstract class ZombiePlayer extends Player {
    protected ArrayList<ActiveCard> zombieCardsInThisWave = new ArrayList<>();
    protected boolean isWaveRunning;
    private int freeTurns = 10;
    public ZombiePlayer(Connection connection) {
        super(connection);
    }

    public void addZombieCardInThisWave(ActiveCard activeCard) {
        zombieCardsInThisWave.add(activeCard);
    }

    public ArrayList<ActiveCard> getZombieCardsInThisWave() {
        return zombieCardsInThisWave;
    }

    public boolean canStart() {
        return !isWaveRunning && freeTurns >= 10;
    }

    public boolean isWaveRunning() {
        return isWaveRunning;
    }

    public void startWave() throws Exception {
        getMap().startWave();
        isWaveRunning = true;
    }

    @Override
    public boolean pickCreature(Creature creature) {
        if (getSunInGame() < creature.getPrice()) {
            return false;
        }
        setSunInGame(getSunInGame() - creature.getPrice());
        return true;
    }

    public void doAction() throws Exception {
        if (zombieCardsInThisWave.isEmpty()) {
            isWaveRunning = false;
            freeTurns++;
            return;
        }
        if (isWaveRunning) {
            freeTurns = 0;
            boolean[] added = new boolean[getMap().getRow()];
            ArrayList<ActiveCard> removedZombies = new ArrayList<>();
            for (ActiveCard zombieCardInThisWave : zombieCardsInThisWave) {
                if (!added[zombieCardInThisWave.getY()]) {
                    added[zombieCardInThisWave.getY()] = true;
                    getMap().addActiveCard(zombieCardInThisWave);
                    removedZombies.add(zombieCardInThisWave);
                }
            }
            for (ActiveCard zombieCardInThisWave : removedZombies) {
                zombieCardsInThisWave.remove(zombieCardInThisWave);
            }
        }
    }
}
