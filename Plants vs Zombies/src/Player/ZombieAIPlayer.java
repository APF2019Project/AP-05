package Player;

import Main.ActiveCard;
import Main.Connection;
import Main.Map;
import Main.MapMode;
import Objects.Creature;
import Objects.Zombie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Supplier;

public class ZombieAIPlayer extends ZombiePlayer {
    Random random;
    private int whenPutZombie, lastZombieDie;
    private boolean isStart;

    public ZombieAIPlayer(Connection connection) {
        super(connection);
        random = new Random();
        isStart = false;
        lastZombieDie = 0;
        whenPutZombie = random.nextInt(3) + 3;
    }

    public void doAction(Supplier<Void> supplier) throws Exception {
        super.doAction();
        if (connection.getUser().getPlayer().getMap().getMapMode().equals(MapMode.Rail)) {
            this.addSun(1000);
            if (whenPutZombie == 0) {
                ArrayList<Creature> available = new ArrayList<Creature>();
                for (Creature creature : this.getCreaturesOnHand()) {
                    if (creature.getPrice() <= this.getSunInGame()) {
                        available.add(creature);
                    }
                }
                if (!available.isEmpty()) {
                    int rand_int = random.nextInt(available.size());
                    Map map = this.getMap();
                    while (true) {
                        int x = map.getCol() - 1;
                        int y = random.nextInt(map.getRow());
                        ActiveCard zombie = new ActiveCard(available.get(rand_int), x, y, this);
                        if (map.canAddActiveCardAndBuy(zombie)) {
                            map.addActiveCard(zombie);
                            break;
                        }
                    }
                }
                whenPutZombie = random.nextInt(3) + 3;
            }
            whenPutZombie--;
        }
        else if (getMap().getMapMode().equals(MapMode.Day) || getMap().getMapMode().equals(MapMode.Water)) {
            Map map = this.getMap();
            if (!map.isMapHasZombie()) {
                lastZombieDie++;
            }
            else {
                isStart = true;
                lastZombieDie = 0;
            }
            if (!isStart && lastZombieDie == 3) {
                startWave();
            }
            else if (isStart && lastZombieDie == 7) {
                startWave();
            }
        }
        if (supplier != null) {
            supplier.get();
        }
    }

    @Override
    public void startWave() throws Exception {
        super.startWave();
        this.addSun(1000);
        int numberOfZombie = random.nextInt(7) + 4;
        for (int i = 0; i < numberOfZombie; i++) {
            while (true) {
                ArrayList<Creature> available = new ArrayList<Creature>();
                for (Creature creature : this.getCreaturesOnHand()) {
                    if (creature.getPrice() <= this.getSunInGame()) {
                        available.add(creature);
                    }
                }
                if (!available.isEmpty()) {
                    int rand_int = random.nextInt(available.size());
                    Map map = this.getMap();
                    int x = map.getCol() - 1;
                    int y = random.nextInt(map.getRow());
                    ActiveCard zombie = new ActiveCard(available.get(rand_int), x, y, this);

                    if (map.canAddActiveCardAndBuy(zombie)) {
                        zombieCardsInThisWave.add(zombie);
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }

    @Override
    public void gameAction() {

    }

    @Override
    public void pickCards(Supplier<Void> supplier) {
        Random rand = new Random();
        Map map = this.getMap();
        ArrayList<Zombie> dryZombie = new ArrayList<Zombie>();
        ArrayList<Zombie> wetZombie = new ArrayList<Zombie>();
        for (Zombie zombie : Zombie.getAllZombies()) {
            if (zombie.isSwimmer()) {
                wetZombie.add(zombie);
            }
            else {
                dryZombie.add(zombie);
            }
        }
        int dry, wet;
        if (map.hasWater()) {
            wet = rand.nextInt(wetZombie.size() - 1) + 1;
        }
        else {
            wet = 0;
        }
        dry = 7 - wet;////// should add to gameData
        Collections.shuffle(dryZombie);
        Collections.shuffle(wetZombie);
        for (int i = 0; i < wet; i++) {
            this.addCreaturesOnHand(wetZombie.get(i));
        }
        for (int i = 0; i < dry; i++) {
            this.addCreaturesOnHand(dryZombie.get(i));
        }
        supplier.get();
    }
}
