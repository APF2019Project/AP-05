package Player;

import Main.Map;
import Main.User;
import Objects.Creature;

import java.util.ArrayList;

public abstract class Player {
    private int sunInGame=50;
    private Map map;
    private int killingEnemyCount;
    protected final User user;

    public User getUser() {
        return user;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private ArrayList<Creature> creaturesOnHand = new ArrayList<>();

    public int getKillingEnemyCount() {
        return killingEnemyCount;
    }

    public void setKillingEnemyCount(int killingEnemyCount) {
        this.killingEnemyCount = killingEnemyCount;
    }

    public int getSunInGame() {
        return sunInGame;
    }

    public void setSunInGame(int sunInGame) {
        this.sunInGame = sunInGame;
    }

    public Player(User user) {
        this.user = user;
        user.setPlayer(this);
    }

    public Map getMap() {
        return map;
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

    public abstract void doAction() throws Exception;

    public abstract void gameAction();

    public abstract boolean pickCreature(Creature creature) throws Exception;

    public abstract void pickCards() throws Exception;

    public void addSun(int sun) {
        this.sunInGame += sun;
    }
}
