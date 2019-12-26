package Player;

import Command.CollectionCommandHandler;
import Main.Main;
import Main.Menu;
import Main.Map;

public class PlantOnDayAndWaterModeHumanPlayer extends PlantPlayer{
    public PlantOnDayAndWaterModeHumanPlayer(Map map) {
        super(map);
    }

    @Override
    public void doAction() {

    }

    @Override
    public void gameAction() {

    }

    @Override
    public void pickCards() throws Exception {
        Menu collectionMenu = new Menu(Menu.getCurrentMenu(), new CollectionCommandHandler());
        collectionMenu.run();
    }
}
