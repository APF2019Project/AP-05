package Main;

import Command.CommandHandler;
import Player.Player;

public class Menu {
    private Menu lastMenu;
    private CommandHandler commandHandler;
    private static Menu currentMenu;
    private User user;
    private boolean isOpen = false;

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public User getUser() {
        return user;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        Menu.currentMenu = currentMenu;
    }

    public Menu(User user, Menu lastMenu, CommandHandler commandHandler) {
        this.user=user;
        this.lastMenu = lastMenu;
        this.commandHandler = commandHandler;
        commandHandler.setMenu(this);
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

    public void exit() throws Exception {
        isOpen = false;
    }

    public void exitAndOpen(Menu menu) throws Exception {
        isOpen = false;
        if (menu != null) {
            menu.run();
        }
    }

    public void accept(String command) throws Exception {
        if (command.equals("help")) {
            Main.print(commandHandler.help());
            return;
        }
        if (command.equals("exit")) {
            exitAndOpen(lastMenu);
            return;
        }
        commandHandler.accept(command);
    }

    public void run() throws Exception {
        isOpen = true;
        setCurrentMenu(this);
        while (isOpen) {
            String command = Main.scanLine();
            accept(command);
        }
    }
}
