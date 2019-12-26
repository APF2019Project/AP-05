package Main;

import Command.CommandHandler;
import Player.Player;

public class Menu {
    private Menu lastMenu;
    private CommandHandler commandHandler;
    private static Menu currentMenu;
    private User user;

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        Menu.currentMenu = currentMenu;
    }

    public Menu(Menu lastMenu, CommandHandler commandHandler) {
        this.lastMenu = lastMenu;
        this.commandHandler = commandHandler;
    }

    public Menu getLastMenu() {
        return lastMenu;
    }

    public void setLastMenu(Menu lastMenu) {
        this.lastMenu = lastMenu;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public void accept(String command) throws Exception {
        if (command.equals("help")) {
            Main.print(commandHandler.help());
            return;
        }
        if (command.equals("exit")) {
            Player.getCurrentPlayer().setCurrentMenu(lastMenu);
            return;
        }
        commandHandler.accept(command);
    }

    public void run() throws Exception {
        String command=Main.scanLine();
        accept(command);
        if (!command.equals("exit")){
            run();
        }
    }
}
