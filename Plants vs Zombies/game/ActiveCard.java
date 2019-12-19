package game;

public class ActiveCard extends Card {
    private int x , y;
    private int remainingHp;
    private int crateTime;

    public int getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(int crateTime) {
        this.crateTime = crateTime;
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
    public void doAction() {
        if(this.getCreature() instanceof Zombie) {
            Zombie zombie = (Zombie)this.getCreature();
            zombie.move(x, y);
        }
        this.getCreature().doAction();
    }
}
