package game;

public class Card {
    private Creature creature;
    int remainCoolDown;

    public int getRemainCoolDown() {
        return remainCoolDown;
    }

    public void setRemainCoolDown(int remainCoolDown) {
        this.remainCoolDown = remainCoolDown;
    }

    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }
}
