package Command;

import Main.Menu;

public class MainMenuCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::play, "play", "play: To enter play menu and select game type."),
                new Command(this::profile, "profile", "profile: To enter your profile."),
                new Command(this::shop, "shop", "shop: To enter shop."),
                new Command(this::allUsers, "show all users", ""),
        };
    }

    public void allUsers(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), new AllUsersCommandHandler()).run();
    }


    public void play(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), new PlayCommandHandler()).run();
    }

    public void profile(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), new ProfileCommandHandler()).run();
    }

    public void shop(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), new ShopCommandHandler()).run();
    }
}