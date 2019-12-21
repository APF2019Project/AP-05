public class Plant extends Creature {
    private int sunCost;
    private boolean cactus, peppery;

    public int getSunCost() {
        return sunCost;
    }

    public Plant(String name, boolean disposable, int coolDown, int fullHp, int reloadTime, int sunCost,
                 boolean cactus, boolean peppery) {
        super(name, disposable, coolDown, fullHp, reloadTime);
        this.sunCost = sunCost;
        this.cactus = cactus;
        this.peppery = peppery;
    }

    public boolean isCactus() {
        return cactus;
    }

    public boolean isPeppery() {
        return peppery;
    }
}
