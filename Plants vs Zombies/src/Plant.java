public class Plant extends Creature {
    private int sunCost;
    private boolean cactus, peppery;

    public int getSunCost() {
        return sunCost;
    }

    public Plant(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown, int reloadTime,
                 Shield shield, int sunCost, boolean cactus, boolean peppery) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield);
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
