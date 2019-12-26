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
    public void pickCards() throws Exception {
        Menu collectionMenu = new Menu(Main.playMenu, new CollectionCommandHandler());
    }
}
