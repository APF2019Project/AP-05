package Main;

public class SunFlower extends Plant {
    private int numberOfSons;

    public SunFlower(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown,
                     int reloadTime, Shield shield, int sunCost, boolean cactus, boolean peppery, boolean waterProof, int numberOfSons) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield, cactus, peppery, waterProof);
        this.numberOfSons = numberOfSons;
    }

    public void doAction(ActiveCard activeCard, Map map) {
        activeCard.getOwner().addSun(numberOfSons);
    }
}
