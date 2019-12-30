package Main;

import java.util.ArrayList;

public class Plant extends Creature {
    private static ArrayList<Plant> allPlants = new ArrayList<>();
    private boolean cactus, peppery;
    private boolean waterProof;

    @Override
    public int getPriceInShop() {
        return this.getPrice()* this.getCoolDown() * this.getFullHp() + 1;
    }
    @Override
    public int getKillingReward(){
        return 10*getFullHp();
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

    public Plant(JSONHandler jsonHandler) throws Exception{
        super(jsonHandler);
        this.cactus = jsonHandler.getBoolean(FieldNames.cactus);
        this.peppery = jsonHandler.getBoolean(FieldNames.peppery);
        this.waterProof = jsonHandler.getBoolean(FieldNames.waterProof);
        allPlants.add(this);
    }

    public static ArrayList<Plant> getAllPlants() {
        return allPlants;
    }

    public static Plant getPlantByName(String plantName){
        for(Plant plant:allPlants){
            if(plant.getName().equals(plantName)){
                return plant;
            }
        }
        return null;
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

    public boolean doAction(ActiveCard activeCard, Map map) {
        return false;
    }
}
