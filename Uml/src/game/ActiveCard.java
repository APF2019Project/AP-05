package game;

public class ActiveCard extends Card {
    private int x , y;

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
