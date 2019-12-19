package game;

public class Sunflower extends Plant {
    private int numberOfSuns;
    private int reloadTime;
    private int remainingReloadTime;
    public int getNumberOfSuns() {
        return numberOfSuns;
    }

    public void setNumberOfSuns(int numberOfSuns) {
        this.numberOfSuns = numberOfSuns;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getRemainingReloadTime() {
        return remainingReloadTime;
    }

    public void setRemainingReloadTime(int remainingReloadTime) {
        this.remainingReloadTime = remainingReloadTime;
    }

    @Override
    public void doAction() {

    }
}
