public class Menu {
    private Menu lastMenu;
    CommandHandler commandHandler;

    public Menu getLastMenu(){
        return  lastMenu;
    }

    public void setLastMenu(Menu lastMenu) {
        this.lastMenu = lastMenu;
    }

    public Menu(Menu lastMenu, CommandHandler commandHandler) {
        this.lastMenu = lastMenu;
        this.commandHandler = commandHandler;
    }

    public void accept(String command) throws Exception {
        if(command.equals("help")){
            Main.print(commandHandler.help());
            return;
        }
        if(command.equals("exit")){
            Main.currentUser.setCurrentMenu(lastMenu);
        }
        commandHandler.accept(command);
    }
}
