package Player;

import Main.Connection;

public abstract class PlantPlayer extends Player {
    public PlantPlayer(Connection connection) {
        super(connection);
        setSunInGame(2);
    }
}
