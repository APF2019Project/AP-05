package Main;

import java.util.ArrayList;

public class Zombie extends Creature {
    private boolean swimmer, cactusHasEffect, peaHasEffect;
    private int speed;
    private static ArrayList<Zombie> allZombies=new ArrayList<>();

    public Zombie(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown, int reloadTime,
                  Shield shield, boolean swimmer, boolean cactusHasEffect,
                  boolean peaHasEffect, int speed) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield);
        this.swimmer = swimmer;
        this.cactusHasEffect = cactusHasEffect;
        this.peaHasEffect = peaHasEffect;
        this.speed = speed;
    }
    public int getDamage(boolean hasShield){
        // to_do
        return 1;
    }
    public boolean isSwimmer() {
        return swimmer;
    }

    public boolean isCactusHasEffect() {
        return cactusHasEffect;
    }

    public boolean isPeaHasEffect() {
        return peaHasEffect;
    }

    public static ArrayList<Zombie> getAllZombies() {
        return allZombies;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isWinning(ActiveCard activeCard){
        return (activeCard.getX()<0);
    }
    public void eatPlant(ActiveCard activeCard,Map map){
        ActiveCard eatedPlant=map.findPlantIn(activeCard.getX(),activeCard.getY());
        if(eatedPlant!=null){
            eatedPlant.setRemainingHp(eatedPlant.getRemainingHp()-getDamage(activeCard.getShieldRemainingHp()>0));
        }
    }

    public void doAction(ActiveCard activeCard,Map map) {
        int finalX=Math.max(map.hasNoPlantIn(activeCard.getY(),activeCard.getX()),activeCard.getX()-speed);
        activeCard.setX(finalX);
    }
}
