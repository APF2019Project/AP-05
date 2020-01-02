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
        this.commandHandler = commandHandler;
        commandHandler.setMenu(this);
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public void exit() {
        isOpen = false;
    }

    public void accept(String command) throws Exception {
        if (command.equals("help")) {
            //showHelp(); // bayad ezafe shavad
            return;
        }
        if (command.equals("exit")) {
            if(GameMenuSwitcher.getGameStatus().equals(GameStatus.OnGame)){
                GameMenuSwitcher.setGameStatus(GameStatus.notInGame);
            }
            exit();
            return;
        }
        commandHandler.accept(command);
    }

    public void showHelp() throws Exception {
        Main.print(commandHandler.help());
    }

    public void run() throws Exception {
        isOpen = true;
        while (isOpen) {
            try {
                commandHandler.setFirstLineDescription();
                showHelp(); // bayad pak shavad
                String command = Main.scanLine().toLowerCase();
                accept(command);
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
        }
    }
}
