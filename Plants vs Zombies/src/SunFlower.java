public class SunFlower extends Plant {
    private int numberOfSons;

    public SunFlower(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown, int reloadTime,
                     Shield shield, int sunCost, boolean cactus, boolean peppery, int numberOfSons) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield, sunCost, cactus, peppery);
        this.numberOfSons = numberOfSons;
    }
    public int getNumberOfSons() {
        return numberOfSons;
    }

    public void doAction(ActiveCard activeCard) {

    }
}
