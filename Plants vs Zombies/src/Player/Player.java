package Player;

import Main.Creature;
import Main.Map;
import Main.Menu;
import Main.User;

import java.util.ArrayList;

public abstract class Player {
    private int sunInGame;
    private Map map;
    private ArrayList<Creature> creaturesOnHand = new ArrayList<>();

    public int getSunInGame() {
        return sunInGame;
    }

    public void setSunInGame(int sunInGame) {
        this.sunInGame = sunInGame;
    }

    public Player(Map map) {
        this.map = map;
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
        for(Creature creature:creaturesOnHand){
            if(creature.getName().equals(creatureName)){
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
