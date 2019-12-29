package Command;

import Main.Creature;
import Main.GameData;
import Main.Main;
import Main.Plant;
import Main.ActiveCard;
import Main.Zombie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZombiePlayerCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", ""),
                new Command(this::showLanes, "show lanes", ""),
                new Command(this::put, "put (.+)," + GameData.positiveNumber + "," + GameData.positiveNumber, ""),
                new Command(this::start, "record", ""),
                new Command(this::endTurn, "end turn", ""),
        };
    }

    void showHand(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Creature creature : menu.getUser().getPlayer().getCreaturesOnHand()) {
            Zombie zombie = (Zombie) creature;
            stringBuilder.append(zombie.getName()).append(" full Hp: ").append(zombie.getFullHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }

    void showLanes(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ActiveCard activeCard : menu.getUser().getPlayer().getMap().getActiveCardArrayList()) {
            if (activeCard.getCreature() instanceof Zombie) {
                stringBuilder.append(activeCard.getCreature().getName()).append(" in line:")
                        .append(activeCard.getY()).append('\n');
            }
        }
        Main.print(stringBuilder.toString());
    }

    void put(String command) throws Exception {
        Matcher matcher = Pattern.compile("put (.+)," + GameData.positiveNumber + "," + GameData.positiveNumber).matcher(command);
        String zombieName = matcher.group(1);
        int zombieCount = Integer.parseInt(matcher.group(2));
        int y = Integer.parseInt(matcher.group(3));
        Zombie zombie = (Zombie) menu.getUser().getPlayer().getCreatureOnHandByName(zombieName);
        for (int i = 0; i < zombieCount; i++) {
            menu.getUser().getPlayer().getMap().addActiveCard(new ActiveCard(zombie, GameData.mapRowCount, y, menu.getUser().getPlayer()));
        }
    }

    void start(String command) {
        menu.getUser().getPlayer().
        menu.exit();
    }

    void endTurn(String command) {
        menu.exit();
    }


    void showLawn(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ActiveCard activeCard : menu.getUser().getPlayer().getMap().getActiveCardArrayList()) {
            stringBuilder.append(activeCard.getCreature().getName()).append(" (")
                    .append(activeCard.getX()).append(',').append(activeCard.getY()).append(")")
                    .append(activeCard.getRemainingHp()).append('\n');
        }
        Main.print(stringBuilder.toString());
    }
}