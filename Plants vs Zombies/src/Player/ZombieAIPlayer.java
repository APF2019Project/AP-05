package Player;

import Main.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class ZombieAIPlayer extends ZombiePlayer {
    private int whenPutZombie,lastZombieDie;
    private boolean isStart;
    Random random;

    public ZombieAIPlayer(User user) {
        super(user);
        random=new Random();
        isStart=false;
        lastZombieDie=0;
        whenPutZombie=random.nextInt(3)+3;
    }


    public void doAction() throws Exception {
        super.doAction();
        if(user.getPlayer().getMap().getMapMode().equals(MapMode.Rail)){
            this.addSun(1000);
            if(whenPutZombie==0){
                ArrayList<Creature> available = new ArrayList<Creature>();
                for (Creature creature : this.getCreaturesOnHand()) {
                    if (creature.getPrice() <= this.getSunInGame()) {
                        available.add(creature);
                    }
                }
                if (!available.isEmpty()) {
                    int rand_int = random.nextInt(available.size());
                    Map map = this.getMap();
                    while(true) {
                        int x = random.nextInt(map.getRow());
                        int y = map.getRow() - 1;
                        ActiveCard zombie = new ActiveCard(available.get(rand_int), x, y, this);
                        if (map.canAddActiveCardAndBuy(zombie)) {
                            map.addActiveCard(zombie);
                            break;
                        }
                    }
                }
                whenPutZombie=random.nextInt(3)+3;
            }
            whenPutZombie--;
        }
        else if(getMap().getMapMode().equals(MapMode.Day) || getMap().getMapMode().equals(MapMode.Water)) {
            Map map = this.getMap();
            if (!map.isMapHasZombie()) {
                lastZombieDie++;
                isStart=true;
            }else{
                lastZombieDie = 0;
            }
            if(!isStart && lastZombieDie==3){
                startWave();
            }else if(isStart && lastZombieDie==7){
                startWave();
            }
        }
    }
    @Override
    public void startWave() throws Exception {
        this.addSun(1000);
        int numberOfZombie=random.nextInt(7)+4;
        for(int i=0;i<numberOfZombie;i++) {
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
                    int x = random.nextInt(map.getRow());
                    int y = map.getRow() - 1;
                    ActiveCard zombie = new ActiveCard(available.get(rand_int), x, y, this);
                    if (map.canAddActiveCardAndBuy(zombie)) {
                        map.addActiveCard(zombie);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public void gameAction() {

    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
        if (getSunInGame() < creature.getPrice()) {
            return false;
        }
        setSunInGame(getSunInGame() - creature.getPrice());
        return true;
    }

    @Override
    public void pickCards() {
        Random rand = new Random();
        Map map = this.getMap();
        ArrayList<Zombie> dryZombie=new ArrayList<Zombie>();
        ArrayList<Zombie> wetZombie=new ArrayList<Zombie>();
        for(Zombie zombie:Zombie.getAllZombies()){
            if(zombie.isSwimmer()){
                wetZombie.add(zombie);
            }else{
                dryZombie.add(zombie);
            }
        }
        int dry,wet;
        if(map.hasWater()){
            wet=rand.nextInt(wetZombie.size()-1)+1;
        }else{
            wet=0;
        }
        dry=7-wet;////// should add to gameData
        Collections.shuffle(dryZombie);
        Collections.shuffle(wetZombie);
        for(int i=0;i<wet;i++){
            this.addCreaturesOnHand(wetZombie.get(i));
        }
        for(int i=0;i<dry;i++){
            this.addCreaturesOnHand(dryZombie.get(i));
        }
    }
}
