package Player;

import Command.CollectionCommandHandler;
import Command.ZombiePlayerCommandHandler;
import Command.CollectionMode;
import Main.Connection;
import Main.Menu;
import Main.User;

public class ZombieHumanPlayer extends ZombiePlayer {
    public ZombieHumanPlayer(Connection connection) {
        super(connection);
    }

    @Override
    public void doAction() throws Exception {
        super.doAction();
        Menu ZombiePlayerMenu = new Menu(connection, new ZombiePlayerCommandHandler());
        ZombiePlayerMenu.run();
    }

    @Override
    public void gameAction() {

    }

    @Override
    public void pickCards() throws Exception {
        Menu collectionMenu = new Menu(connection, new CollectionCommandHandler(CollectionMode.zombiesCollection));
        collectionMenu.run();
    }
}
