package game;

import java.util.HashSet;

public class User {
    private int SunInGame;
    private boolean zombie;
    private int coin;
    private HashSet<String> unLockedPlant;
    private HashSet<String> getUnLockedZombie;
    private Deck ChosenDeckInGame;
    private int killingEnemy;

    public Deck getChosenDeckInGame() {
        return ChosenDeckInGame;
    }

    public void setChosenDeckInGame(Deck chosenDeckInGame) {
        ChosenDeckInGame = chosenDeckInGame;
    }

    public int getKillingEnemy() {
        return killingEnemy;
    }

    public void setKillingEnemy(int killingEnemy) {
        this.killingEnemy = killingEnemy;
    }


    public HashSet<String> getUnLockedPlant() {
        return unLockedPlant;
    }

    public void setUnLockedPlant(HashSet<String> unLockedPlant) {
        this.unLockedPlant = unLockedPlant;
    }

    public HashSet<String> getGetUnLockedZombie() {
        return getUnLockedZombie;
    }

    public void setGetUnLockedZombie(HashSet<String> getUnLockedZombie) {
        this.getUnLockedZombie = getUnLockedZombie;
    }

    public void StartGame(boolean zombieMode){

    }
    public int getSunInGame() {
        return SunInGame;
    }

    public void setSunInGame(int sunInGame) {
        SunInGame = sunInGame;
    }

    public boolean isZombie() {
        return zombie;
    }

    public void setZombie(boolean zombie) {
        this.zombie = zombie;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
