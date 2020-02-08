package Command;

import Main.ActiveCard;
import Main.Map;
import Main.Menu;
import Objects.Creature;
import Objects.GunShot;
import Objects.Plant;
import Objects.Zombie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class watchGameCommandHandler extends CommandHandler  {
    private Map map;
    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", "show hand: To see your collection's " +
                        "remaining cooldown and other things"),
                new Command(this::showLawn, "show lawn", "show lawn: To see list of remaining " +
                        "zombies and plants."),
                new Command(this::endTurn, "end turn", ""),
        };
    }
    watchGameCommandHandler(Map map){
        this.map=map;
        System.out.println("ma omadim injaa :))");
    }

    public void endTurn(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), this).run();
    }
    void showLawn(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (ActiveCard activeCard : map.getActiveCardArrayList()) {
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
        for (GunShot gunShot : map.getGunShotArrayList()) {
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
    void showHand(InputCommand inputCommand) throws Exception {
        JSONObject sendingJSONObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Creature creature : map.getPlantPlayer().getCreaturesOnHand()) {
            Plant plant = (Plant) creature;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", plant.getName());
            jsonObject.put("price", plant.getPrice());
            jsonObject.put("cool down", plant.getCoolDown());
            jsonObject.put("remaining cool down", plant.getRemainingCoolDown());
            jsonArray.add(jsonObject);
        }
        sendingJSONObject.put("hasWater", map.hasWater());
        sendingJSONObject.put("sun", map.getPlantPlayer().getSunInGame());
        sendingJSONObject.put("cards", jsonArray);
        menu.getConnection().send("showHand", sendingJSONObject);
    }
}
