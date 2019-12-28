package Main;

public class Mine extends Plant {
    private int deltaX, deltaY,activeRange;

    public Mine(String name, boolean disposable, int coolDown, int fullHp, int price, int reloadTime,
                Shield shield, boolean cactus, boolean peppery, boolean waterProof,
                int deltaX, int deltaY, int activeRange) {
        super(name, disposable, coolDown, fullHp, price, reloadTime, shield, cactus, peppery, waterProof);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.activeRange = activeRange;
    }

    @Override
    public boolean doAction(ActiveCard activeCard, Map map) {
        if(activeCard.getDistance(map.getNearestZombie(activeCard))<=activeRange) {
            for (ActiveCard activeCard1 : map.activeCardArrayList) {
                if ((activeCard.getCreature() instanceof Zombie) && Math.abs(activeCard.getX() - activeCard1.getX()) <= deltaX
                        && Math.abs(activeCard.getY() - activeCard1.getY()) <= deltaY) {
                    activeCard1.setRemainingHp(0);
                }
            }
            return true;
        }
        return false;
    }
}
