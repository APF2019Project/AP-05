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

    private ArrayList<ActiveCard> zombieCardsInNextWave = new ArrayList<>();
    public void startWave() throws Exception {
        for(ActiveCard activeCard:zombieCardsInNextWave) {
            getMap().addActiveCard(activeCard);
        }
    }
}
