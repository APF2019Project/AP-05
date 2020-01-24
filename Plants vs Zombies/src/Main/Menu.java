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
            JSONObject jsonObject = new JSONObject();
            JSONObject parametersJsonObject = new JSONObject();
            jsonObject.put("action", "showMessage");
            parametersJsonObject.put("message", e.getMessage());
            parametersJsonObject.put("messageType", "WARNING");
            jsonObject.put("parameters", parametersJsonObject);
            connection.send(jsonObject.toJSONString());
        }
    }

    public void showHelp() throws Exception {
        Main.print(commandHandler.help());
    }

    public void run() throws Exception {
        isOpen = true;
        JSONObject jsonObject = new JSONObject();
        JSONObject parametersJsonObject = new JSONObject();
        jsonObject.put("action", "newMenu");
        parametersJsonObject.put("menuName", "first");
        jsonObject.put("parameters", parametersJsonObject);
        connection.send(jsonObject.toJSONString());
        /*while (isOpen) {
            try {
                commandHandler.setFirstLineDescription();
                showHelp(); // bayad pak shavad
                String command = Main.scanLine().toLowerCase();
                accept(command);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }*/
    }
}
