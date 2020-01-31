package Objects;

import Main.*;
import Objects.Plant;
import Objects.Zombie;

public class Mine extends Plant {
    private int deltaX, deltaY, activeRange;
/*
    public Mine(String name, boolean disposable, int coolDown, int fullHp, int price, int reloadTime,
                Shield shield, boolean cactus, boolean peppery, boolean waterProof,
                int deltaX, int deltaY, int activeRange) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.activeRange = activeRange;
    }
*/
    public Mine(JSONHandler jsonHandler) throws Exception {
        super(jsonHandler);
        this.deltaX = jsonHandler.getInt(FieldNames.deltaX);
        this.deltaY = jsonHandler.getInt(FieldNames.deltaY);
        this.activeRange = jsonHandler.getInt(FieldNames.activeRange);
    }

    @Override
    public boolean doAction(ActiveCard activeCard, Map map) {
        if (activeCard.getDistance(map.getNearestZombie(activeCard)) <= activeRange || activeRange == -1) {
            for (ActiveCard activeCard1 : map.getActiveCardArrayList()) {
                if ((activeCard1.getCreature() instanceof Zombie) && Math.abs(activeCard.getX() - activeCard1.getX()) <= deltaX*GameData.slices
                        && Math.abs(activeCard.getY() - activeCard1.getY()) <= deltaY) {
                    activeCard1.setRemainingHp(0);
                    activeCard1.setShieldRemainingHp(0);
                }
            }
            return true;
        }
        return false;
    }
}
