package Command;

import Command.CommandHandler;
import Main.Main;
import Main.Player;
import MainPackage.Main;

public class DayAndWaterGameModeCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::play, "play", ""),
                new Command(this::profile, "profile", ""),
                new Command(this::shop, "shop", "")
        };
    }
    public boolean haveWater;

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
