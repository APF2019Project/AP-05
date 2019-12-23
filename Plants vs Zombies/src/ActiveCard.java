public class ActiveCard {
    private Creature creature;
    private int remainingHp, x, y;
    private int shieldRemainingHp = 0;
    private int remainingSlowDown = 0, slowDownPercent = 0;
    private int remainReloadTime=0;
    private Player owner;
    public ActiveCard(Creature creature, int remainingHp, int x, int y,Player player) {
        this.creature = creature;
        this.remainingHp = remainingHp;
        this.x = x;
        this.y = y;
        if (creature.getShield() != null)
            shieldRemainingHp = creature.getShield().getFullHp();
        owner=player;
        remainReloadTime=creature.getReloadTime();
    }
    public int getDistance(ActiveCard activeCard){
        return Math.abs(activeCard.getX()-this.getX())+Math.abs(activeCard.getY()-this.getY());
    }
    public Player getOwner() {
        return owner;
    }

    public int getRemainReloadTime() {
        return remainReloadTime;
    }
    public void setRemainReloadTime(int remainReloadTime) {
        this.remainReloadTime = remainReloadTime;
    }

    public int getSlowDownPercent() {
        return slowDownPercent;
    }

    public void setSlowDownPercent(int slowDownPercent) {
        this.slowDownPercent = slowDownPercent;
    }

    public int getRemainingSlowDown() {
        return remainingSlowDown;
    }

    public void setRemainingSlowDown(int remainingSlowDown) {
        this.remainingSlowDown = remainingSlowDown;
    }

    public int getShieldRemainingHp() {
        return shieldRemainingHp;
    }

    public void setShieldRemainingHp(int shieldRemainingHp) {
        this.shieldRemainingHp = shieldRemainingHp;
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public int getRemainingHp() {
        return remainingHp;
    }

    public void setRemainingHp(int remainingHp) {
        this.remainingHp = remainingHp;
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
    public void doAction(Map map){
        if(remainReloadTime==0) {
            creature.doAction(this,map);
            remainReloadTime=creature.getReloadTime();
        }else{
            remainReloadTime--;
        }
    }
}
