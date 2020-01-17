package Command;

import Main.ActiveCard;
import Main.GameData;
import Main.Main;
import Objects.Creature;
import Objects.Plant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantOnRailModePlayerCommandHandler extends CommandHandler {
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
        for (Creature creature : menu.getUser().getPlayer().getCreaturesOnHand()) {
            Plant plant = (Plant) creature;
            stringBuilder.append("Name: ").append(plant.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    void select(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnRailModePlayerCommandHandler select method");
        }
        plantIndex = Integer.parseInt(matcher.group(1)); // input is 0-base
    }

    void record(InputCommand inputCommand) {
        Main.print(String.valueOf(menu.getUser().getPlayer().getKillingEnemyCount()));
    }

    void plant(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnRailModePlayerCommandHandler plant method");
        }
        int x = Integer.parseInt(matcher.group(1)) - 1, y = Integer.parseInt(matcher.group(2)) - 1;
        ActiveCard activeCard=new ActiveCard(
                menu.getUser().getPlayer().getCreaturesOnHand().get(plantIndex), x, y, menu.getUser().getPlayer());
        if (!menu.getUser().getPlayer().getMap().canAddActiveCardAndBuy(activeCard)) {
            throw new Exception("you can't your plant here");
        }
        menu.getUser().getPlayer().getMap()
                .addActiveCard(activeCard);
        menu.getUser().getPlayer().getCreaturesOnHand().remove(plantIndex);
        plantIndex = -1;
    }

    void remove(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnRailModePlayerCommandHandler remove method");
        }
        int x = Integer.parseInt(matcher.group(1)) - 1, y = Integer.parseInt(matcher.group(2)) - 1;
        ActiveCard activeCard = menu.getUser().getPlayer().getMap().findPlantIn(x, y);
        if (activeCard == null) {
            throw new Exception("there is no plant here to remove");
        }
        menu.getUser().getPlayer().getMap().removeActiveCard(activeCard);
    }

    void endTurn(InputCommand inputCommand) {
        menu.exit();
    }

    void showLawn(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ActiveCard activeCard : menu.getUser().getPlayer().getMap().getActiveCardArrayList()) {
            stringBuilder.append("\nName: ").append(activeCard.getCreature().getName()).append("\nposition: (")
                    .append(activeCard.getX()).append(',').append(activeCard.getY()).append(")\n remaining Hp:")
                    .append(activeCard.getRemainingHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

}