package Main;

public class Plant extends Creature {
    private int sunCost;
    private boolean cactus, peppery;
    private boolean waterProof;
    public int getSunCost() {
        return sunCost;
    }

    public Plant(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown,
                 int reloadTime, Shield shield, int sunCost, boolean cactus, boolean peppery, boolean waterProof) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield);
        this.sunCost = sunCost;
        this.cactus = cactus;
        this.peppery = peppery;
        this.waterProof = waterProof;
    }

    public boolean isWaterProof() {
        return waterProof;
    }
    public boolean isCactus() {
        return cactus;
    }

    public boolean isPeppery() {
        return peppery;
    }

    @Override
    public void doAction(ActiveCard activeCard,Map map){

    }
}
