package Player;

import Main.ActiveCard;
import Main.GameData;
import Main.Map;
import Main.User;
import Objects.Creature;
import Objects.Plant;

import java.util.ArrayList;
import java.util.Random;

public class PlantAIPlayer extends PlantPlayer {
    public PlantAIPlayer(User user) {
        super(user);
    }

    @Override
    public void doAction() {

    }
    @Override
    public void gameAction() {

    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
        return true;
    }

    @Override
    public void pickCards() throws Exception {
        Random random=new Random();
        Map map=this.getMap();
        ArrayList<String> availablePlantName=new ArrayList<String>();
        if(!map.hasWater()){
            availablePlantName= GameData.DryModeAvailablePlantName;
        }else{
            availablePlantName= GameData.WetModeAvailablePlantName;
        }
        ArrayList<Creature> availablePlant=map.getPlantPlayer().getCreaturesOnHand();
        this.setSunInGame(GameData.inf);
        while(availablePlant.size()>0){
            Plant plant=(Plant)availablePlant.get(0);
            int x=random.nextInt(3);
            int y=random.nextInt(map.getRow());
            ActiveCard activeCard=new ActiveCard(plant,x,y,this);
            try{
                map.addActiveCard(activeCard);
                availablePlant.remove(0);
            }catch (Error e){
                // its really should be empty
            }
        }
    }
}
