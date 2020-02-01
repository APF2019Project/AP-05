package Objects;

import Main.ActiveCard;
import Main.GameData;
import Main.JSONHandler;
import Main.Map;

public class MagnetShroom extends Plant {
    /*
    public MagnetShroom(String name, boolean disposable, int coolDown, int fullHp, int price,
                        int reloadTime, Shield shield, boolean cactus, boolean peppery, boolean waterProof) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
    }
    */

    public MagnetShroom(JSONHandler jsonHandler) throws Exception {
        super(jsonHandler);
    }

    public boolean doAction(ActiveCard magnet, Map map) {
        for (ActiveCard zombie : map.getActiveCardArrayList()) {
            if (zombie.getCreature() instanceof Zombie) {
                if (magnet.getDistance(zombie) <= GameData.maxMagnetRange &&
                        zombie.getShieldRemainingHp() > 0 && zombie.getCreature().getShield().isMetal()) {
                    magnet.setRemainReloadTime(GameData.MagnetEatingTime);
                    zombie.setShieldRemainingHp(0);
                }
            }
        }
        return false;
    }
}

