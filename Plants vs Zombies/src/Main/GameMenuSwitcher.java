package Main;

import Player.PlantPlayer;
import Player.ZombiePlayer;
import Player.Player;

public class GameMenuSwitcher {
    private Player plantPlayer, zombiePlayer;
    private boolean onGame=true;
    private int numberOfRemainedWaves=-1;

    public GameMenuSwitcher(PlantPlayer plantPlayer, ZombiePlayer zombiePlayer){
        this.plantPlayer=plantPlayer;
        this.zombiePlayer=zombiePlayer;
    }
    public GameMenuSwitcher(PlantPlayer plantPlayer, ZombiePlayer zombiePlayer,int numberOfWaves){
        this.plantPlayer=plantPlayer;
        this.zombiePlayer=zombiePlayer;
        this.numberOfRemainedWaves=numberOfWaves;
    }

    public void runGame() throws Exception {
        plantPlayer.pickCards();
        zombiePlayer.pickCards();

        // this part is incomplete
        while(onGame){
            plantPlayer.doAction();
            zombiePlayer.doAction();
        }
    }
}
