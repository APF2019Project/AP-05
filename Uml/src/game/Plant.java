package game;

public class Plant extends Creature {
    private int coolDown;
    private int remainingCoolDown;
    private int sunCost;

    private boolean cactus;
    private boolean peppery;

    public boolean isCactus() {
        return cactus;
    }

    public void setCactus(boolean cactus) {
        this.cactus = cactus;
    }

    public boolean isPeppery() {
        return peppery;
    }

    public void setPeppery(boolean peppery) {
        this.peppery = peppery;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }


    public int getRemainingCoolDown() {
        return remainingCoolDown;
    }

    public void setRemainingCoolDown(int remainingCoolDown) {
        this.remainingCoolDown = remainingCoolDown;
    }

    public int getSunCost() {
        return sunCost;
    }

    public void setSunCost(int sunCost) {
        this.sunCost = sunCost;
    }

    public void doAction() {

    }
}
