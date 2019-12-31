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
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(menu.getUser());
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Day, plantOnDayAndWaterModeHumanPlayer,
                new ZombieAIPlayer(GameData.getAIUser()), GameData.numberOfWavesInDayAndWaterMode);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(map);
        gameMenuSwitcher.runGame();
    }

    public void playWaterMode(InputCommand inputCommand) throws Exception {
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(menu.getUser());
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Water, plantOnDayAndWaterModeHumanPlayer,
                new ZombieAIPlayer(GameData.getAIUser()), GameData.numberOfWavesInDayAndWaterMode);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(map);
        gameMenuSwitcher.runGame();
    }

    public void playRailMode(InputCommand inputCommand) throws Exception {
        PlantOnRailGameModeHumanPlayer plantOnRailGameModeHumanPlayer =
                new PlantOnRailGameModeHumanPlayer(menu.getUser());
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Rail, plantOnRailGameModeHumanPlayer,
                new ZombieAIPlayer(GameData.getAIUser()), GameData.numberOfWavesInRailMode);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(map);
        gameMenuSwitcher.runGame();
    }

    public void playZombieMode(InputCommand inputCommand) throws Exception {
        ZombieHumanPlayer zombieHumanPlayer = new ZombieHumanPlayer(menu.getUser());
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.PvP, new PlantAIPlayer(menu.getUser()),
                zombieHumanPlayer, GameData.numberOfWavesInZombieMode);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(map);
        gameMenuSwitcher.runGame();
    }

    public void playPvPMode(InputCommand inputCommand) throws Exception {
        PlantOnDayAndWaterModeHumanPlayer plantOnDayAndWaterModeHumanPlayer =
                new PlantOnDayAndWaterModeHumanPlayer(menu.getUser());
        String opponentUsername = Main.scanLine();
        int numberOfWaves = Integer.parseInt(Main.scanLine());
        User opponentUser = User.getUserByUsername(opponentUsername);
        ZombieHumanPlayer zombieHumanPlayer =
                new ZombieHumanPlayer(opponentUser);
        Map map = new Map(GameData.mapRowCount, GameData.mapColCount, MapMode.Day, plantOnDayAndWaterModeHumanPlayer,
                zombieHumanPlayer, numberOfWaves);
        GameMenuSwitcher gameMenuSwitcher = new GameMenuSwitcher(map);
        gameMenuSwitcher.runGame();
    }
}