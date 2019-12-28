package Main;

public class LilyPad extends Plant {
    public LilyPad(String name, boolean disposable, int coolDown, int fullHp, int price,
                   int reloadTime, Shield shield, boolean cactus, boolean peppery, boolean waterProof) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
    }

    @Override
    public boolean doAction(ActiveCard activeCard, Map map) {
        return true;
        // noting
    }
}
