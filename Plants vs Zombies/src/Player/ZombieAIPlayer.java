package Player;

import Main.ActiveCard;
import Main.Creature;
import Main.Map;
import Main.Zombie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import Main.User;

public class ZombieAIPlayer extends ZombiePlayer {
    public ZombieAIPlayer(Map map, User user) {
        super(map,user);
    }


    public void doAction() throws Exception {
        Random rand = new Random();
        while(true) {
            ArrayList<Creature> available = new ArrayList<Creature>();
            for (Creature creature : this.getCreaturesOnHand()) {
                if (creature.getPrice() <= this.getSunInGame()) {
                    available.add(creature);
                }
            }
            if (!available.isEmpty()) {
                int rand_int = rand.nextInt(available.size());
                Map map = this.getMap();
                int y = map.getCol() - 1;
                int x = rand.nextInt(map.getRow());
                ActiveCard zombie = new ActiveCard(available.get(rand_int), x, y, this);
                try {
                    map.addActiveCard(zombie);
                }catch (Error | Exception e){
                    // zombie is not ok with location (zombie is swimmer and its location is dry or visa visa)
                }
            }else{
                break;
            }
        }
    }
    @Override
    public void gameAction() {

    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
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
