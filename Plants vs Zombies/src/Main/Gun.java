package Main;

public class Gun {
    private String name;
    private boolean icy, shy, sleepy, projectile;
    private int damage, gunShotsPerRound;

    public Gun(String name, boolean icy, boolean shy, boolean sleepy, int damage,
               int gunShotsPerRound, boolean hasEffectOnShield) {
        this.name = name;
        this.icy = icy;
        this.shy = shy;
        this.sleepy = sleepy;
        this.damage = damage;
        this.gunShotsPerRound = gunShotsPerRound;
        this.projectile = hasEffectOnShield;
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

    public boolean isProjectile() {
        return projectile;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isShyes(ActiveCard activeCard, Map map) {
        for (ActiveCard activeCard1 : map.activeCardArrayList) {
            if (activeCard1.getX() == activeCard.getX() &&
                    activeCard.getDistance(activeCard1) <= GameData.shyDistanceLimit) {
                return true;
            }
        }
        return false;
    }

    boolean doAction(ActiveCard activeCard, Map map) {
        if (!this.isShy() || !this.isShyes(activeCard, map)) {
            for (int i = 0; i < gunShotsPerRound; i++) {
                map.addGunShot(new GunShot(this, activeCard.getX(), activeCard.getY()
                        , GameData.speedOfGunShot, activeCard.getOwner()));
            }
            return true;
        }
        return false;
    }
}
