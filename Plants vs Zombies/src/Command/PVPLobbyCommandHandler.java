package Command;

import Main.*;
import Player.PlantOnDayAndWaterModeHumanPlayer;
import Player.ZombieHumanPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PVPLobbyCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showInLobbyUsers, "showInLobbyUsers", ""),
                new Command(this::enterGame, "enter game", ""),
                new Command(this::endTurn, "end turn", ""),
        };
    }

    public void endTurn(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), this).run();
    }
    public void showInLobbyUsers(InputCommand inputCommand) throws Exception {
        JSONArray jsonArray = new JSONArray();
        HashSet<User> allOnlineUser= new HashSet<User>();
        for (Connection connection : Connection.getAllConnection()) {
            User user = connection.getUser();
            if (user != null && connection.getCurrentMenu().getCommandHandlerName().equals(this.getClass().getSimpleName())) {
                allOnlineUser.add(user);
            }
        }
        for(User user:allOnlineUser){
            if(user!=this.menu.getConnection().getUser()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", user.getUsername());
                jsonObject.put("imageAddress", user.getImageAddress());
                jsonArray.add(jsonObject);
            }
        }
        menu.getConnection().send("showInLobbyUsers", jsonArray);
    }
    public void enterGame(InputCommand inputCommand) throws Exception {
        User opponentUser=User.getUserByUsername ((String) inputCommand.getInputJsonObject().get("enemy username"));
        if (opponentUser == null) {
            throw new Exception("opponent not found!");
        }

        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(menu.getConnection());
        int numberOfWaves = 5;

        ZombieHumanPlayer zombieHumanPlayer =
                new ZombieHumanPlayer(Server.getConnectionByUsername(opponentUser.getUsername()));
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.PvP, plantOnDayAndWaterModeHumanPlayer,
                zombieHumanPlayer, numberOfWaves);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(map);
        gameMenuSwitcher.runGame();
    }
}
