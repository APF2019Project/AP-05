package Main;

public class ThreeRowGun extends Gun {
    public ThreeRowGun(String name, boolean icy, boolean shy, boolean sleepy, int damage,
                       int gunShotsPerRound, boolean hasEffectOnShield) {
        super(name, icy, shy, sleepy, damage, gunShotsPerRound, hasEffectOnShield);
    }

    public void doAction(ActiveCard activeCard,Map map) {
        if(!this.isShy() || !this.isShyes(activeCard,map)) {
            map.addGunShot(new GunShot(this, activeCard.getX(), activeCard.getY(), GameData.speedOfGunShot, activeCard.getOwner()));
            map.addGunShot(new GunShot(this, activeCard.getX(), activeCard.getY() - 1, GameData.speedOfGunShot, activeCard.getOwner()));
            map.addGunShot(new GunShot(this, activeCard.getX(), activeCard.getY() + 1, GameData.speedOfGunShot, activeCard.getOwner()));
        }
    }
}
