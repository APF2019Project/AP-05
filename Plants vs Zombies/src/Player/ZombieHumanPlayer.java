package Player;

import Command.CollectionCommandHandler;
import Command.PlantOnRailModePlayerCommandHandler;
import Command.ZombiePlayerCommandHandler;
import Main.*;

public class ZombieHumanPlayer extends ZombiePlayer {
    public ZombieHumanPlayer(User user) {
        super(user);
    }

    @Override
    public void doAction() throws Exception {
        super.doAction();
        Menu ZombiePlayerMenu = new Menu(user, new ZombiePlayerCommandHandler());
        ZombiePlayerMenu.run();
    }

    @Override
    public void gameAction() {

    }

    @Override
    public void pickCards() throws Exception {
        Menu collectionMenu = new Menu(user, new CollectionCommandHandler(CollectionMode.zombiesCollection));
        collectionMenu.run();
    }
}
