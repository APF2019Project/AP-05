package Objects;

import Main.ActiveCard;
import Main.JSONHandler;
import Main.Map;

public class LilyPad extends Plant {
    /*
    public LilyPad(String name, boolean disposable, int coolDown, int fullHp, int price,
                   int reloadTime, Shield shield, boolean cactus, boolean peppery, boolean waterProof) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
    }
    */

    public LilyPad(JSONHandler jsonHandler) throws Exception {
        super(jsonHandler);
    }

    @Override
    public boolean doAction(ActiveCard activeCard, Map map) {
        return true;
        // noting
    }
}
