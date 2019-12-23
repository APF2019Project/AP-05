import java.util.Comparator;

public class PlayCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::playDayMode, "day", ""),
                new Command(this::playWaterMode, "water", ""),
                new Command(this::playRailMode, "rail", ""),
                new Command(this::playZombieMode, "zombie", ""),
                new Command(this::playPvPMode, "pvp", ""),
        };
    }

    public void playDayMode(String command) {
        Menu collectionMenu=new Menu(Main.playMenu, new CollectionCommandHandler());
        CollectionCommandHandler collectionCommandHandler =
                (CollectionCommandHandler) collectionMenu.getCommandHandler();
        collectionCommandHandler.collectionMode = CollectionMode.plantsCollection;
        collectionCommandHandler.nextMenu = Main.dayAndWaterGameModeMenu;

        DayAndWaterGameModeCommandHandler dayAndWaterGameModeCommandHandler =
                (DayAndWaterGameModeCommandHandler) Main.dayAndWaterGameModeMenu.getCommandHandler();
        dayAndWaterGameModeCommandHandler.haveWater = false;
        Player.getCurrentPlayer().setCurrentMenu(collectionMenu);
    }

    public void playWaterMode(String command) {
        Menu collectionMenu=new Menu(Main.playMenu, new CollectionCommandHandler());
        CollectionCommandHandler collectionCommandHandler =
                (CollectionCommandHandler) collectionMenu.getCommandHandler();
        collectionCommandHandler.collectionMode = CollectionMode.plantsCollection;
        collectionCommandHandler.nextMenu = Main.dayAndWaterGameModeMenu;

        DayAndWaterGameModeCommandHandler dayAndWaterGameModeCommandHandler =
                (DayAndWaterGameModeCommandHandler) Main.dayAndWaterGameModeMenu.getCommandHandler();
        dayAndWaterGameModeCommandHandler.haveWater = true;
        Player.getCurrentPlayer().setCurrentMenu(collectionMenu);
    }

    public void playRailMode(String command) {
        Player.getCurrentPlayer().setCurrentMenu(Main.railGameModeMenu);
    }

    public void playZombieMode(String command) {
        Menu collectionMenu=new Menu(Main.playMenu, new CollectionCommandHandler());
        CollectionCommandHandler collectionCommandHandler =
                (CollectionCommandHandler) collectionMenu.getCommandHandler();
        collectionCommandHandler.collectionMode = CollectionMode.zombiesCollection;
        collectionCommandHandler.nextMenu = Main.zombieGameModeMenu;
        Player.getCurrentPlayer().setCurrentMenu(collectionMenu);
    }

    public void playPvPMode(String command) throws Exception {
        String username = Main.scanLine();
        User opponentUser=User.getUserByUsername(username);
        if(opponentUser==null){
            throw new Exception("invalid username");
        }
        ZombiePlayer opponentPlayer=new ZombiePlayer(opponentUser);

        PvPGameModeCommandHandler pvpGameModeCommandHandler =
                (PvPGameModeCommandHandler) Main.pvpGameModeMenu.getCommandHandler();
        pvpGameModeCommandHandler.zombiePlayer = opponentPlayer;


        Menu plantCollectionMenu=new Menu(Main.playMenu, new CollectionCommandHandler());
        Menu zombieCollectionMenu=new Menu(plantCollectionMenu, new CollectionCommandHandler());

        CollectionCommandHandler plantCollectionMenuCommandHandler =
                (CollectionCommandHandler) plantCollectionMenu.getCommandHandler();
        plantCollectionMenuCommandHandler.collectionMode = CollectionMode.plantsCollection;
        plantCollectionMenuCommandHandler.nextMenu = zombieCollectionMenu;

        CollectionCommandHandler zombieCollectionMenuCommandHandler =
                (CollectionCommandHandler) zombieCollectionMenu.getCommandHandler();
        zombieCollectionMenuCommandHandler.collectionMode = CollectionMode.plantsCollection;
        zombieCollectionMenuCommandHandler.nextMenu = Main.pvpGameModeMenu;

        Player.getCurrentPlayer().setCurrentMenu(plantCollectionMenu);
    }
}
