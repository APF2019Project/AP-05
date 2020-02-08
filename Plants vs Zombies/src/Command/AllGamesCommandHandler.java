package Command;

import Main.Map;
import Main.Menu;
import Main.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AllGamesCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showAllGames, "show all games", ""),
                new Command(this::EnterGame, "enter game", ""),
                new Command(this::endTurn, "end turn", ""),
        };
    }

    public void endTurn(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), this).run();
    }

    public void showAllGames(InputCommand inputCommand) {
        ArrayList<Map> allGames = Map.getAllRunningGame();
        JSONArray jsonArray = new JSONArray();
        for (Map map : allGames) {
            User plantUser=map.getPlantPlayer().getConnection().getUser();
            User zombieUser=map.getZombiePlayer().getConnection().getUser();

            JSONObject gameData=new JSONObject();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", plantUser.getUsername());
            jsonObject.put("imageAddress", plantUser.getImageAddress());
            gameData.put("plant player",jsonObject);
            jsonObject = new JSONObject();
            jsonObject.put("username", zombieUser.getUsername());
            jsonObject.put("imageAddress", zombieUser.getImageAddress());
            gameData.put("zombie player",jsonObject);

            jsonArray.add(gameData);

        }

        menu.getConnection().send("showAllGames", jsonArray);
    }

    public void EnterGame(InputCommand inputCommand) throws Exception {
        String plantUsername = (String) inputCommand.getInputJsonObject().get("firstUsername");
        String zombieUsername = (String) inputCommand.getInputJsonObject().get("secondUsername");
        ArrayList<Map> allGames = Map.getAllRunningGame();
        Map chosenMap=null;
        for(Map map :allGames){
            if(map.getPlantPlayer().getConnection().getUser().getUsername().equals(plantUsername) &&
                    map.getZombiePlayer().getConnection().getUser().getUsername().equals(zombieUsername)){
                chosenMap=map;
                break;
            }
        }
        if(chosenMap==null){
            throw new Exception("game doesn't exist anymore!");
        }

        new Menu(menu.getConnection(), new watchGameCommandHandler(chosenMap)).run();
    }
}
