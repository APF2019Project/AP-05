public class LilyPad extends Plant {
    public LilyPad(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown, int reloadTime,
                   Shield shield, int sunCost, boolean cactus, boolean peppery) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield, sunCost, cactus, peppery);
    }

    @Override
    public void doAction(ActiveCard activeCard) {
        // noting
    }
}
