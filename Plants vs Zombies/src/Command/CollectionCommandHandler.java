package Command;

import Main.CollectionMode;
import Main.Main;
import Main.Player;
import Main.Menu;
public class CollectionCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::play, "play", ""),
                new Command(this::profile, "profile", ""),
                new Command(this::shop, "shop", "")
        };
    }

    public Menu nextMenu;
    public CollectionMode collectionMode;

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