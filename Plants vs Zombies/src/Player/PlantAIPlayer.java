package Player;

import Main.ActiveCard;
import Main.Connection;
import Main.GameData;
import Main.Map;
import Objects.Creature;
import Objects.Plant;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class PlantAIPlayer extends PlantPlayer {
    public PlantAIPlayer(Connection connection) {
        super(connection);
    }

    @Override
    public void doAction(Supplier<Void> supplier) {
        if (supplier != null) {
            supplier.get();
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
    public void pickCards(Supplier<Void> supplier) throws Exception {
        Random random = new Random();
        Map map = this.getMap();
        ArrayList<String> availablePlantName = new ArrayList<>();
        if (!map.hasWater()) {
            availablePlantName = GameData.DryModeAvailablePlantName;
        }
        else {
            availablePlantName = GameData.WetModeAvailablePlantName;
        }
        //ArrayList<Creature> availablePlant = map.getPlantPlayer().getCreaturesOnHand();
        this.setSunInGame(GameData.inf);
        System.out.println("KHOB MIRIM");
        for (int i = 0; i < availablePlantName.size(); i++) {
            System.out.println(availablePlantName.get(i));
            Plant plant = Plant.getPlantByName(availablePlantName.get(i));
            for (int j = 0; j < 1000; j++) {
                int x = random.nextInt(3) * GameData.slices + GameData.slices / 2;
                int y = random.nextInt(map.getRow());
                System.out.println(x + ":" + y);
                assert plant != null;
                try {
                    ActiveCard activeCard = new ActiveCard(plant, x, y, this);
                    map.addActiveCard(activeCard);
                    System.out.println("ACCEPT");
                    break;
                } catch (Exception e) {
                    System.out.println("faild" + e.getMessage());
                    // its really should be empty
                }
            }
        }
        supplier.get();
    }
}
