package game;

public class Card {
    private Creature creature;

    int coolDown;
    int remainCoolDown;
    public Creature getCreature() {
        return creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }
}
