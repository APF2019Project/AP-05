package Command;

import Main.Creature;
import Main.GameData;
import Main.Main;
import Main.Plant;
import Main.ActiveCard;

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

    private Plant selectedPlant = null;

    void list(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getUser().getPlayer().getCreaturesOnHand()) {
            Plant plant = (Plant) creature;
            stringBuilder.append(plant.getName()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    void select(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnRailModePlayerCommandHandler select method");
        }
        int plantIndex = Integer.parseInt(matcher.group(1));
        selectedPlant = (Plant) menu.getUser().getPlayer().getCreaturesOnHand().get(plantIndex);
    }

    void record(InputCommand inputCommand) {
        Main.print(String.valueOf(menu.getUser().getPlayer().getKillingEnemyCount()));
    }

    void plant(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnRailModePlayerCommandHandler plant method");
        }
        int x = Integer.parseInt(matcher.group(1)), y = Integer.parseInt(matcher.group(2));
        menu.getUser().getPlayer().getMap()
                .addActiveCard(new ActiveCard(selectedPlant, x, y, menu.getUser().getPlayer()));
        selectedPlant = null;
    }

    void remove(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnRailModePlayerCommandHandler remove method");
        }
        int x = Integer.parseInt(matcher.group(1)), y = Integer.parseInt(matcher.group(2));
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
            stringBuilder.append(activeCard.getCreature().getName()).append(" (")
                    .append(activeCard.getX()).append(',').append(activeCard.getY()).append(")")
                    .append(activeCard.getRemainingHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

}