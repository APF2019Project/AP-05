package Player;

import Command.CollectionCommandHandler;
import Command.PlantOnDayAndWaterModePlayerCommandHandler;
import Command.PlantOnRailModePlayerCommandHandler;
import Main.*;

public class PlantOnRailGameModeHumanPlayer extends PlantPlayer {
    public PlantOnRailGameModeHumanPlayer(User user) {
        super(user);
    }

    @Override
    public void doAction() throws Exception {
        Menu PlantOnRailModePlayerMenu = new Menu(user, new PlantOnRailModePlayerCommandHandler());
        PlantOnRailModePlayerMenu.run();
    }

    @Override
    public void gameAction() {

    }

    @Override
    public boolean pickCreature(Creature creature) throws Exception {
        return true;
    }

    @Override
    public void pickCards() throws Exception {
        //nothing
    }
}
