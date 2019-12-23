package Main;

public class Shooter extends Plant {
    private Gun gun;

    public Shooter(String name, boolean disposable, int coolDown, int fullHp,
                   int remainingCoolDown, int reloadTime, Shield shield, int sunCost, boolean cactus, boolean peppery, boolean waterProof, Gun gun) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield, sunCost, cactus, peppery, waterProof);
        this.gun = gun;
    }

    public Gun getGun() {
        return gun;
    }

    @Override
    public void doAction(ActiveCard activeCard,Map map) {
            gun.doAction(activeCard,map);
    }
}