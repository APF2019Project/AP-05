package Main;

import Command.CommandHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Menu {
    //private Menu lastMenu;
    private CommandHandler commandHandler;
    private final Connection connection;
    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public Connection getConnection() {
        return connection;
    }

    public Menu(Connection connection, CommandHandler commandHandler) {
        this.connection = connection;
        this.commandHandler = commandHandler;
        commandHandler.setMenu(this);
        connection.pushMenu(this);
    }

    public void exit() throws Exception {
        isOpen = false;
        connection.popMenu();
    }

    public void accept(String input) throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(input);
        String command = (String) jsonObject.get("command");
        System.err.println("Command:" + command);
        try {
            if (command.equals("help")) {
                //showHelp(); // bayad ezafe shavad
                return;
            }
            if (command.equals("exitMenu")) {
                if (GameMenuSwitcher.getGameStatus().equals(GameStatus.OnGame)) {
                    GameMenuSwitcher.setGameStatus(GameStatus.notInGame);
                }
                exit();
                return;
            }
            commandHandler.accept(jsonObject);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            data.put("message", e.getMessage());
            data.put("messageType", "WARNING");
            connection.send("showMessage", data);
        }
    }

    public void showHelp() throws Exception {
        Main.print(commandHandler.help());
    }

    public void run() throws Exception {
        isOpen = true;
        String menuName = commandHandler.getClass().getSimpleName().split("CommandHandler")[0];
        connection.send("runMenu", menuName);
    }
}
