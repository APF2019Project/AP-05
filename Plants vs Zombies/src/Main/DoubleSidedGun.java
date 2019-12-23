package Main;

import MainPackage.Main;

public class DoubleSidedGun extends Gun {
    public DoubleSidedGun(String name, boolean icy, boolean shy, boolean sleepy, int damage,
                          int gunShotsPerRound, boolean hasEffectOnShield) {
        super(name, icy, shy, sleepy, damage, gunShotsPerRound, hasEffectOnShield);
    }

    public void doAction(ActiveCard activeCard,Map map) {
        if(!this.isShy() || !this.isShyes(activeCard,map)) {
            map.addGunShot(new GunShot(this, activeCard.getX(), activeCard.getY(), GameData.speedOfGunShot, activeCard.getOwner()));
            map.addGunShot(new GunShot(this, activeCard.getX(), activeCard.getY(), -GameData.speedOfGunShot, activeCard.getOwner()));
            map.addGunShot(new GunShot(this, activeCard.getX(), activeCard.getY(), -GameData.speedOfGunShot, activeCard.getOwner()));
        }
    }
}
