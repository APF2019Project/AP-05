package game;

public class Gun {
    int id;
    private int damage;
    private int reloadTime;
    private int remainingReloadTime;


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    public void doAction() {

    }
}
