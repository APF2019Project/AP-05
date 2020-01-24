package Command;

import Main.ActiveCard;
import Main.GameData;
import Main.Main;
import Objects.Creature;
import Objects.Zombie;
import Player.ZombiePlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZombiePlayerCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", "show hand: To see selected cards."),
                new Command(this::showLanes, "show lanes", "show lanes: To see " +
                        "rows and zombies to go there."),
                new Command(this::put, "put (.+)," + GameData.positiveNumber + "," + GameData.positiveNumber,
                        "put [name],[number of zombies to put],[row]: to put zombies."),
                new Command(this::start, "start", "start: To start current wave."),
                new Command(this::endTurn, "end turn", "end turn: To end turn."),
                new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining \n" +
                        "zombies and plants"),
        };
    }

    void showHand(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getConnection().getUser().getPlayer().getCreaturesOnHand()) {
            Zombie zombie = (Zombie) creature;
            stringBuilder.append(zombie.getName()).append(" full Hp: ").append(zombie.getFullHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    void showLanes(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ActiveCard activeCard : menu.getConnection().getUser().getPlayer().getMap().getActiveCardArrayList()) {
            if (activeCard.getCreature() instanceof Zombie) {
                stringBuilder.append(activeCard.getCreature().getName()).append(" in line:")
                        .append(activeCard.getY()).append('\n');
            }
        }
        Main.print(stringBuilder.toString());
    }

    void put(InputCommand inputCommand) throws Exception {
        String zombieName = (String) inputCommand.getInputJsonObject().get("zombieName");
        int zombieCount = (int) inputCommand.getInputJsonObject().get("zombieCount");
        int y = (int) inputCommand.getInputJsonObject().get("y") - 1;
        Zombie zombie = (Zombie) menu.getConnection().getUser().getPlayer().getCreatureOnHandByName(zombieName);
        for (int i = 0; i < zombieCount; i++) {
            menu.getConnection().getUser().getPlayer().getMap().addActiveCard(new ActiveCard(zombie, GameData.mapColCount, y, menu.getConnection().getUser().getPlayer()));
        }
    }

    void start(InputCommand inputCommand) throws Exception {
        ((ZombiePlayer) menu.getConnection().getUser().getPlayer()).startWave();
        Main.print("Wave Started");
        //menu.exit();
    }

    void endTurn(InputCommand inputCommand) {
        menu.exit();
    }


    void showLawn(InputCommand inputCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ActiveCard activeCard : menu.getConnection().getUser().getPlayer().getMap().getActiveCardArrayList()) {
            stringBuilder.append(activeCard.getCreature().getName()).append(" (")
                    .append(activeCard.getX()).append(',').append(activeCard.getY()).append(")")
                    .append(activeCard.getRemainingHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }
}