public abstract class Player {
    private int sunInGame;
    abstract void doAction();
    public void addSun(int sun){
        this.sunInGame+=sun;
    }
}
