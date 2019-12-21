public class Shooter extends Plant {
    private Gun gun;

    public Shooter(String name, boolean disposable, int coolDown, int fullHp, int reloadTime, int sunCost,
                   boolean cactus, boolean peppery, Gun gun) {
        super(name, disposable, coolDown, fullHp, reloadTime, sunCost, cactus, peppery);
        this.gun = gun;
    }

    public Gun getGun() {
        return gun;
    }
}
