package Player;

import Main.Map;

public abstract class PlantPlayer extends Player {
    public PlantPlayer(Map map) {
        super(map);
    }

    @Override
    public void doAction() {
    }

    abstract public void pickCards() throws Exception;
}
