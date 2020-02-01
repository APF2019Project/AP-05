package Objects;

import Main.ActiveCard;
import Main.FieldNames;
import Main.JSONHandler;
import Main.Map;

import java.util.ArrayList;

public class Plant extends Creature {
    private final static ArrayList<Plant> allPlants = new ArrayList<>();
    private final static ArrayList<Plant> firstPlants = new ArrayList<>();
    private boolean cactus, peppery;
    private boolean waterProof;
    public Plant(JSONHandler jsonHandler) throws Exception {
        super(jsonHandler);
        this.cactus = jsonHandler.getBoolean(FieldNames.cactus);
        this.peppery = jsonHandler.getBoolean(FieldNames.peppery);
        this.waterProof = jsonHandler.getBoolean(FieldNames.waterProof);
        allPlants.add(this);
    }

    public static ArrayList<Plant> getFirstPlants() {
        return firstPlants;
    }

    public static void addFirstPlant(Plant plant) {
        firstPlants.add(plant);
    }

    public static ArrayList<Plant> getAllPlants() {
        return allPlants;
    }

    /*
    public Plant(String name, boolean disposable, int coolDown, int fullHp, int price, int reloadTime, Shield shield,
                 boolean cactus, boolean peppery, boolean waterProof) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield);
        this.cactus = cactus;
        this.peppery = peppery;
        this.waterProof = waterProof;
        allPlants.add(this);
    }*/

    public static Plant getPlantByName(String plantName) {
        for (Plant plant : allPlants) {
            if (plant.getName().equals(plantName)) {
                return plant;
            }
        }
        return null;
    }

    @Override
    public int getPriceInShop() {
        return this.getPrice() * this.getCoolDown() * this.getFullHp() + 1;
    }

    @Override
    public int getKillingReward() {
        return 10 * getFullHp();
    }

    public boolean isMarine(ActiveCard activeCard) {
        return waterProof;
    }

    public boolean isOnshore(ActiveCard activeCard) {
        return !waterProof;
    }

    public boolean isCactus() {
        return cactus;
    }

    public boolean isPeppery() {
        return peppery;
    }

    public boolean doAction(ActiveCard activeCard, Map map) {
        return false;
    }
}
