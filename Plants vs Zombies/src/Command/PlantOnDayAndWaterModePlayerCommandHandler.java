package Command;

import Main.ActiveCard;
import Main.GameData;
import Main.Main;
import Objects.Creature;
import Objects.Plant;
import Player.PlantOnDayAndWaterModeHumanPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantOnDayAndWaterModePlayerCommandHandler extends CommandHandler {
    private Supplier<Void> supplier;

    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", "show hand: To see your collection's " +
                        "remaining cooldown and other things"),
                new Command(this::select, "select", "select [Plant Name]: To select a plant"),
                new Command(this::plant, "plant",
                        "plant [row],[column]: To plant your selected plant in the given coordination."),
                new Command(this::remove, "remove",
                        "remove [row],[column]: To remove a plant from given coordination."),
                new Command(this::endTurn, "end turn", "end turn: To end turn."),
                new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining " +
                        "zombies and plants.")
        };
    }


    public PlantOnDayAndWaterModePlayerCommandHandler(Supplier<Void> supplier) {
        this.supplier = supplier;
    }

    public void setFirstLineDescription() {
        firstLineDescription = "Sun in Game: " + menu.getConnection().getUser().getPlayer().getSunInGame();
    }

    private Plant selectedPlant = null;

    void showHand(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getPlayer().getCreaturesOnHand()) {
            Plant plant = (Plant) creature;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", plant.getName());
            jsonObject.put("price", plant.getPrice());
            jsonObject.put("cool down", plant.getCoolDown());
            jsonObject.put("remaining cool down", plant.getRemainingCoolDown() + 1);
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("showHand", jsonArray);
    }

    void select(InputCommand inputCommand) throws Exception {
        String plantName = (String) inputCommand.getInputJsonObject().get("plantName");
        Plant plant = (Plant) menu.getConnection().getUser().getPlayer().getCreatureOnHandByName(plantName);
        if (plant == null) {
            throw new Exception("invalid plant name");
        }
        if (menu.getConnection().getUser().getPlayer().getSunInGame() < plant.getPrice()) {
            throw new Error("you don't have Enough money");
        }
        if(plant.equals(selectedPlant)){
            selectedPlant=null;
            menu.getConnection().send("selectPlant", null);
        }else {
            selectedPlant = plant;
            menu.getConnection().send("selectPlant", selectedPlant.getName().toLowerCase());
        }
    }

    void plant(InputCommand inputCommand) throws Exception {
        if (selectedPlant == null) {
            throw new Exception("you select nothing!");
        }
        int x = ((Long) inputCommand.getInputJsonObject().get("x")).intValue() - 1,
                y = ((Long) inputCommand.getInputJsonObject().get("y")).intValue() - 1;
        ActiveCard activeCard = new ActiveCard(selectedPlant, x, y, menu.getConnection().getUser().getPlayer());
        if (!menu.getConnection().getUser().getPlayer().getMap().canAddActiveCardAndBuy(activeCard)) {
            throw new Exception("you can't your plant here");
        }
        menu.getConnection().getUser().getPlayer().getMap()
                .addActiveCard(new ActiveCard(selectedPlant, x, y, menu.getConnection().getUser().getPlayer()));
        selectedPlant = null;
        menu.getConnection().send("selectPlant", null);
        show();
    }

    void remove(InputCommand inputCommand) throws Exception {
        int x = ((Long) inputCommand.getInputJsonObject().get("x")).intValue() - 1,
                y = ((Long) inputCommand.getInputJsonObject().get("y")).intValue() - 1;
        ActiveCard activeCard = menu.getConnection().getUser().getPlayer().getMap().findPlantIn(x, y);
        if (activeCard == null) {
            throw new Exception("there is no plant here to remove");
        }
        menu.getConnection().getUser().getPlayer().getMap().removeActiveCard(activeCard);
        show();
    }

    void endTurn(InputCommand inputCommand) throws Exception {
        supplier.get();
        //menu.exit();
    }

    void showLawn(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (ActiveCard activeCard : menu.getConnection().getUser().getPlayer().getMap().getActiveCardArrayList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", activeCard.getCreature().getName());
            jsonObject.put("x", activeCard.getX());
            jsonObject.put("y", activeCard.getY());
            jsonObject.put("remaining hp", activeCard.getRemainingHp());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("showLawn", jsonArray);
    }

    void show() throws Exception {
        showHand(null);
        showLawn(null);
    }

}