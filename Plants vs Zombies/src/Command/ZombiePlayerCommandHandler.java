package Command;

import Main.ActiveCard;
import Main.GameData;
import Main.Main;
import Objects.Creature;
import Objects.GunShot;
import Objects.Zombie;
import Player.ZombiePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZombiePlayerCommandHandler extends CommandHandler {
    private Supplier<Void> supplier;
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

    public ZombiePlayerCommandHandler(Supplier<Void> supplier) {
        this.supplier = supplier;
    }

    void showHand(InputCommand inputCommand) {
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : menu.getConnection().getUser().getPlayer().getCreaturesOnHand()) {
            Zombie zombie = (Zombie) creature;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", zombie.getName());
            jsonObject.put("price", zombie.getPrice());
            jsonObject.put("cool down", zombie.getCoolDown());
            jsonObject.put("remaining cool down", zombie.getRemainingCoolDown());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sun", menu.getConnection().getUser().getPlayer().getSunInGame());
        jsonObject.put("cards", jsonArray);
        menu.getConnection().send("showHand", jsonObject);
    }

    void showLanes(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (ActiveCard activeCard : menu.getConnection().getUser().getPlayer().getMap().getActiveCardArrayList()) {
            if (activeCard.getCreature() instanceof Zombie) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", activeCard.getCreature().getName());
                jsonObject.put("y", activeCard.getY());
                jsonArray.add(jsonObject);
            }
        }
        menu.getConnection().send("showLanes", jsonArray);
    }

    void put(InputCommand inputCommand) throws Exception {
        String zombieName = (String) inputCommand.getInputJsonObject().get("zombieName");
        int zombieCount = (int) inputCommand.getInputJsonObject().getOrDefault("zombieCount", 1);
        int y = (int) inputCommand.getInputJsonObject().get("y") - 1;
        Zombie zombie = (Zombie) menu.getConnection().getUser().getPlayer().getCreatureOnHandByName(zombieName);
        for (int i = 0; i < zombieCount; i++) {
            menu.getConnection().getUser().getPlayer().getMap().addActiveCard(new ActiveCard(zombie, GameData.mapColCount, y, menu.getConnection().getUser().getPlayer()));
        }
        menu.getConnection().send("showLog","put successful");
    }

    void start(InputCommand inputCommand) throws Exception {
        ((ZombiePlayer) menu.getConnection().getUser().getPlayer()).startWave();
        menu.getConnection().send("showLog","Wave Started");
    }

    void endTurn(InputCommand inputCommand) throws Exception {
        menu.exit();
    }


    void showLawn(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (ActiveCard activeCard : menu.getConnection().getUser().getPlayer().getMap().getActiveCardArrayList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", activeCard.getCreature().getName());
            jsonObject.put("type", (activeCard.getCreature() instanceof Zombie) ? "Zombie" : "Plant");
            jsonObject.put("x", activeCard.getX());
            jsonObject.put("y", activeCard.getY());
            jsonObject.put("remaining hp", activeCard.getRemainingHp());
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
            jsonObject.put("speed", gunShot.getVx());
            jsonArray.add(jsonObject);
        }
        menu.getConnection().send("showLawn", jsonArray);
    }
}