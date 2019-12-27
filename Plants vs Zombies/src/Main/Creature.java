package Main;

import java.util.ArrayList;

public abstract class Creature {
    private static ArrayList<Creature> allCreatures = new ArrayList<Creature>();

    private String name;
    private boolean disposable;
    private int coolDown;
    private int fullHp;
    private int remainingCoolDown = 0;
    private int reloadTime;
    private int price;
    private Shield shield;

    public Creature(String name, boolean disposable, int coolDown, int fullHp, int reloadTime,Shield shield) {
        this.name = name;
        this.disposable = disposable;
        this.coolDown = coolDown;
        this.fullHp = fullHp;
        this.reloadTime = reloadTime;
        this.shield = shield;
        allCreatures.add(this);
    }

    static public Creature getCreatureByName(String name) {
        for (Creature creature : allCreatures) {
            if (creature.getName().equals(name)) {
                return creature;
            }
        }
        return null;
    }

    static ArrayList<Creature> getAllCreatures() {
        return allCreatures;
    }

    public int getPrice() {
        return price;
    }

    abstract public boolean doAction(ActiveCard activeCard, Map map);

    public int getReloadTime() {
        return reloadTime;
    }

    public Shield getShield() {
        return shield;
    }

    public String getName() {
        return name;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public int getFullHp() {
        return fullHp;
    }

    public int getRemainingCoolDown() {
        return remainingCoolDown;
    }

    public void setRemainingCoolDown(int remainingCoolDown) {
        this.remainingCoolDown = remainingCoolDown;
    }
}
