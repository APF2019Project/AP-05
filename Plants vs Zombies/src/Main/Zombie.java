package Main;

import java.util.ArrayList;

public class Zombie extends Creature {
    private boolean swimmer, cactusHasEffect, peaHasEffect, hasLadder;
    private int speed, power, powerWithShield;
    private static ArrayList<Zombie> allZombies = new ArrayList<>();

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

    public boolean isHasLadder() {
        Shield shield;

        return hasLadder;
    }

    public static ArrayList<Zombie> getAllZombies() {
        return allZombies;
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

    public boolean isSwimmerWithActiveCard(ActiveCard activeCard) {
        return (swimmer || activeCard.isHasOrdak());
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
            if (isHasLadder()) {
                hasLadder = false;
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

    public boolean doAction(ActiveCard activeCard, Map map) throws Exception {
        int deltaX = speed;
        if (activeCard.getRemainingSlowDown() > 0) {
            deltaX *= (100 - activeCard.getSlowDownPercent()) / 100.0;
        }
        int finalX = Math.max(map.hasNoPlantIn(activeCard.getY(), activeCard.getX()), activeCard.getX() - deltaX);
        while (activeCard.getRemainingHp() > 0) {
            GunShot gunShot = map.getGunShotIn(activeCard.getY(), finalX, activeCard.getX());
            if (gunShot == null) break;
            gunShot.collision(activeCard);
        }
        activeCard.setX(finalX);
        eatPlant(activeCard, map);
        return true;
    }
}
