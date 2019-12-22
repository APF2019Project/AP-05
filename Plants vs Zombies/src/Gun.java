public class Gun {
    private String name;
    private boolean icy, shy, sleepy, hasEffectOnShield;
    private int damage, gunShotsPerRound;

    public Gun(String name, boolean icy, boolean shy, boolean sleepy, int damage,
               int gunShotsPerRound, boolean hasEffectOnShield) {
        this.name = name;
        this.icy = icy;
        this.shy = shy;
        this.sleepy = sleepy;
        this.damage = damage;
        this.gunShotsPerRound = gunShotsPerRound;
        this.hasEffectOnShield = hasEffectOnShield;
    }

    public int getGunShotsPerRound() {
        return gunShotsPerRound;
    }

    public String getName() {
        return name;
    }

    public boolean isIcy() {
        return icy;
    }

    public boolean isShy() {
        return shy;
    }

    public boolean isSleepy() {
        return sleepy;
    }

    public int getDamage() {
        return damage;
    }

    void doAction(ActiveCard activeCard) {

    }
}
