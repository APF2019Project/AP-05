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

    public void playDayMode(InputCommand inputCommand) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Day);
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(map, menu.getUser());
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnDayAndWaterModeHumanPlayer, new ZombieAIPlayer(map,GameData.getAIUser()));
        gameMenuSwitcher.runGame();
    }

    public void playWaterMode(InputCommand inputCommand) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Water);
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(map, menu.getUser());
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnDayAndWaterModeHumanPlayer, new ZombieAIPlayer(map,GameData.getAIUser()));
        gameMenuSwitcher.runGame();
    }

    public void playRailMode(InputCommand inputCommand) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Rail);
        PlantOnRailGameModeHumanPlayer plantOnRailGameModeHumanPlayer =
                new PlantOnRailGameModeHumanPlayer(map, menu.getUser());
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnRailGameModeHumanPlayer, new ZombieAIPlayer(map,GameData.getAIUser()));
        gameMenuSwitcher.runGame();
    }

    public void playZombieMode(InputCommand inputCommand) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.PvP);
        ZombieHumanPlayer zombieHumanPlayer =
                new ZombieHumanPlayer(map, menu.getUser());
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(new PlantAIPlayer(map,menu.getUser()), zombieHumanPlayer);
        gameMenuSwitcher.runGame();
    }

    public void playPvPMode(InputCommand inputCommand) throws Exception {
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Day);
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(map, menu.getUser());
        String opponentUsername = Main.scanLine();
        int numberOfWaves = Integer.parseInt(Main.scanLine());
        User opponentUser = User.getUserByUsername(opponentUsername);
        ZombieHumanPlayer zombieHumanPlayer =
                new ZombieHumanPlayer(map, opponentUser);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(plantOnDayAndWaterModeHumanPlayer, zombieHumanPlayer, numberOfWaves);
        gameMenuSwitcher.runGame();
    }
}
