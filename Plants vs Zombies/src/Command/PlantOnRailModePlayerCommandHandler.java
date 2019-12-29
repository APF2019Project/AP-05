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
                new Command(this::list, "list", ""),
                new Command(this::select, "select " + GameData.positiveNumber, ""),
                new Command(this::record, "record", ""),
                new Command(this::plant, "plant " + GameData.positiveNumber + "," + GameData.positiveNumber,
                        ""),
                new Command(this::remove, "remove " + GameData.positiveNumber + "," + GameData.positiveNumber,
                        ""),
                new Command(this::endTurn, "end turn", ""),
                new Command(this::showLawn, "show lawn", ""),
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

    void select(InputCommand inputCommand) {
        int plantIndex = Integer.parseInt(Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput()).group(1));
        selectedPlant = (Plant) menu.getUser().getPlayer().getCreaturesOnHand().get(plantIndex);
    }

    void record(InputCommand inputCommand) {
        Main.print(String.valueOf(menu.getUser().getPlayer().getKillingEnemyCount()));
    }

    void plant(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex())
                .matcher(inputCommand.getInput());
        int x = Integer.parseInt(matcher.group(1)), y = Integer.parseInt(matcher.group(2));
        menu.getUser().getPlayer().getMap().addActiveCard(new ActiveCard(selectedPlant, x, y, menu.getUser().getPlayer()));
        selectedPlant = null;
    }

    void remove(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex())
                .matcher(inputCommand.getInput());
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