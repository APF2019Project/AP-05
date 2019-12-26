package Command;

import Main.*;
import Player.*;

public class PlayCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::playDayMode, "day", "day: To enter \"day\" type game."),
                new Command(this::playWaterMode, "water", "water: To enter \"water\" type game."),
                new Command(this::playRailMode, "rail", "rail: To enter \"rail\" type game."),
                new Command(this::playZombieMode, "zombie", "zombie: To enter \"zombie\" type game."),
                new Command(this::playPvPMode, "pvp", "pvp: To enter multiPlayer type game."),
        };
    }

    public void playDayMode(String command) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Day);
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(map);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnDayAndWaterModeHumanPlayer, new ZombieAIPlayer(map));
        gameMenuSwitcher.runGame();
    }

    public void playWaterMode(String command) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount,MapMode.Water);
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(map);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnDayAndWaterModeHumanPlayer, new ZombieAIPlayer(map));
        gameMenuSwitcher.runGame();
    }

    public void playRailMode(String command) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount,MapMode.Water);
        PlantOnRailGameModeHumanPlayer plantOnRailGameModeHumanPlayer =
                new PlantOnRailGameModeHumanPlayer(map);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnRailGameModeHumanPlayer, new ZombieAIPlayer(map));
        gameMenuSwitcher.runGame();
    }

    public void playZombieMode(String command) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Day);
        ZombieHumanPlayer zombieHumanPlayer =
                new ZombieHumanPlayer(map);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(new PlantAIPlayer(map), zombieHumanPlayer);
        gameMenuSwitcher.runGame();
    }

    public void playPvPMode(String command) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Day);
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(map);
        ZombieHumanPlayer zombieHumanPlayer =
                new ZombieHumanPlayer(map);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnDayAndWaterModeHumanPlayer, zombieHumanPlayer);
        gameMenuSwitcher.runGame();
    }
}
