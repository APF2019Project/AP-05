package Player;

import Command.CollectionCommandHandler;
import Main.Main;
import Main.Menu;
import Main.User;
import Player.Player;

public class PlantPlayer extends Player {
    public PlantPlayer(User user) {
        super(user);
    }

    @Override
    void doAction() {
        super.setMeCurrentPlayer();
    }

    @Override
    void pickCards() {
        super.setMeCurrentPlayer();
        Menu collectionMenu = new Menu(Main.playMenu, new CollectionCommandHandler());
        Player.getCurrentPlayer().setCurrentMenu(collectionMenu);
    }
}
