package Command;

import Main.GameData;
import Main.MapMode;
import Main.Menu;
import Player.Player;

public class DayAndWaterGameModeCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::showHand, "show hand", ""),
                new Command(this::select, "select (.+)", ""),
                new Command(this::plant, "plant "+ GameData.positiveNumber+","+GameData.positiveNumber, "")
        };
    }
    private MapMode mapMode;
    DayAndWaterGameModeCommandHandler(MapMode mapMode){
        super();
        this.mapMode=mapMode;
    }

    void showHand(String command){

    }
    void select(String command){

    }
    void plant(String command){

    }

}
