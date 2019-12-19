package game;
public class Creature {
    private String name;
    private int fullHp;
    private int  coolDown;
    private boolean disposable;

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public boolean isDisposable() {
        return disposable;
    }

    public void setDisposable(boolean disposable) {
        this.disposable = disposable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFullHp() {
        return fullHp;
    }

    public void setFullHp(int fullHp) {
        this.fullHp = fullHp;
    }


    public void getHit(int damage) {

    }
    public void doAction() {

    }
}
