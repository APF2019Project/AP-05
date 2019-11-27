package game;

public class Gunshot {
    private String name;
    private int x , y;
    private int damage;
    private boolean hasEffectOnShield;

    private boolean icy;
    private boolean sleepy;
    private boolean projectile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isHasEffectOnShield() {
        return hasEffectOnShield;
    }

    public void setHasEffectOnShield(boolean hasEffectOnShield) {
        this.hasEffectOnShield = hasEffectOnShield;
    }

    public void move(){

    }
    public void collision(Zombie zombie){

    }

}
