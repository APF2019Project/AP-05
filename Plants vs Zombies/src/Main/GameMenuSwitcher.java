package Main;

import Player.PlantPlayer;
import Player.ZombiePlayer;
import Player.Player;

public class GameMenuSwitcher {
    private Map map;
    private static GameStatus gameStatus = GameStatus.notInGame;

    public GameMenuSwitcher(Map map) {
        this.map = map;
    }

    public static GameStatus getGameStatus() {
        return gameStatus;
    }

    public static void setGameStatus(GameStatus gameStatus) {
        GameMenuSwitcher.gameStatus = gameStatus;
    }

    public void runGame() throws Exception {
        setGameStatus(GameStatus.OnGame);
        System.err.println("DE0");
        map.getPlantPlayer().pickCards();
        System.err.println("DE1");
        map.getZombiePlayer().pickCards();
        System.err.println("DE");
        while (gameStatus.equals(GameStatus.OnGame)) {
            map.getPlantPlayer().doAction();
            if (gameStatus.equals(GameStatus.OnGame)) {
                map.getZombiePlayer().doAction();
            }
            if (gameStatus.equals(GameStatus.OnGame)) {
                gameStatus = map.run();
            }
            if (gameStatus.equals(GameStatus.OnGame)) {
                map.getPlantPlayer().gameAction();
            }
            if (gameStatus.equals(GameStatus.OnGame)) {
                map.getZombiePlayer().gameAction();
            }
        }
        if(!gameStatus.equals(GameStatus.notInGame)) {
            map.getPlantPlayer().getUser().gameEnded(gameStatus.equals(GameStatus.PlantPlayerWins));
            map.getZombiePlayer().getUser().gameEnded(gameStatus.equals(GameStatus.ZombiePlayerWins));
            Main.print(gameStatus.name());
        }
        setGameStatus(GameStatus.notInGame);
    }
}
