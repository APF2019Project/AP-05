package Command;

import Main.ActiveCard;
import Objects.Creature;
import Objects.GunShot;
import Objects.Plant;
import Objects.Zombie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.function.Supplier;

public class PlantOnRailModePlayerCommandHandler extends CommandHandler {
    boolean shovelSelected = false;
    private Supplier<Void> supplier;
    private int plantIndex = -1;


    public PlantOnRailModePlayerCommandHandler(Supplier<Void> supplier) {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", "showHand: To see your current cards."),
                new Command(this::select, "select", "select [number]: To select " +
                        "number'th card."),
                new Command(this::record, "record", "record: To see number of killed zombies."),
                new Command(this::put, "put",
                        "plant [row],[column]: To plant your selected plant in the given coordination."),
                new Command(this::remove, "remove",
                        "remove [row],[column]: To remove a plant from given coordination."),
                new Command(this::endTurn, "end turn", "end turn: To end turn."),
                new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining " +
                        "zombies and plants.")
        };
        this.supplier = supplier;
    }

    public PlantOnRailModePlayerCommandHandler( Supplier<Void> supplier, boolean isViewer) {
        if(isViewer){
            this.commands = new Command[]{
                    new Command(this::showHand, "show hand", "showHand: To see your current cards."),
                    new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining " +
                            "zombies and plants.")
            };
        }else{
            this.commands = new Command[]{
                    new Command(this::showHand, "show hand", "showHand: To see your current cards."),
                    new Command(this::select, "select", "select [number]: To select " +
                            "number'th card."),
                    new Command(this::record, "record", "record: To see number of killed zombies."),
                    new Command(this::put, "put",
                            "plant [row],[column]: To plant your selected plant in the given coordination."),
                    new Command(this::remove, "remove",
                            "remove [row],[column]: To remove a plant from given coordination."),
                    new Command(this::endTurn, "end turn", "end turn: To end turn."),
                    new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining " +
                            "zombies and plants.")
            };
        }
        this.supplier = supplier;
    }

//private Plant selectedPlant = null;

    void showHand(InputCommand inputCommand) {
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getPlayer().getCreaturesOnHand()) {
            Plant plant = (Plant) creature;
            jsonArray.add(plant.getName());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cards", jsonArray);
        jsonObject.put("record", menu.getConnection().getUser().getPlayer().getKillingEnemyCount());
        menu.getConnection().send("showHand", jsonObject);
    }

    void select(InputCommand inputCommand) {
        plantIndex = ((Long) inputCommand.getInputJsonObject().get("plantIndex")).intValue(); // input is 0-base
        menu.getConnection().send("selectCreature", plantIndex);
    }

    //not used
    void record(InputCommand inputCommand) {
        menu.getConnection().send("record", String.valueOf(menu.getConnection().getUser().getPlayer()
                .getKillingEnemyCount()));
    }

    void put(InputCommand inputCommand) throws Exception {
        System.err.println(inputCommand.getInputJsonObject());
        if (plantIndex == 10) {
            remove(inputCommand);
            return;
        }
        int x = ((Long) inputCommand.getInputJsonObject().get("x")).intValue() - 1;
        int y = ((Long) inputCommand.getInputJsonObject().get("y")).intValue() - 1;
        ActiveCard activeCard = new ActiveCard(menu.getConnection().getUser().getPlayer().
                getCreaturesOnHand().get(plantIndex), x, y, menu.getConnection().getUser().getPlayer());
        if (!menu.getConnection().getUser().getPlayer().getMap().canAddActiveCardAndBuy(activeCard)) {
            throw new Exception("you can't put your plant here");
        }
        menu.getConnection().getUser().getPlayer().getMap()
                .addActiveCard(activeCard);
        menu.getConnection().getUser().getPlayer().getCreaturesOnHand().remove(plantIndex);
        plantIndex = -1;
        menu.getConnection().send("selectCreature", plantIndex);
    }

    void remove(InputCommand inputCommand) throws Exception {
        int x = ((Long) inputCommand.getInputJsonObject().get("x")).intValue() - 1,
                y = ((Long) inputCommand.getInputJsonObject().get("y")).intValue() - 1;
        ActiveCard activeCard = menu.getConnection().getUser().getPlayer().getMap().findPlantIn(x, y);
        if (activeCard == null) {
            throw new Exception("there is no plant here to remove");
        }
        menu.getConnection().getUser().getPlayer().getMap().removeActiveCard(activeCard);
        menu.getConnection().send("selectCreature", -1);
        show();
    }

    void endTurn(InputCommand inputCommand) throws Exception {
        supplier.get();
        //    menu.exit();
    }

    void showLawn(InputCommand inputCommand) {
        JSONArray jsonArray = new JSONArray();
        for (ActiveCard activeCard : menu.getConnection().getUser().getPlayer().getMap().getActiveCardArrayList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", activeCard.getCreature().getName());
            jsonObject.put("type", (activeCard.getCreature() instanceof Zombie) ? "Zombie" : "Plant");
            jsonObject.put("x", activeCard.getX());
            jsonObject.put("y", activeCard.getY());
            jsonObject.put("remaining hp", activeCard.getRemainingHp() + activeCard.getShieldRemainingHp());
            jsonObject.put("full hp", activeCard.getCreature().getFullHpWithShield());
            if (activeCard.getCreature() instanceof Zombie) {
                jsonObject.put("speed", ((Zombie) activeCard.getCreature()).getSpeed());
            }
            jsonArray.add(jsonObject);
        }
        for (GunShot gunShot : menu.getConnection().getUser().getPlayer().getMap().getGunShotArrayList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", gunShot.getGun().getName());
            jsonObject.put("type", "GunShot");
            jsonObject.put("x", gunShot.getX());
            jsonObject.put("y", gunShot.getY());
            jsonObject.put("speed", gunShot.getSignedVx());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("showLawn", jsonArray);
    }

    void show() throws Exception {
        showHand(null);
        showLawn(null);
    }
}