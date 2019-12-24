package Main;


public class LilyPad extends Plant {
    public LilyPad(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown,
                   int reloadTime, Shield shield, int sunCost, boolean cactus, boolean peppery, boolean waterProof) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield, cactus, peppery, waterProof);
    }

    @Override
    public void doAction(ActiveCard activeCard, Map map) {
        // noting
    }
}
