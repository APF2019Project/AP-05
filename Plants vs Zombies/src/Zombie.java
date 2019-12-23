public class Zombie extends Creature {
    private boolean swimmer, cactusHasEffect, peaHasEffect;
    private int speed;

    public Zombie(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown, int reloadTime,
                  Shield shield, boolean swimmer, boolean cactusHasEffect,
                  boolean peaHasEffect, int speed) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield);
        this.swimmer = swimmer;
        this.cactusHasEffect = cactusHasEffect;
        this.peaHasEffect = peaHasEffect;
        this.speed = speed;
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

    @Override
    public void doAction(ActiveCard activeCard,Map map) {

    }
}
