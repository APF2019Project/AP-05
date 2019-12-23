public abstract class Player {
    private int sunInGame;
    private int killingEnemyCount;
    abstract void doAction();
    public void addSun(int sun){
        this.sunInGame+=sun;
    }
}
