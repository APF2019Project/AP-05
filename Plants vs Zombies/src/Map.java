import java.util.ArrayList;

public class Map {
    private int row, col;
    ArrayList<ActiveCard> activeCardArrayList;
    ArrayList<GunShot> gunShotArrayList;
    boolean[] isWater;

    private boolean isInMap(int x,int y){
        return !(x<0 || x>=row || y<0 || y>=col);
    }
    public Map(int row, int col) {
        this.row = row;
        this.col = col;
        isWater = new boolean[row];
    }
    public void addGunShot(GunShot gunShot){
        if(isInMap(gunShot.getX(),gunShot.getY())){
            gunShotArrayList.add(gunShot);
        }
    }
    public ActiveCard getZombieIn(int y,int xl,int xr){
        // To-Do
        return null;
    }
    public void run(){
        for(GunShot gunShot:gunShotArrayList){
            gunShot.doAction(this);
        }
        for(ActiveCard activeCard:activeCardArrayList){
            activeCard.doAction(this);
        }
    }
}
