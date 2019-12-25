package Command;

import Main.*;
import Player.Player;

public class PlayCommandHandler extends CommandHandler {
    {
        this.commands = new Command[]{
                new Command(this::playDayMode, "day", "day: To enter \"day\" type game."),
                new Command(this::playWaterMode, "water", "water: To enter \"water\" type game."),
                new Command(this::playRailMode, "rail", "rail: To enter \"rail\" type game."),
                new Command(this::playZombieMode, "zombie", "zombie: To enter \"zombie\" type game."),
                new Command(this::playPvPMode, "pvp", "pvp: To enter multiPlayer type game."),
        };
    }

    public void playDayMode(String command) {
        GameMenuSwitcher gameMenuSwitcher=new GameMenuSwitcher(Player.getCurrentPlayer(),new ZombieAIPleyer());

        Menu collectionMenu = new Menu(Main.playMenu, new CollectionCommandHandler(){
            @Override
            public void onEnd() {
                Player.getCurrentPlayer().setCurrentMenu(Main.dayAndWaterGameModeMenu);
                super.onEnd();
            }
        });
        CollectionCommandHandler collectionCommandHandler =
                (CollectionCommandHandler) collectionMenu.getCommandHandler();
        collectionCommandHandler.collectionMode = CollectionMode.plantsCollection;

        DayAndWaterGameModeCommandHandler dayAndWaterGameModeCommandHandler =
                (DayAndWaterGameModeCommandHandler) Main.dayAndWaterGameModeMenu.getCommandHandler();
        dayAndWaterGameModeCommandHandler.haveWater = false;
        Player.getCurrentPlayer().setCurrentMenu(collectionMenu);
    }

    public void playWaterMode(String command) {
        Menu collectionMenu = new Menu(Main.playMenu, new CollectionCommandHandler(){
            @Override
            public void onEnd() {
                Player.getCurrentPlayer().setCurrentMenu(Main.dayAndWaterGameModeMenu);
                super.onEnd();
            }
        });
        CollectionCommandHandler collectionCommandHandler =
                (CollectionCommandHandler) collectionMenu.getCommandHandler();
        collectionCommandHandler.collectionMode = CollectionMode.plantsCollection;

        DayAndWaterGameModeCommandHandler dayAndWaterGameModeCommandHandler =
                (DayAndWaterGameModeCommandHandler) Main.dayAndWaterGameModeMenu.getCommandHandler();
        dayAndWaterGameModeCommandHandler.haveWater = true;
        Player.getCurrentPlayer().setCurrentMenu(collectionMenu);
    }

    public void playRailMode(String command) {
        Player.getCurrentPlayer().setCurrentMenu(Main.railGameModeMenu);
    }

    public void playZombieMode(String command) {
        Menu collectionMenu = new Menu(Main.playMenu, new CollectionCommandHandler(){
            @Override
            public void onEnd() {
                Player.getCurrentPlayer().setCurrentMenu(Main.zombieGameModeMenu);
                super.onEnd();
            }
        });
        CollectionCommandHandler collectionCommandHandler =
                (CollectionCommandHandler) collectionMenu.getCommandHandler();
        collectionCommandHandler.collectionMode = CollectionMode.zombiesCollection;
        Player.getCurrentPlayer().setCurrentMenu(collectionMenu);
    }

    public void playPvPMode(String command) throws Exception {
        String username = Main.scanLine();
        User opponentUser = User.getUserByUsername(username);
        if (opponentUser == null) {
            throw new Exception("invalid username");
        }
        ZombiePlayer zombiePlayer = new ZombiePlayer(opponentUser);
        PlantPlayer plantPlayer = (PlantPlayer) Player.getCurrentPlayer();

        PvPGameModeCommandHandler pvpGameModeCommandHandler =
                (PvPGameModeCommandHandler) Main.pvpGameModeMenu.getCommandHandler();
        pvpGameModeCommandHandler.zombiePlayer = zombiePlayer;
        Menu zombieCollectionMenu = null;
        Menu plantCollectionMenu = new Menu(Main.playMenu, new CollectionCommandHandler(){
            public void onEnd() {
                Player.setCurrentPlayer(plantPlayer);
                Player.getCurrentPlayer().setCurrentMenu(zombieCollectionMenu);
                super.onEnd();
            }
        });

        zombieCollectionMenu = new Menu(plantCollectionMenu, new CollectionCommandHandler() {
            public void onStart() {
                Player.setCurrentPlayer(zombiePlayer);
                super.onStart();
            }

            public void onEnd() {
                Player.setCurrentPlayer(plantPlayer);
                Player.getCurrentPlayer().setCurrentMenu(Main.pvpGameModeMenu);
                super.onEnd();
            }
        });
        CollectionCommandHandler plantCollectionMenuCommandHandler =
                (CollectionCommandHandler) plantCollectionMenu.getCommandHandler();
        plantCollectionMenuCommandHandler.collectionMode = CollectionMode.plantsCollection;
        plantCollectionMenuCommandHandler.nextMenu = zombieCollectionMenu;

        CollectionCommandHandler zombieCollectionMenuCommandHandler =
                (CollectionCommandHandler) zombieCollectionMenu.getCommandHandler();
        zombieCollectionMenuCommandHandler.collectionMode = CollectionMode.zombiesCollection;
        zombieCollectionMenuCommandHandler.nextMenu = Main.pvpGameModeMenu;

        Player.getCurrentPlayer().setCurrentMenu(plantCollectionMenu);
    }
}
