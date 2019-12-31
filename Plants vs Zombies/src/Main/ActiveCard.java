package Main;


import Player.Player;

public class ActiveCard {
    private Creature creature;
    private int remainingHp, x, y;
    private int shieldRemainingHp = 0;
    private int remainingSlowDown = 0, slowDownPercent = 0;
    private int remainReloadTime;
    private Player owner;
    private boolean hasLadder;
    private boolean hasOrdak;

    public ActiveCard(Creature creature, int x, int y, Player player) throws Exception {
        if(creature.getRemainingCoolDown()!=0){
            throw new Exception("couldn't add this creature cool down is not 0");
        }
        this.creature = creature;
        /*if(creature instanceof Zombie){
            hasLadder=(((Zombie)creature).isHasLadder());
        }else{
            hasLadder=false;
        } kosshere mahz!!! mese hasordak bayad handle she*/
        this.remainingHp = creature.getFullHp();
        this.x = x;
        this.y = y;
        if (creature.getShield() != null)
            shieldRemainingHp = creature.getShield().getFullHp();
        owner = player;
        remainReloadTime = creature.getReloadTime();
    }

    public void setHasOrdak(boolean hasOrdak) {
        this.hasOrdak = hasOrdak;
    }
    public boolean isHasOrdak() {
        return hasOrdak;
    }

    public void setHasLadder(boolean hasLadder) throws Exception {
        if(((Zombie)creature).isSwimmerWithActiveCard(this)){
            throw new Exception("this zombie is already swimmer");
        }
        this.hasLadder = hasLadder;

    }
    public boolean isHasLadder() {
        return hasLadder;
    }
    public int getDistance(ActiveCard activeCard) {
        if(activeCard==null){
            return GameData.inf;
        }
        return Math.abs(activeCard.getX() - this.getX()) + Math.abs(activeCard.getY() - this.getY());
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
        if (shieldRemainingHp <= 0) {
            if (creature.getName().equals("Buckethead Zombie") && remainingHp >= 2) {
                remainingHp--;
            }
            if (creature.getName().equals("Buckethead Zombie") && remainingHp >= 2) {
                remainingHp--;
            }
            this.shieldRemainingHp = 0;
        } else {
            this.shieldRemainingHp = shieldRemainingHp;
        }
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
        this.remainingHp = Math.max(remainingHp, 0);
    }

    public void damaged(int Hp){
        if(shieldRemainingHp>0){
            int res=Math.min(Hp,shieldRemainingHp);
            Hp-=res;
            this.setShieldRemainingHp(shieldRemainingHp-res);
        }
        this.setRemainingHp(this.getRemainingHp()-Hp);
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

    public void doAction(Map map) throws Exception {
        if (remainReloadTime <= 0) {
            boolean isAct=creature.doAction(this, map);
            if(!isAct){
                return ;
            }
            if(creature.isDisposable()){
                remainingHp=0;
                shieldRemainingHp=0;
                return;
            }
            remainReloadTime = creature.getReloadTime()-1;
        } else {
            remainReloadTime--;
        }
        if (remainingSlowDown > 0) {
            remainingSlowDown--;
            if (remainingSlowDown == 0)
                slowDownPercent = 0;
        }

    }

    public void collisionSlowingGunShot(int slowDownTime, int slowDownPercent) {
        if (slowDownPercent > this.slowDownPercent) {
            this.slowDownPercent = slowDownPercent;
            this.remainingSlowDown = slowDownTime;
        }
    }
}
