package Command;

import Chat.Message;
import Main.Menu;
import Main.User;
import org.json.simple.JSONObject;

public class ChatCommandHandler extends CommandHandler {
    User sender, receiver;

    {
        this.commands = new Command[]{
                new Command(this::showChat, "show chat", ""),
                new Command(this::sendMessage, "send message", ""),
                new Command(this::endTurn, "end turn", ""),
        };
    }

    private void endTurn(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), this).run();
    }

    public ChatCommandHandler(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public void sendMessage(InputCommand inputCommand) {
        String content = (String) inputCommand.getInputJsonObject().get("content");
        new Message(content, sender, receiver);
        showChat(null);
    }

    public void showChat(InputCommand inputCommand) {
        menu.getConnection().send("showChat", Message.getChatBetweenUsers(sender, receiver));
    }
}
