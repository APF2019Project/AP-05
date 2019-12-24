package Main;

public class Creature {
    private String name;
    private boolean disposable;
    private int coolDown;
    private int fullHp;
    private int remainingCoolDown;
    private int reloadTime;
    private int price;
    private Shield shield;
    public Creature(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown, int reloadTime, Shield shield) {
        this.name = name;
        this.disposable = disposable;
        this.coolDown = coolDown;
        this.fullHp = fullHp;
        this.remainingCoolDown = remainingCoolDown;
        this.reloadTime = reloadTime;
        this.shield = shield;
    }

    public int getPrice() {
        return price;
    }

    public void doAction(ActiveCard activeCard, Map map) {

    }

    public int getReloadTime() {
        return reloadTime;
    }

    public Shield getShield() {
        return shield;
    }

    public String getName() {
        return name;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public int getFullHp() {
        return fullHp;
    }

    public int getRemainingCoolDown() {
        return remainingCoolDown;
    }

    public void setRemainingCoolDown(int remainingCoolDown) {
        this.remainingCoolDown = remainingCoolDown;
    }
}
