package Main;

import Player.Player;

public class GameMenuSwitcher {
    public Player plantPlayer, zombiePlayer;
    public boolean onGame=true;

    public GameMenuSwitcher(PlantPlayer plantPlayer, ZombiePlayer zombiePlayer){
        this.plantPlayer=plantPlayer;
        this.zombiePlayer=zombiePlayer;
    }

    void runGame(){
        plantPlayer.pickCards();
        zombiePlayer.pickCards();

        // this part is incomplete
        while(onGame){
            plantPlayer.doAction();
            zombiePlayer.doAction();
        }
    }
}
