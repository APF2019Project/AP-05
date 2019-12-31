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
            //showHelp(); // bayad ezafe shavad
            return;
        }
        if (command.equals("exit")) {
            exit();
            return;
        }
        commandHandler.accept(command);
    }

    public void showHelp() throws Exception {
        Main.print("Commands:\n" + commandHandler.help());
    }

    public void run() throws Exception {
        isOpen = true;
        while (isOpen) {
            try {
                showHelp(); // bayad pak shavad
                String command = Main.scanLine().toLowerCase();
                accept(command);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
