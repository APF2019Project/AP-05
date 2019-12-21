public class SunFlower extends Plant {
    private int numberOfSons;

    public SunFlower(String name, boolean disposable, int coolDown, int fullHp, int reloadTime, int sunCost,
                     boolean cactus, boolean peppery, int numberOfSons) {
        super(name, disposable, coolDown, fullHp, reloadTime, sunCost, cactus, peppery);
        this.numberOfSons = numberOfSons;
    }
}
