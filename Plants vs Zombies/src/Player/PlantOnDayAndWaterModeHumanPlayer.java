package Player;

import Command.CollectionCommandHandler;
import Main.Main;
import Main.Menu;
import Main.Map;
import Main.User;

public class PlantOnDayAndWaterModeHumanPlayer extends PlantPlayer {
    public PlantOnDayAndWaterModeHumanPlayer(Map map, User user) {
        super(map, user);
    }

    @Override
    public void doAction() {

    }

    @Override
    public void gameAction() {

    }

    @Override
    public void pickCards() throws Exception {
        Menu collectionMenu = new Menu(Menu.getCurrentMenu().getUser(), Menu.getCurrentMenu(), new CollectionCommandHandler());
        collectionMenu.run();
    }
}
