package Main;

import Chat.Message;
import Command.CommandHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Menu {
    private final Connection connection;
    //private Menu lastMenu;
    private CommandHandler commandHandler;

    public Menu(Connection connection, CommandHandler commandHandler) {
        this.connection = connection;
        this.commandHandler = commandHandler;
        commandHandler.setMenu(this);
        connection.pushMenu(this);
    }

    public Connection getConnection() {
        return connection;
    }

    public void exit() throws Exception {
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
                System.out.println(this.getCommandHandlerName() + " HERE BAW");
                if (GameMenuSwitcher.getGameStatus().equals(GameStatus.OnGame)) {
                    GameMenuSwitcher.setGameStatus(GameStatus.notInGame);
                }
                exit();
                return;
            }
            if (command.equals("exitDoubleMenu")) {
                if (GameMenuSwitcher.getGameStatus().equals(GameStatus.OnGame)) {
                    GameMenuSwitcher.setGameStatus(GameStatus.notInGame);
                }
                getConnection().popMenuWithoutRun();
                getConnection().getCurrentMenu().exit();
                return;
            }
            if (command.equals("send notification message")) {
                JSONObject data = (JSONObject) jsonObject.get("data");
                String content = (String) data.get("content");
                JSONObject message = (JSONObject) data.get("message");
                int repliedId = ((Long) message.get("id")).intValue();
                Message repliedMessage = Message.getMessageById(repliedId);
                User receiver = null;
                if (message.containsKey("receiverUsername"))
                    receiver = User.getUserByUsername((String) message.get("senderUsername"));
                new Message(content, getConnection().getUser(), receiver, null).setRepliedMessage(repliedMessage);
                return;
            }
            commandHandler.accept(jsonObject);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            data.put("message", e.getMessage());
            data.put("messageType", "WARNING");
            connection.send("showMessage", data);
            run();
        }
    }

    public boolean isPlayerModeMenu() {
        return commandHandler.getClass().getSimpleName().toLowerCase().contains("player");
    }

    public void showHelp() throws Exception {
        Main.print(commandHandler.help());
    }

    public void run() throws Exception {
        String menuName = commandHandler.getClass().getSimpleName().split("CommandHandler")[0];
        connection.send("runMenu", menuName);
    }

    public String getCommandHandlerName() {
        return commandHandler.getClass().getSimpleName();
    }
}
