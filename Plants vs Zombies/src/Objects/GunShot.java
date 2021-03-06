package Objects;


import Main.ActiveCard;
import Main.GameData;
import Main.Map;
import Player.Player;

public class GunShot {
    private Gun gun;
    private int x, y;
    private int vx;
    private Player owner;
    private int destination = -1000;

    public GunShot(Gun gun, int x, int y, int vx, Player player) {
        this.gun = gun;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.owner = player;
    }

    public boolean isUsed() {
        return (destination != -1000);
    }

    public Player getOwner() {
        return owner;
    }

    public int getSignedVx() {
        return -vx;
    }

    public Gun getGun() {
        return gun;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void collision(ActiveCard activeCard) {
        if (isUsed()) return;
        if (gun.isIcy()) {
            activeCard.collisionSlowingGunShot(GameData.iceSlowDownTime, GameData.iceSlowDownPercent);
        }
        if (gun.isSleepy()) {
            activeCard.collisionSlowingGunShot(GameData.sleepySlowDownTime, GameData.sleepySlowDownPercent);
        }
        if (gun.isProjectile() || (activeCard.getCreature().getShield() != null && activeCard.getCreature().getShield().isFullBodyShield())) {
            if (activeCard.getShieldRemainingHp() > 0) {
                activeCard.setShieldRemainingHp(activeCard.getShieldRemainingHp() - gun.getDamage());
            }
            else {
                activeCard.setRemainingHp(activeCard.getRemainingHp() - gun.getDamage());
            }
        }
        else {
            activeCard.setRemainingHp(activeCard.getRemainingHp() - gun.getDamage());
        }
        destination = activeCard.getX();
    }

    public void doAction(Map map) {
        ActiveCard activeCard = map.getZombieIn(this.y, this.x, this.vx);
        if (activeCard == null) {
            this.x += this.vx;
        }
        else {
            collision(activeCard);
        }
    }
}
