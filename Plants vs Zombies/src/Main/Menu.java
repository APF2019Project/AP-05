package Main;

import Command.CommandHandler;
import org.json.simple.JSONObject;

public class Menu {
    //private Menu lastMenu;
    private CommandHandler commandHandler;
    private final Connection connection;
    private boolean isOpen = false;

    public Connection getConnection() {
        return connection;
    }

    public Menu(Connection connection, CommandHandler commandHandler) {
        this.connection = connection;
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
        try {
            if (command.equals("help")) {
                //showHelp(); // bayad ezafe shavad
                return;
            }
            if (command.equals("exit")) {
                if (GameMenuSwitcher.getGameStatus().equals(GameStatus.OnGame)) {
                    GameMenuSwitcher.setGameStatus(GameStatus.notInGame);
                }
                exit();
                return;
            }
            commandHandler.accept(command);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            data.put("message", e.getMessage());
            data.put("messageType", "WARNING");
            connection.send("showMessage",data);
        }
    }

    public void showHelp() throws Exception {
        Main.print(commandHandler.help());
    }

    public void run() throws Exception {
        isOpen = true;
        while(isOpen) {
            JSONObject data = new JSONObject();
            String menuName = commandHandler.getClass().getName().split("CommandHandler")[0].toLowerCase();
            connection.send("runMenu", menuName);
        }
    }
}
