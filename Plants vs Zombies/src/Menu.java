public class Menu {
    private Menu lastMenu;
    CommandHandler commandHandler;
    public Menu(Menu lastMenu, CommandHandler commandHandler) {
        this.lastMenu = lastMenu;
        this.commandHandler = commandHandler;
    }

    public void accept(User user, String command) throws Exception {
        commandHandler.accept(user, command);
    }
}
