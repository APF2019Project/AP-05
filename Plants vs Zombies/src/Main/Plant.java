package Main;

import java.util.ArrayList;

public abstract class Plant extends Creature {
    private static ArrayList<Plant> allPlants = new ArrayList<>();
    private boolean cactus, peppery;
    private boolean waterProof;

    public Plant(String name, boolean disposable, int coolDown, int fullHp, int price, int reloadTime, Shield shield,
                 boolean cactus, boolean peppery, boolean waterProof) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield);
        this.cactus = cactus;
        this.peppery = peppery;
        this.waterProof = waterProof;
        allPlants.add(this);
    }

    public static ArrayList<Plant> getAllPlants() {
        return allPlants;
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

    abstract public void doAction(ActiveCard activeCard, Map map);
}
