package Main;

import org.json.simple.JSONObject;

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

    private void runTurn() throws Exception {
        System.out.println("your turn Started");
        map.getPlantPlayer().doAction(() -> {
            try {
                System.out.println("your turn ended");
                if (gameStatus.equals(GameStatus.OnGame)) {
                    map.getZombiePlayer().doAction(null);
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
                if (gameStatus.equals(GameStatus.OnGame)) {
                    runTurn();
                } else {
                    if (!gameStatus.equals(GameStatus.notInGame)) {
                        JSONObject data = new JSONObject();
                        data.put("message", GameData.winMessage);
                        data.put("messageType", "INFORMATION");
                        (gameStatus.equals(GameStatus.PlantPlayerWins) ? map.getPlantPlayer() : map.getZombiePlayer())
                                .getConnection().send("showMessage", data);

                        data = new JSONObject();
                        data.put("message", GameData.loseMessage);
                        data.put("messageType", "INFORMATION");
                        (gameStatus.equals(GameStatus.ZombiePlayerWins) ? map.getPlantPlayer() : map.getZombiePlayer())
                                .getConnection().send("showMessage", data);

                        map.getPlantPlayer().getConnection().popMenu();
                        map.getPlantPlayer().getConnection().popMenu();

                        map.getZombiePlayer().getConnection().popMenu();
                        map.getZombiePlayer().getConnection().popMenu();

                        map.getPlantPlayer().getConnection().getUser().gameEnded(gameStatus.equals(GameStatus.PlantPlayerWins));
                        map.getZombiePlayer().getConnection().getUser().gameEnded(gameStatus.equals(GameStatus.ZombiePlayerWins));
                        Main.print(gameStatus.name());
                    }
                    setGameStatus(GameStatus.notInGame);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public void runGame() throws Exception {
        setGameStatus(GameStatus.OnGame);
        map.getPlantPlayer().pickCards(() -> {
            try {
                map.getZombiePlayer().pickCards(() -> {
                    try {
                        System.out.println("end of picks");
                        runTurn();
                    } catch (Exception e) {
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
