package game;

public class Battle {
    private Player zombieLeader;
    private Player plantLeader;
    private Map map;

    public void moveTime(){

    }
    public Player getZombieLeader() {
        return zombieLeader;
    }

    public void setZombieLeader(Player zombieLeader) {
        this.zombieLeader = zombieLeader;
    }

    public Player getPlantLeader() {
        return plantLeader;
    }

    public void setPlantLeader(Player plantLeader) {
        this.plantLeader = plantLeader;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
