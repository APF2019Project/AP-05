package game;

import java.util.HashMap;

public class Plant extends Creature {
    private static HashMap<String,Integer> remainingCoolDown;
    private static HashMap<String,Integer> coolDown;

    private int sunCost;

    private boolean cactus;
    private boolean peppery;

    public boolean isCactus() {
        return cactus;
    }

    public void setCactus(boolean cactus) {
        this.cactus = cactus;
    }

    public boolean isPeppery() {
        return peppery;
    }

    public void setPeppery(boolean peppery) {
        this.peppery = peppery;
    }

    public static void setCoolDown(String name,int coolDown){

    }
    public static void getCoolDown(String name,int coolDown){

    }
    public static void setRemainCoolDown(String name,int coolDown){

    }
    public static void getRemainCoolDown(String name,int coolDown){

    }

    public int getSunCost() {
        return sunCost;
    }

    public void setSunCost(int sunCost) {
        this.sunCost = sunCost;
    }

    public void doAction() {

    }
}
