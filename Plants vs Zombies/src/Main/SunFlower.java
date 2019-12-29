package Main;

public class SunFlower extends Plant {
    private int numberOfSuns;

    public SunFlower(String name, boolean disposable, int coolDown, int fullHp, int price, int reloadTime,
                     Shield shield, boolean cactus, boolean peppery, boolean waterProof, int numberOfSuns) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
        this.numberOfSuns = numberOfSuns;
    }

    public boolean doAction(ActiveCard activeCard, Map map) {
        activeCard.getOwner().addSun(numberOfSuns);
        return false;
    }
}
