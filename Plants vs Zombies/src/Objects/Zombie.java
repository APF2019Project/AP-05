package Objects;

import Main.*;

import java.util.ArrayList;


public class Zombie extends Creature {
    private final static ArrayList<Zombie> firstZombies = new ArrayList<>();
    private static ArrayList<Zombie> allZombies = new ArrayList<>();
    private boolean swimmer, cactusHasEffect, peaHasEffect;
    private int speed, power, powerWithShield;


    public Zombie(JSONHandler jsonHandler) throws Exception {
        super(jsonHandler);
        this.swimmer = jsonHandler.getBoolean(FieldNames.swimmer);
        this.cactusHasEffect = jsonHandler.getBoolean(FieldNames.cactusHasEffect);
        this.peaHasEffect = jsonHandler.getBoolean(FieldNames.peaHasEffect);
        this.speed = jsonHandler.getInt(FieldNames.speed);
        this.power = jsonHandler.getInt(FieldNames.power);
        this.powerWithShield = jsonHandler.getInt(FieldNames.powerWithShield);
        System.out.println(this.getName());
        allZombies.add(this);
    }

    /*
        public Zombie(String name, boolean disposable, int coolDown, int fullHp, int reloadTime,
                      Shield shield, boolean swimmer, boolean cactusHasEffect,
                      boolean peaHasEffect, boolean hasLadder, int speed, int power, int powerWithShield) {
            super(name, disposable, coolDown, fullHp, fullHp * 10, reloadTime, shield);
            this.swimmer = swimmer;
            this.cactusHasEffect = cactusHasEffect;
            this.peaHasEffect = peaHasEffect;
            this.hasLadder = hasLadder;
            this.speed = speed;
            this.power = power;
            this.powerWithShield = powerWithShield;
        }
    */

    public static ArrayList<Zombie> getFirstZombies() {
        return firstZombies;
    }

    public static void addFirstZombie(Zombie zombie) {
        firstZombies.add(zombie);
    }

    public static ArrayList<Zombie> getAllZombies() {
        return allZombies;
    }

    public static Zombie getZombieByName(String plantName) {
        for (Zombie zombie : allZombies) {
            if (zombie.getName().equals(plantName)) {
                return zombie;
            }
        }
        return null;
    }

    public int getPower(boolean hasShield) {
        if (hasShield) return powerWithShield;
        else return power;
    }

    @Override
    public int getKillingReward() {
        return 0;
    }

    @Override
    public int getPriceInShop() {
        return (1 + this.getSpeed()) * this.getFullHp() * 10;
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

    public int getSpeed() {
        return speed;
    }

    public boolean isWinning(ActiveCard activeCard) {
        return (activeCard.getX() < 0);
    }

    public boolean eatPlant(ActiveCard activeCard, Map map) throws Exception {
        ActiveCard eatenPlant = map.findPlantIn(activeCard.getX(), activeCard.getY());
        if (eatenPlant != null && !eatenPlant.isHasLadder()) {
            if (activeCard.isHasLadder()) {
                activeCard.setHasLadder(false);
                eatenPlant.setHasLadder(true);
                return false;
            }
            eatenPlant.damaged(getPower(activeCard.getShieldRemainingHp() > 0));
            if (((Plant) eatenPlant.getCreature()).isCactus() && ((Zombie) activeCard.getCreature()).cactusHasEffect) {
                activeCard.damaged(GameData.cactusDamage);
            }
            if (((Plant) eatenPlant.getCreature()).isPeppery()) {
                activeCard.damaged(GameData.PepperDamage);
            }
            if (eatenPlant.getRemainingHp() == 0) {
                activeCard.getOwner().addSun(eatenPlant.getCreature().getKillingReward());
            }
            return true;
        }
        return false;
    }

    public boolean isMarine(ActiveCard activeCard) {
        return (swimmer || activeCard.isHasOrdak());
    }

    public boolean isOnshore(ActiveCard activeCard) {
        return (!swimmer);
    }

    public boolean doAction(ActiveCard activeCard, Map map) throws Exception {
        int deltaX = speed;
        if (activeCard.getRemainingSlowDown() > 0) {
            deltaX *= (100 - activeCard.getSlowDownPercent()) / 100.0;
        }
        int finalX = Math.max(map.hasNoPlantIn(activeCard.getY(), activeCard.getX()), activeCard.getX() - deltaX);
        ArrayList<GunShot> collisionGUnShot = map.getGunShotIn(activeCard.getY(), finalX, activeCard.getX());
        for (GunShot gunShot : collisionGUnShot) {
            gunShot.collision(activeCard);
        }
        activeCard.setX(finalX);
        return eatPlant(activeCard, map);
    }
}
