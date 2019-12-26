package Player;

import Main.*;

import java.util.ArrayList;
import java.util.Random;

public class PlantAIPlayer extends PlantPlayer {
    public PlantAIPlayer(Map map) {
        super(map);
    }

    @Override
    public void doAction() {

    }

    @Override
    public void gameAction() {

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
        ArrayList<Plant> availablePlant=new ArrayList<Plant>();
        for(String name:availablePlantName){
            availablePlant.add((Plant) Creature.getCreatureByName(name));
        }
        this.setSunInGame(GameData.inf);
        while(true){
            Plant plant=availablePlant.get(0);
            int x=random.nextInt(3);
            int y=random.nextInt(map.getRow());
            ActiveCard activeCard=new ActiveCard(plant,x,y,this);
            try{
                map.addActiveCard(activeCard);
                availablePlant.remove(0);
            }catch (Error e){

            }
        }
    }
}
