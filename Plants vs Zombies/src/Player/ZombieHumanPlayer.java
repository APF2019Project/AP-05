package Player;

import Command.CollectionCommandHandler;
import Command.CollectionMode;
import Command.ZombiePlayerCommandHandler;
import Main.Connection;
import Main.Menu;

import java.util.function.Supplier;

public class ZombieHumanPlayer extends ZombiePlayer {
    public ZombieHumanPlayer(Connection connection) {
        super(connection);
    }

    @Override
    public void doAction(Supplier<Void> supplier) throws Exception {
        super.doAction();
        if (!isWaveRunning()) {
            Menu ZombiePlayerMenu = new Menu(connection, new ZombiePlayerCommandHandler(supplier));
            ZombiePlayerMenu.run();
        }
    }
    @Override
    public void doAction(Supplier<Void> supplier, boolean isViewer) throws Exception {
        super.doAction();
        if (!isWaveRunning()) {
            Menu ZombiePlayerMenu = new Menu(connection, new ZombiePlayerCommandHandler(supplier,isViewer));
            ZombiePlayerMenu.run();
        }
    }

    @Override
    public void gameAction() {

    }

    @Override
    public void pickCards(Supplier<Void> supplier) throws Exception {
        Menu collectionMenu = new Menu(connection, new CollectionCommandHandler(CollectionMode.zombiesCollection, supplier));
        collectionMenu.run();
    }
}
