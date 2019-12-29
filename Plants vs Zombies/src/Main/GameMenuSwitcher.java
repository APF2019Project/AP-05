package Main;

import Player.PlantPlayer;
import Player.ZombiePlayer;
import Player.Player;

public class GameMenuSwitcher {
    private Map map;
    private GameStatus gameStatus=GameStatus.OnGame;
    private int numberOfRemainedWaves=-1;

    public GameMenuSwitcher(Map map){
        this.map=map;
    }
    public GameMenuSwitcher(Map map,int numberOfWaves){
        this.map=map;
        this.numberOfRemainedWaves=numberOfWaves;
    }

    public void runGame() throws Exception {
        map.getPlantPlayer().pickCards();
        map.getZombiePlayer().pickCards();

        // this part is incomplete
        while(gameStatus.equals(GameStatus.OnGame)){
            map.getPlantPlayer().doAction();
            map.getZombiePlayer().doAction();
            gameStatus=map.run();
            map.getPlantPlayer().gameAction();
            map.getZombiePlayer().gameAction();
        }
    }
}
