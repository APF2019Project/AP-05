package game;

public class Zombie extends Creature {
    private int speed;
    private int remainingTurnsOfSlowdown;
    private boolean getEffectsOCactus;
    private Shield shied;
    private boolean inAir;
    private boolean haveShield;
    private boolean seaZombie;


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRemainingTurnsOfSlowdown() {
        return remainingTurnsOfSlowdown;
    }

    public void setRemainingTurnsOfSlowdown(int remainingTurnsOfSlowdown) {
        this.remainingTurnsOfSlowdown = remainingTurnsOfSlowdown;
    }

    public boolean isGetEffectsOCactus() {
        return getEffectsOCactus;
    }

    public void setGetEffectsOCactus(boolean getEffectsOCactus) {
        this.getEffectsOCactus = getEffectsOCactus;
    }

    public Shield getShied() {
        return shied;
    }

    public void setShied(Shield shied) {
        this.shied = shied;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public boolean isHaveShield() {
        return haveShield;
    }

    public void setHaveShield(boolean haveShield) {
        this.haveShield = haveShield;
    }

    public boolean isSeaZombie() {
        return seaZombie;
    }

    public void setSeaZombie(boolean seaZombie) {
        this.seaZombie = seaZombie;
    }


    public  void attack(Plant plant){

    }
    public void lostShield(){

    }
    public void move(int x , int y) {

    }
    public void slowDown(int turns, int speed) {

    }


}
