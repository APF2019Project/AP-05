package Main;

import Player.Player;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    ArrayList<ActiveCard> activeCardArrayList = new ArrayList<>();
    ArrayList<GunShot> gunShotArrayList = new ArrayList<>();
    private boolean[] isWater;
    private int row, col, numberOfRemainedWaves, turnNumber;
    MapMode mapMode;
    final private Player plantPlayer, zombiePlayer;

    public ArrayList<ActiveCard> getActiveCardArrayList() {
        return activeCardArrayList;
    }

    public Player getPlantPlayer() {
        return plantPlayer;
    }

    public Player getZombiePlayer() {
        return zombiePlayer;
    }

    public Map(int row, int col, MapMode mapMode, Player plantPlayer, Player zombiePlayer, int wavesNumber) {
        this.row = row;
        this.col = col;
        this.plantPlayer = plantPlayer;
        this.zombiePlayer = zombiePlayer;
        this.mapMode = mapMode;
        this.numberOfRemainedWaves = wavesNumber;
        plantPlayer.setMap(this);
        zombiePlayer.setMap(this);
        if (mapMode.equals(MapMode.Water)) {
            isWater = GameData.isWaterInWaterMapMode.clone();
        } else {
            isWater = GameData.isWaterInDayMapMode.clone();
        }
    }

    public int getRow() {
        return row;
    }

    public boolean hasWater() {
        boolean hasWater = false;
        for (int i = 0; i < row; i++) {
            hasWater |= isWater[i];
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

    public void startWave(){
        numberOfRemainedWaves--;
    }

    public boolean canAddActiveCard(ActiveCard activeCard) {
        if (activeCard.getCreature() instanceof Plant) {
            if (activeCard.getX() % 2 == 1) {
                return false;
            }
            if (((Plant) activeCard.getCreature()).isWaterProof() && findPlantIn(activeCard.getX(), activeCard.getY())
                    != null && findPlantIn(activeCard.getX(), activeCard.getY()).getCreature() instanceof LilyPad) {
                return true;
            } else if (!((Plant) activeCard.getCreature()).isWaterProof() &&
                    findPlantIn(activeCard.getX(), activeCard.getY()) == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return (((Zombie) activeCard.getCreature()).isSwimmerWithActiveCard(activeCard) == isWater(activeCard.getX()));
        }
    }

    public void removeActiveCard(ActiveCard activeCard) {
        activeCardArrayList.remove(activeCard);
    }

    public void addActiveCard(ActiveCard activeCard) {
        if(activeCard.getCreature().getName().equals("bungee zombie")){
            Random random=new Random();
            activeCard.setX(random.nextInt(col));
            activeCard.setY(random.nextInt(row));
        }
        if(activeCard.getCreature().getRemainingCoolDown()==0){
            activeCardArrayList.add(activeCard);
            activeCard.getCreature().setRemainingCoolDown(activeCard.getCreature().getCoolDown());
        }else{
            /// cool down is not handel:D
        }

    }

    public ActiveCard findPlantIn(int x, int y) {
        ArrayList<ActiveCard> option = new ArrayList<ActiveCard>();
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Plant) {
                if (activeCard.getX() == x && activeCard.getY() == y) {
                    option.add(activeCard);
                }
            }
        }
        if (option.isEmpty()) {
            return null;
        }
        if (option.size() == 1) {
            return option.get(0);
        }
        for (int i = 0; i < option.size(); i++) {
            if (!(option.get(i).getCreature() instanceof LilyPad)) {
                return option.get(i);
            }
        }
        return null;
    }

    GunShot getGunShotIn(int y, int xl, int xr) {
        for (GunShot gunshot : gunShotArrayList) {
            if (gunshot.getY() == y && xl <= gunshot.getX() && gunshot.getX() <= xr) {
                return gunshot;
            }
        }
        return null;
    }

    int hasNoPlantIn(int y, int x) {
        int maxX = -1;
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Plant && !activeCard.isHasLadder()) {
                maxX = Math.max(maxX, activeCard.getX());
            }
        }
        return maxX;
    }

    public GameStatus run() throws Exception {
        turnNumber++;
        for(Creature creature:Creature.getAllCreatures()){
            creature.setRemainingCoolDown(Math.max(0,creature.getRemainingCoolDown()-1));
        }
        if(numberOfRemainedWaves<0){
            return GameStatus.PlantPlayerWins;
        }
        for (GunShot gunShot : gunShotArrayList) {
            gunShot.doAction(this);
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            activeCard.doAction(this);
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Zombie) {
                if (((Zombie) activeCard.getCreature()).isWinning(activeCard)) {
                    return GameStatus.ZombiePlayerWins;
                }
            }
        }
        ArrayList<ActiveCard> dies = new ArrayList<ActiveCard>();
        ArrayList<GunShot> usedGunShot = new ArrayList<GunShot>();
        for (GunShot gunShot : gunShotArrayList) {
            if (gunShot.isUsed()) {
                usedGunShot.add(gunShot);
            }
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getRemainingHp() == 0) {
                dies.add(activeCard);
            }
        }
        for (GunShot gunShot : usedGunShot) {
            gunShotArrayList.remove(gunShot);
        }
        for (ActiveCard activeCard : dies) {
            activeCardArrayList.remove(activeCard);
            if (activeCard.getCreature() instanceof Plant) {
                zombiePlayer.addSun(activeCard.getCreature().getKillingReward());
            }
            if (activeCard.getCreature() instanceof Zombie) {
                plantPlayer.addSun(activeCard.getCreature().getKillingReward());
            }
        }
        if(mapMode!=mapMode.Rail){
            Random random=new Random();
            if(random.nextInt()%3==0){
                plantPlayer.addSun(random.nextInt(4)+2);
            }
        }
        mapSimpleShow();
        return GameStatus.OnGame;
    }

    ActiveCard getNearestZombie(ActiveCard activeCard) {
        ActiveCard nearestZombie = null;
        int distance = GameData.inf;
        for (ActiveCard zombie : activeCardArrayList) {
            if (zombie.getCreature() instanceof Zombie) {
                if (activeCard.getDistance(zombie) > distance) {
                    distance = activeCard.getDistance(zombie);
                    nearestZombie = zombie;
                }
            }
        }
        return nearestZombie;
    }
    void mapSimpleShow() {
        char jad[][] = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (isWater[i]) {
                    jad[i][j] = '~';
                } else {
                    jad[i][j] = ' ';
                }
            }
        }
        for (GunShot gunShot : gunShotArrayList) {
            jad[gunShot.getX()][gunShot.getY()] = '*';
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Zombie) {
                jad[activeCard.getX()][activeCard.getY()] = 'Z';
            } else {
                jad[activeCard.getX()][activeCard.getY()] = 'P';
            }
        }
        System.out.println("/////////////////////////////////////////");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(jad[i][j]);
            }
            System.out.println();
        }
        System.out.println("/////////////////////////////////////////");
    }
}
