package Main;

public class Shooter extends Plant {
    private Gun gun;

    public Shooter(String name, boolean disposable, int coolDown, int fullHp, int price, int reloadTime,
                   Shield shield, boolean cactus, boolean peppery, boolean waterProof, Gun gun) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
        this.gun = gun;
    }

    public Gun getGun() {
        return gun;
    }

    @Override
    public void doAction(ActiveCard activeCard, Map map) {
        return gun.doAction(activeCard, map);
    }
}
