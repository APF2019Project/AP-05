package Player;

import Main.Connection;
import Main.User;

public abstract class PlantPlayer extends Player {
    public PlantPlayer(Connection connection) {
        super(connection);
        setSunInGame(2);
    }
}
