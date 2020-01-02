package Command;

import Main.*;
import Player.Player;

import java.security.spec.ECField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantOnDayAndWaterModePlayerCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", "show hand: To see your collection's " +
                        "remaining cooldown and other things"),
                new Command(this::select, "select (.+)", "select [Plant Name]: To select a plant"),
                new Command(this::plant, "plant " + GameData.positiveNumber + "," + GameData.positiveNumber,
                        "plant [row],[column]: To plant your selected plant in the given coordination."),
                new Command(this::remove, "remove " + GameData.positiveNumber + "," + GameData.positiveNumber,
                        "remove [row],[column]: To remove a plant from given coordination."),
                new Command(this::endTurn, "end turn", "end turn: To end turn."),
                new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining " +
                        "zombies and plants.")
        };
    }

    public void setFirstLineDescription() {
        firstLineDescription = "Sun in Game: " + menu.getUser().getPlayer().getSunInGame();
    }

    private Plant selectedPlant = null;

    void showHand(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getUser().getPlayer().getCreaturesOnHand()) {
            Plant plant = (Plant) creature;
            stringBuilder.append("\nName: ").append(plant.getName()).append("\nprice: ")
                    .append(plant.getPrice()).append("\nremaining cool down: ")
                    .append(plant.getRemainingCoolDown()).append("\n");
        }
        Main.print(stringBuilder.toString());
    }

    void select(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnDayAndWaterModePlayerCommandHandler select method");
        }
        String plantName = matcher.group(1);
        Plant plant = (Plant) menu.getUser().getPlayer().getCreatureOnHandByName(plantName);
        if (plant == null) {
            throw new Exception("invalid plant name");
        }
        if (menu.getUser().getPlayer().getSunInGame() < plant.getPrice()) {
            throw new Error("you don't have Enough money");
        }
        selectedPlant = plant;
    }

    void plant(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnDayAndWaterModePlayerCommandHandler plant method");
        }
        if (selectedPlant == null) {
            throw new Exception("you select nothing!");
        }
        int x = Integer.parseInt(matcher.group(1)) - 1, y = Integer.parseInt(matcher.group(2)) - 1;
        ActiveCard activeCard = new ActiveCard(selectedPlant, x, y, menu.getUser().getPlayer());
        if (!menu.getUser().getPlayer().getMap().canAddActiveCardAndBuy(activeCard)) {
            throw new Exception("you can't your plant here");
        }
        menu.getUser().getPlayer().getMap()
                .addActiveCard(new ActiveCard(selectedPlant, x, y, menu.getUser().getPlayer()));
        selectedPlant = null;
    }

    void remove(InputCommand inputCommand) throws Exception {
        Matcher matcher = Pattern.compile(inputCommand.getCommand().getRegex()).matcher(inputCommand.getInput());
        if (!matcher.find()) {
            throw new Exception("there are some bug in PlantOnDayAndWaterModePlayerCommandHandler remove method");
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
                    .append(activeCard.getX()).append(',').append(activeCard.getY()).append(")\nremaining Hp:")
                    .append(activeCard.getRemainingHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

}