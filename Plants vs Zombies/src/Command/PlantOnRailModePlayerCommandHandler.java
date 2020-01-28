package Command;

import Main.ActiveCard;
import Main.GameData;
import Main.Main;
import Objects.Creature;
import Objects.Plant;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantOnRailModePlayerCommandHandler extends CommandHandler {
    private Supplier<Void> supplier;

    public PlantOnRailModePlayerCommandHandler(Supplier<Void> supplier) {
        this.supplier = supplier;
    }

    {
        this.commands = new Command[]{
                new Command(this::list, "list", "list: To see your current cards."),
                new Command(this::select, "select " + GameData.positiveNumber, "select [number]: To select " +
                        "number'th card."),
                new Command(this::record, "record", "record: To see number of killed zombies."),
                new Command(this::plant, "plant " + GameData.positiveNumber + "," + GameData.positiveNumber,
                        "plant [row],[column]: To plant your selected plant in the given coordination."),
                new Command(this::remove, "remove " + GameData.positiveNumber + "," + GameData.positiveNumber,
                        "remove [row],[column]: To remove a plant from given coordination."),
                new Command(this::endTurn, "end turn", "end turn: To end turn."),
                new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining " +
                        "zombies and plants.")
        };
    }

    private int plantIndex = -1;
    //private Plant selectedPlant = null;

    void list(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getConnection().getUser().getPlayer().getCreaturesOnHand()) {
            Plant plant = (Plant) creature;
            stringBuilder.append("Name: ").append(plant.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    void select(InputCommand inputCommand) throws Exception {
        plantIndex = (int) inputCommand.getInputJsonObject().get("plantIndex"); // input is 0-base
    }

    void record(InputCommand inputCommand) {
        Main.print(String.valueOf(menu.getConnection().getUser().getPlayer().getKillingEnemyCount()));
    }

    void plant(InputCommand inputCommand) throws Exception {
        int x = (int) inputCommand.getInputJsonObject().get("x") - 1, y = (int) inputCommand.getInputJsonObject().get("y") - 1;
        ActiveCard activeCard=new ActiveCard(
                menu.getConnection().getUser().getPlayer().getCreaturesOnHand().get(plantIndex), x, y, menu.getConnection().getUser().getPlayer());
        if (!menu.getConnection().getUser().getPlayer().getMap().canAddActiveCardAndBuy(activeCard)) {
            throw new Exception("you can't your plant here");
        }
        menu.getConnection().getUser().getPlayer().getMap()
                .addActiveCard(activeCard);
        menu.getConnection().getUser().getPlayer().getCreaturesOnHand().remove(plantIndex);
        plantIndex = -1;
    }

    void remove(InputCommand inputCommand) throws Exception {
        int x = (int) inputCommand.getInputJsonObject().get("x") - 1, y = (int) inputCommand.getInputJsonObject().get("y") - 1;
        ActiveCard activeCard = menu.getConnection().getUser().getPlayer().getMap().findPlantIn(x, y);
        if (activeCard == null) {
            throw new Exception("there is no plant here to remove");
        }
        menu.getConnection().getUser().getPlayer().getMap().removeActiveCard(activeCard);
    }

    void endTurn(InputCommand inputCommand) throws Exception {
        menu.exit();
    }

    void showLawn(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ActiveCard activeCard : menu.getConnection().getUser().getPlayer().getMap().getActiveCardArrayList()) {
            stringBuilder.append("\nName: ").append(activeCard.getCreature().getName()).append("\nposition: (")
                    .append(activeCard.getX()).append(',').append(activeCard.getY()).append(")\n remaining Hp:")
                    .append(activeCard.getRemainingHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

}