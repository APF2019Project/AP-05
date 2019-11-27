package game;

public class Gun {
    private String name;
    private int damage;
    private int reloadTime;
    private int gunshotsPerRound;
    private boolean icy;
    private boolean sleepy;
    private boolean projectile;

    public boolean isProjectile() {
        return projectile;
    }

    public void setProjectile(boolean projectile) {
        this.projectile = projectile;
    }

    public boolean isIcy() {
        return icy;
    }

    public void setIcy(boolean icy) {
        this.icy = icy;
    }

    public boolean isSleepy() {
        return sleepy;
    }

    public void setSleepy(boolean sleepy) {
        this.sleepy = sleepy;
    }

    public boolean isScaredy() {
        return scaredy;
    }

    public void setScaredy(boolean scaredy) {
        this.scaredy = scaredy;
    }

    private boolean scaredy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGunshotsPerRound() {
        return gunshotsPerRound;
    }

    public void setGunshotsPerRound(int gunshotsPerRound) {
        this.gunshotsPerRound = gunshotsPerRound;
    }

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

    public void shoot() {

    }

    public void doAction() {

    }
}
