package Player;

import Main.Connection;
import Main.Map;
import Objects.Creature;

import java.util.ArrayList;
import java.util.function.Supplier;

public abstract class Player {
    protected final Connection connection;
    private int sunInGame = 50;
    private Map map;
    private int killingEnemyCount;
    private ArrayList<Creature> creaturesOnHand = new ArrayList<>();

    public Player(Connection connection) {
        this.connection = connection;
        connection.getUser().setPlayer(this);
    }

    public Connection getConnection() {
        return connection;
    }

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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
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
        for (Creature creature : creaturesOnHand) {
            if (creature.getName().equals(creatureName)) {
                return creature;
            }
        }
        return null;
    }

    public abstract void doAction(Supplier<Void> supplier) throws Exception;
    public abstract void gameAction();

    public abstract boolean pickCreature(Creature creature) throws Exception;

    public abstract void pickCards(Supplier<Void> supplier) throws Exception;

    public void addSun(int sun) {
        this.sunInGame += sun;
    }
}
