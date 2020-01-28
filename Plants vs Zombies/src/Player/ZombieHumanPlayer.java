package Player;

import Command.CollectionCommandHandler;
import Command.ZombiePlayerCommandHandler;
import Command.CollectionMode;
import Main.Connection;
import Main.Menu;
import Main.User;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ZombieHumanPlayer extends ZombiePlayer {
    public ZombieHumanPlayer(Connection connection) {
        super(connection);
    }

    @Override
    public void doAction(Supplier<Void> supplier) throws Exception {
        super.doAction();
        Menu ZombiePlayerMenu = new Menu(connection, new ZombiePlayerCommandHandler(supplier));
        ZombiePlayerMenu.run();
    }

    @Override
    public void gameAction() {

    }

    @Override
    public void pickCards(Supplier<Void> supplier) throws Exception {
        Menu collectionMenu = new Menu(connection, new CollectionCommandHandler(CollectionMode.zombiesCollection,supplier));
        collectionMenu.run();
    }
}
