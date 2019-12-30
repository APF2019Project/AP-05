package Main;

import Command.CommandHandler;
import Player.Player;

public class Menu {
    //private Menu lastMenu;
    private CommandHandler commandHandler;
    private User user;
    private boolean isOpen = false;

    public User getUser() {
        return user;
    }

    public Menu(User user, CommandHandler commandHandler) {
        this.user = user;
        //this.lastMenu = lastMenu;
        this.commandHandler = commandHandler;
        commandHandler.setMenu(this);
    }

    /*public Menu getLastMenu() {
        return lastMenu;
    }

    public void setLastMenu(Menu lastMenu) {
        this.lastMenu = lastMenu;
    }*/

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public void exit() {
        isOpen = false;
    }

    public void accept(String command) throws Exception {
        if (command.equals("help")) {
            Main.print(commandHandler.help());
            return;
        }
        if (command.equals("exit")) {
            exit();
            return;
        }
        commandHandler.accept(command);
    }

    public void run() throws Exception {
        isOpen = true;
        while (isOpen) {
            Main.clearScreen();
            Main.print("Commands:\n" + commandHandler.help());
            String command = Main.scanLine();
            accept(command);
        }
    }
}
