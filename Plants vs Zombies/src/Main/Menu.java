package Main;

import Command.CommandHandler;

public class Menu {
    private Menu lastMenu;
    private CommandHandler commandHandler;

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
        }
        commandHandler.accept(command);
    }
}
