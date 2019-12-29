package Main;

public class SunFlower extends Plant {
    private int numberOfSons;

    public SunFlower(String name, boolean disposable, int coolDown, int fullHp, int price, int reloadTime,
                     Shield shield, boolean cactus, boolean peppery, boolean waterProof, int numberOfSons) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
        this.numberOfSons = numberOfSons;
    }

    public boolean doAction(ActiveCard activeCard, Map map) {
        activeCard.getOwner().addSun(numberOfSons);
        return false;
    }
}
