package game;

import java.util.HashSet;

public class User {

    private int coin;
    private String userName;
    private String passWorld;

    private HashSet<String> unLockedPlant;
    private HashSet<String> getUnLockedZombie;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWorld() {
        return passWorld;
    }

    public void setPassWorld(String passWorld) {
        this.passWorld = passWorld;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
