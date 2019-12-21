public class Creature {
    private String name;
    private boolean disposable;
    private int coolDown, fullHp, remainingCoolDown, reloadTime;

    public Creature(String name, boolean disposable, int coolDown, int fullHp, int reloadTime) {
        this.name = name;
        this.disposable = disposable;
        this.coolDown = coolDown;
        this.fullHp = fullHp;
        this.remainingCoolDown = 0;
        this.reloadTime = reloadTime;
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
