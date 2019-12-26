package Main;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;

public class Map {
    ArrayList<ActiveCard> activeCardArrayList;
    ArrayList<GunShot> gunShotArrayList;
    boolean[] isWater;
    private int row, col;

    public Map(int row, int col) {
        this.row = row;
        this.col = col;
        isWater=GameData.isWaterInDayMapMode.clone();
    }

    public Map(int row, int col,MapMode mapMode) {
        this.row = row;
        this.col = col;
        if(mapMode.equals(MapMode.Water)){
            isWater=GameData.isWaterInWaterMapMode.clone();
        }else {
            isWater=GameData.isWaterInDayMapMode.clone();
        }
    }

    public int getRow() {
        return row;
    }
    public boolean hasWater(){
        boolean hasWater=false;
        for(int i=0;i<row;i++){
            hasWater|=isWater[i];
        }
        return hasWater;
    }
    public int getCol() {
        return col;
    }

    public boolean isWater(int x) {
        return this.isWater[x];
    }

    public boolean isDry(int x, int y) {
        if (!isWater(x)) return true;
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getX() == x && activeCard.getY() == y && activeCard.getCreature() instanceof LilyPad) {
                return true;
            }
        }
        return false;
    }

    private boolean isInMap(int x, int y) {
        return !(x < 0 || x >= row || y < 0 || y >= col);
    }

    public void addGunShot(GunShot gunShot) {
        if (isInMap(gunShot.getX(), gunShot.getY())) {
            gunShotArrayList.add(gunShot);
        }
    }

    public ActiveCard getZombieIn(int y, int xl, int vx) {
        ActiveCard nearest = null;
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getY() == y) {
                if (vx < 0) {
                    if (activeCard.getX() <= xl && xl + vx <= activeCard.getX()) {
                        if (nearest == null || nearest.getX() < activeCard.getX()) {
                            nearest = activeCard;
                        }
                    }
                } else {
                    if (xl <= activeCard.getX() && activeCard.getX() <= xl + vx) {
                        if (nearest == null || nearest.getX() > activeCard.getX()) {
                            nearest = activeCard;
                        }
                    }
                }
            }
        }
        return nearest;
    }

    public void addActiveCard(ActiveCard activeCard) {
        if(activeCard.getOwner().getSunInGame()<activeCard.getCreature().getPrice()){
            throw new Error("you don't have Enough money");
        }
        if (activeCard.getCreature() instanceof Plant) {
            if (((Plant) activeCard.getCreature()).isWaterProof() ^ isDry(activeCard.getX(), activeCard.getY())) {
                activeCardArrayList.add(activeCard);
            } else {
                throw new Error("your can't put your plant here");
            }
        } else if (activeCard.getCreature() instanceof Zombie) {
            if (((Zombie) activeCard.getCreature()).isSwimmer()) {
                if (((Zombie) activeCard.getCreature()).isSwimmer() ^ !isWater(activeCard.getX())) {
                    activeCardArrayList.add(activeCard);
                } else {
                    throw new Error("your can't put your zombie here");
                }
            }
        } else {
            throw new Error("add ActiveCard have a bog");
        }
        activeCard.getOwner().setSunInGame(activeCard.getOwner().getSunInGame()-activeCard.getCreature().getPrice());
    }
    ActiveCard findPlantIn(int x, int y){
        for(ActiveCard activeCard:activeCardArrayList){
            if(activeCard.getCreature() instanceof Plant){
                if(activeCard.getX()==x && activeCard.getY()==y){
                    return activeCard;
                }
            }
        }
        return null;
    }
    GunShot getGunShotIn(int y,int xl,int xr){
        for(GunShot gunshot:gunShotArrayList){
            if(gunshot.getY()==y && xl<=gunshot.getX() && gunshot.getX()<=xr){
                return gunshot;
            }
        }
        return null;
    }
    int hasNoPlantIn(int y,int x){
        int maxX=-1;
        for(ActiveCard activeCard:activeCardArrayList){
            if(activeCard.getCreature() instanceof  Plant){
                maxX= Math.max(maxX,activeCard.getX());
            }
        }
        return maxX;
    }
    public void run() {
        for (GunShot gunShot : gunShotArrayList) {
            gunShot.doAction(this);
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            activeCard.doAction(this);
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Zombie) {
                if (((Zombie) activeCard.getCreature()).isWinning(activeCard)) {
                    // zombies wining

                    return;
                }
            }
        }
        ArrayList<ActiveCard> dies = new ArrayList<ActiveCard>();
        ArrayList<GunShot> usedGunShot = new ArrayList<GunShot>();
        for (GunShot gunShot : gunShotArrayList) {
            if(gunShot.isUsed()){
                usedGunShot.add(gunShot);
            }
        }
        for (GunShot gunShot : usedGunShot) {
            gunShotArrayList.remove(gunShot);
        }
        for (ActiveCard activeCard : dies) {
            activeCardArrayList.remove(activeCard);
        }
    }
}
