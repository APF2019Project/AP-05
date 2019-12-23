package Main;

import java.util.ArrayList;

public class Map {
    private int row, col;
    ArrayList<ActiveCard> activeCardArrayList;
    ArrayList<GunShot> gunShotArrayList;
    boolean[] isWater;

    public boolean isWater(int x){
        return this.isWater[x];
    }
    public boolean isDry(int x,int y){
        if(!isWater(x))return true;
        for(ActiveCard activeCard:activeCardArrayList){
            if(activeCard.getX()==x && activeCard.getY()==y && activeCard.getCreature() instanceof LilyPad){
                return true;
            }
        }
        return false;
    }
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
    public ActiveCard getZombieIn(int y,int xl,int vx){
        ActiveCard nearest=null;
        for(ActiveCard activeCard:activeCardArrayList){
            if(activeCard.getY()==y){
                if(vx<0){
                    if(activeCard.getX()<=xl && xl+vx<=activeCard.getX()){
                        if(nearest==null || nearest.getX()<activeCard.getX()){
                            nearest=activeCard;
                        }
                    }
                }else{
                    if(xl<=activeCard.getX() && activeCard.getX()<=xl+vx){
                        if(nearest==null || nearest.getX()>activeCard.getX()){
                            nearest=activeCard;
                        }
                    }
                }
            }
        }
        return nearest;
    }
    public void addActiveCard(ActiveCard activeCard){
        if(activeCard.getCreature()instanceof Plant){
            if(((Plant) activeCard.getCreature()).isWaterProof() ^  isDry(activeCard.getX() ,activeCard.getY())){
                activeCardArrayList.add(activeCard);
            }else{
                throw new Error("your can't put your plant here");
            }
        }else if(activeCard.getCreature() instanceof Zombie){
            if(((Zombie) activeCard.getCreature()).isSwimmer()){
                if(((Zombie) activeCard.getCreature()).isSwimmer() ^  !isWater(activeCard.getX())){
                    activeCardArrayList.add(activeCard);
                }else{
                    throw new Error("your can't put your zombie here");
                }
            }
        }else{
            throw new Error("add ActiveCard have a bog");
        }
    }
    public void run(){
        for(GunShot gunShot:gunShotArrayList){
            gunShot.doAction(this);
        }
        for(ActiveCard activeCard:activeCardArrayList){
            activeCard.doAction(this);
        }
        for(ActiveCard activeCard:activeCardArrayList){
            if(activeCard.getCreature() instanceof Zombie){
                if(((Zombie) activeCard.getCreature()).isWinning(activeCard)){
                    // zombies wining

                    return;
                }
            }
        }
    }
}
