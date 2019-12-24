package Main;

public class Mine extends Plant {
    private int deltaX, deltaY;

    public Mine(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown, int reloadTime,
                Shield shield, int sunCost, boolean cactus, boolean peppery, boolean waterProof, int deltaX, int deltaY) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield, sunCost, cactus, peppery, waterProof);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    @Override
    public void doAction(ActiveCard activeCard, Map map) {
        for (ActiveCard activeCard1 : map.activeCardArrayList) {
            if ((activeCard.getCreature() instanceof Zombie) && Math.abs(activeCard.getX() - activeCard1.getX()) <= deltaX
                    && Math.abs(activeCard.getY() - activeCard1.getY()) <= deltaY) {
                activeCard1.setRemainingHp(0);
            }
        }
    }
}
