package Player;

import Main.Creature;
import Main.Map;
import Main.User;

import java.util.ArrayList;

public abstract class Player {
    private int sunInGame;
    final private Map map;
    final User user;
    private ArrayList<Creature> creaturesOnHand = new ArrayList<>();

    public int getSunInGame() {
        return sunInGame;
    }

    public void setSunInGame(int sunInGame) {
        this.sunInGame = sunInGame;
    }

    public Player(Map map, User user) {
        this.map = map;
        this.user = user;
    }

    public void addCreaturesOnHand(Creature creature) {
        creaturesOnHand.add(creature);
    }

    public void removeCreaturesOnHand(Creature creature) {
        creaturesOnHand.remove(creature);
    }

    public ArrayList<Creature> getCreaturesOnHand() {
        return creaturesOnHand;
    }

    public Creature getCreatureOnHandByName(String creatureName) {
        for (Creature creature : creaturesOnHand) {
            if (creature.getName().equals(creatureName)) {
                return creature;
            }
        }
        return null;
    }

    public abstract void doAction();

    public abstract void gameAction();

    public abstract void pickCards() throws Exception;

    public void addSun(int sun) {
        this.sunInGame += sun;
    }
}
