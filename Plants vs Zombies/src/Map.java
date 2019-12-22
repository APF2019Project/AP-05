import java.util.ArrayList;

public class Map {
    private int row, col;
    ArrayList<ActiveCard> activeCardArrayList;
    ArrayList<GunShot> gunShotArrayList;
    boolean[] isWater;

    public Map(int row, int col) {
        this.row = row;
        this.col = col;
        isWater = new boolean[row];
    }

    public void run(){

    }
    public void checkCollisions(){

    }
}
