package Command;

import Main.Main;
import Main.Player;

public class DayAndWaterGameModeCommandHandler extends CommandHandler {
    public boolean haveWater;

    {
        this.commands = new Command[]{
                new Command(this::play, "play", ""),
                new Command(this::profile, "profile", ""),
                new Command(this::shop, "shop", "")
        };
    }

    public void play(String command) throws Exception {
        Player.getCurrentPlayer().setCurrentMenu(Main.playMenu);
    }

    public void profile(String command) throws Exception {
        Player.getCurrentPlayer().setCurrentMenu(Main.profileMenu);
    }

    public void shop(String command) throws Exception {
        Player.getCurrentPlayer().setCurrentMenu(Main.shopMenu);
    }
}
