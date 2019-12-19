package game;

public class Player {
    private Deck ChosenDeckInGame;
    private int killingEnemy;
    private int SunInGame;
    private boolean zombie;

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
}
