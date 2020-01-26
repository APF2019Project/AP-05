package Main;

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
        map.getPlantPlayer().pickCards(()-> {
            try {
                map.getZombiePlayer().pickCards(()->{
                    try {
                        while (gameStatus.equals(GameStatus.OnGame)) {
                            Thread.sleep(500);
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
                        if (!gameStatus.equals(GameStatus.notInGame)) {
                            map.getPlantPlayer().getConnection().getUser().gameEnded(gameStatus.equals(GameStatus.PlantPlayerWins));
                            map.getZombiePlayer().getConnection().getUser().gameEnded(gameStatus.equals(GameStatus.ZombiePlayerWins));
                            Main.print(gameStatus.name());
                        }
                        setGameStatus(GameStatus.notInGame);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
