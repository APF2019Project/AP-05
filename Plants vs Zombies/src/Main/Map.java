package Main;

import Objects.*;
import Player.*;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    final private Player plantPlayer, zombiePlayer;
    MapMode mapMode;
    private ArrayList<ActiveCard> activeCardArrayList = new ArrayList<>();
    private ArrayList<GunShot> gunShotArrayList = new ArrayList<>();
    private boolean[] isWater;
    private int row, col, numberOfRemainedWaves;
    private static ArrayList<Map> allRunningGame=new ArrayList<Map>();

    public static void checkAllMap(){
        for(Map map:allRunningGame){
            try {
                if (((map.getPlantPlayer() instanceof PlantOnDayAndWaterModeHumanPlayer
                        || map.getPlantPlayer() instanceof PlantOnRailGameModeHumanPlayer) &&
                        map.getPlantPlayer().getConnection().getUser().getPlayer().getMap()!=map) ||
                        (map.getZombiePlayer() instanceof ZombieHumanPlayer &&
                                map.getZombiePlayer().getConnection().getUser().getPlayer().getMap()!=map)){
                    allRunningGame.remove(map);
                }
            }catch (Exception e){
                allRunningGame.remove(map);
            }
        }
    }
    public static ArrayList<Map> getAllRunningGame() {
        return allRunningGame;
    }
    public Map(int row, int col, MapMode mapMode, Player plantPlayer, Player zombiePlayer, int wavesNumber) {
        allRunningGame.add(this);
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
        }
        else {
            isWater = GameData.isWaterInDayMapMode.clone();
        }
        Creature creature = Creature.getCreatureByName("lawnmower");
        for (int i = 0; i < row; i++) {
            try {
                ActiveCard activeCard = new ActiveCard(creature, 2, i, plantPlayer);
                this.addActiveCard(activeCard);
            } catch (Exception e) {
                System.out.println("cannot add buchers");
            }
        }
    }

    public ArrayList<GunShot> getGunShotArrayList() {
        return gunShotArrayList;
    }

    public ArrayList<ActiveCard> getActiveCardArrayList() {
        return activeCardArrayList;
    }

    public Player getPlantPlayer() {
        return plantPlayer;
    }

    public Player getZombiePlayer() {
        return zombiePlayer;
    }

    public MapMode getMapMode() {
        return mapMode;
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

    public boolean isWater(int y) {
        return this.isWater[y];
    }

    public boolean isDry(int x, int y) {
        if (!isWater(y)) return true;
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getX() == x && activeCard.getY() == y && activeCard.getCreature() instanceof LilyPad) {
                return true;
            }
        }
        return false;
    }

    private boolean isInMap(int x, int y) {
        return !(x < 0 || x >= col || y < 0 || y >= row);
    }

    public void addGunShot(GunShot gunShot) {
        //System.out.println(gunShot.getX()+' '+gunShot.getY());
        if (isInMap(gunShot.getX(), gunShot.getY())) {
            gunShotArrayList.add(gunShot);
        }
    }

    public ActiveCard getZombieIn(int y, int xl, int vx) {
        ActiveCard nearest = null;
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getY() == y && activeCard.getCreature() instanceof Zombie) {
                if (vx < 0) {
                    if (activeCard.getX() <= xl && xl + vx <= activeCard.getX()) {
                        if (nearest == null || nearest.getX() < activeCard.getX()) {
                            nearest = activeCard;
                        }
                    }
                }
                else {
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

    public void startWave() {
        numberOfRemainedWaves--;
    }

    public boolean canAddActiveCardAndBuy(ActiveCard activeCard) throws Exception {
        if (!isInMap(activeCard.getX(), activeCard.getY())) {
            return false;
        }
        if (activeCard.getCreature() instanceof Plant) {
            if (activeCard.getX() % GameData.slices != GameData.slices / 2) {
                System.out.println("EEEEE4");
                return false;
            }
        }
        if (findPlantIn(activeCard.getX(), activeCard.getY()) != null &&
                !(findPlantIn(activeCard.getX(), activeCard.getY()).getCreature() instanceof LilyPad)) {
            System.out.println("eeeeeeee: " + findPlantIn(activeCard.getX(), activeCard.getY()).getCreature().getName());
            return false;
        }
        if (isWater(activeCard.getY()) && !activeCard.getCreature().isMarine(activeCard) &&
                findPlantIn(activeCard.getX(), activeCard.getY()) == null) {
            System.out.println("EEEEE1");
            return false;
        }
        if (!isWater(activeCard.getY()) && !activeCard.getCreature().isOnshore(activeCard)) {
            System.out.println("EEEEE2");
            return false;
        }
        return activeCard.getOwner().pickCreature(activeCard.getCreature());
    }

    public void removeActiveCard(ActiveCard activeCard) {
        activeCardArrayList.remove(activeCard);
    }

    public void addActiveCard(ActiveCard activeCard) throws Exception {
        if (activeCard.getCreature().getName().equals("bungee zombie")) {
            Random random = new Random();
            activeCard.setX(random.nextInt(7)*GameData.slices+GameData.slices/2);
            activeCard.setY(random.nextInt(row));
        }
        if (activeCard.getCreature().getRemainingCoolDown() == 0 || mapMode.equals(MapMode.Rail)
                || (mapMode.equals(MapMode.Zombie) && activeCard.getCreature() instanceof Plant)) {
            activeCardArrayList.add(activeCard);
            activeCard.getCreature().setRemainingCoolDown(activeCard.getCreature().getCoolDown());
        }
        else {
            throw new Exception("cool down is not 0");
            /// cool down is not handel:D
        }

    }

    public ActiveCard findPlantIn(int x, int y) {
        ArrayList<ActiveCard> option = new ArrayList<>();
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
        for (ActiveCard activeCard : option) {
            if (!(activeCard.getCreature() instanceof LilyPad)) {
                return activeCard;
            }
        }
        return null;
    }

    public ArrayList<GunShot> getGunShotIn(int y, int xl, int xr) {
        ArrayList<GunShot> ans = new ArrayList<>();
        for (GunShot gunshot : gunShotArrayList) {
            if (!gunshot.isUsed() && gunshot.getY() == y && xl <= gunshot.getX() && gunshot.getX() <= xr) {
                ans.add(gunshot);
            }
        }
        return ans;
    }

    public int hasNoPlantIn(int y, int x) {
        int maxX = -10000;
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Plant && !activeCard.isHasLadder()
                    && activeCard.getY() == y && activeCard.getX() <= x) {
                maxX = Math.max(maxX, activeCard.getX());
            }
        }
        return maxX;
    }

    public void gameActionForEachTurn() {
        if (!mapMode.equals(MapMode.Rail)) {
            Random random = new Random();
            if (random.nextInt() % 6 == 0) {
                plantPlayer.addSun(random.nextInt(2) + 1);
            }
        }

    }

    public boolean isMapHasPlant() {
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Plant) {
                return true;
            }
        }
        return false;
    }

    public boolean isMapHasZombie() {
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Zombie) {
                return true;
            }
        }
        return false;
    }
    public void gameIsFinish(){
        allRunningGame.remove(this);
    }

    public GameStatus run() throws Exception {
        for (Creature creature : plantPlayer.getCreaturesOnHand()) {
            creature.setRemainingCoolDown(Math.max(0, creature.getRemainingCoolDown() - 1));
        }
        for (Creature creature : zombiePlayer.getCreaturesOnHand()) {
            creature.setRemainingCoolDown(Math.max(0, creature.getRemainingCoolDown() - 1));
        }
        if (numberOfRemainedWaves < 0) {
            gameIsFinish();
            return GameStatus.PlantPlayerWins;
        }
        for (GunShot gunShot : gunShotArrayList) {
            gunShot.doAction(this);
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Plant)
                activeCard.doAction(this);
        }
        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getCreature() instanceof Zombie)
                activeCard.doAction(this);
        }
        if (mapMode.equals(MapMode.Zombie) && !isMapHasPlant()) {
            gameIsFinish();
            return GameStatus.ZombiePlayerWins;
        }
        if (!mapMode.equals(MapMode.Zombie)) {
            for (ActiveCard activeCard : activeCardArrayList) {
                if (activeCard.getCreature() instanceof Zombie) {
                    if (((Zombie) activeCard.getCreature()).isWinning(activeCard)) {
                        gameIsFinish();
                        return GameStatus.ZombiePlayerWins;
                    }
                }
            }
        }

        ArrayList<ActiveCard> dies = new ArrayList<ActiveCard>();
        ArrayList<GunShot> usedGunShot = new ArrayList<GunShot>();
        for (GunShot gunShot : gunShotArrayList) {
            if (gunShot.isUsed() || !isInMap(gunShot.getX(), gunShot.getY())) {
                usedGunShot.add(gunShot);
            }
        }

        for (ActiveCard activeCard : activeCardArrayList) {
            if (activeCard.getRemainingHp() == 0 || !isInMap(activeCard.getX(), activeCard.getY())) {
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
                zombiePlayer.setKillingEnemyCount(zombiePlayer.getKillingEnemyCount() + 1);
            }
            if (activeCard.getCreature() instanceof Zombie) {
                plantPlayer.addSun(activeCard.getCreature().getKillingReward());
                plantPlayer.setKillingEnemyCount(plantPlayer.getKillingEnemyCount() + 1);
            }
        }

        gameActionForEachTurn();

        return GameStatus.OnGame;
    }
    public ActiveCard getNearestZombie(ActiveCard activeCard) {
        ActiveCard nearestZombie = null;
        int distance = GameData.inf;
        for (ActiveCard zombie : activeCardArrayList) {
            if (zombie.getCreature() instanceof Zombie) {
                if (activeCard.getDistance(zombie) < distance) {
                    distance = activeCard.getDistance(zombie);
                    nearestZombie = zombie;
                }
            }
        }
        return nearestZombie;
    }
}
