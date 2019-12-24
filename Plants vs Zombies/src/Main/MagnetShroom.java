package Main;

public class MagnetShroom extends Plant {
    public MagnetShroom(String name, boolean disposable, int coolDown, int fullHp, int remainingCoolDown,
                        int reloadTime, Shield shield, int sunCost, boolean cactus, boolean peppery,
                        boolean waterProof) {
        super(name, disposable, coolDown, fullHp, remainingCoolDown, reloadTime, shield,
                cactus, peppery, waterProof);
    }
    public void doAction(ActiveCard magnet, Map map) {
        for(ActiveCard zombie:map.activeCardArrayList){
            if(zombie.getCreature() instanceof  Zombie) {
                if (magnet.getDistance(zombie) <= GameData.maxMagnetRange &&
                        zombie.getShieldRemainingHp() > 0 && zombie.getCreature().getShield().isMetal()) {
                    magnet.setRemainReloadTime(GameData.MagnetEatingTime);
                    zombie.setShieldRemainingHp(0);
                }
            }
        }
    }
}
