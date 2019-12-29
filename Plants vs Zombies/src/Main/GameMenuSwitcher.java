package Main;

import Player.PlantPlayer;
import Player.ZombiePlayer;
import Player.Player;

public class GameMenuSwitcher {
    private Player plantPlayer, zombiePlayer;
    private Map map;
    private GameStatus gameStatus=GameStatus.OnGame;
    private int numberOfRemainedWaves=-1;

    public GameMenuSwitcher(PlantPlayer plantPlayer, ZombiePlayer zombiePlayer,Map map){
        this.plantPlayer=plantPlayer;
        this.zombiePlayer=zombiePlayer;
        this.map=map;
    }
    public GameMenuSwitcher(PlantPlayer plantPlayer, ZombiePlayer zombiePlayer,Map map,int numberOfWaves){
        this.plantPlayer=plantPlayer;
        this.zombiePlayer=zombiePlayer;
        this.map=map;
        this.numberOfRemainedWaves=numberOfWaves;
    }

    public void runGame() throws Exception {
        plantPlayer.pickCards();
        zombiePlayer.pickCards();

        // this part is incomplete
        while(gameStatus.equals(GameStatus.OnGame)){
            plantPlayer.doAction();
            zombiePlayer.doAction();
            gameStatus=map.run();
        }
    }
}
