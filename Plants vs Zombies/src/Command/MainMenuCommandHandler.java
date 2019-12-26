package Command;

import Main.Main;
import Main.Menu;
import Player.Player;

public class MainMenuCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::play, "play", "play: To enter play menu and select game type."),
                new Command(this::profile, "profile", "profile: To enter your profile."),
                new Command(this::shop, "shop", "shop: To enter shop.")
        };
    }

    public void play(String command) throws Exception {
        menu.exitAndOpen(new Menu(menu.getUser(),menu,new PlayCommandHandler()));
    }

    public void profile(String command) throws Exception {
        menu.exitAndOpen(new Menu(menu.getUser(),menu,new ProfileCommandHandler()));
    }

    public void shop(String command) throws Exception {
        menu.exitAndOpen(new Menu(menu.getUser(),menu,new ShopCommandHandler()));
    }
}
