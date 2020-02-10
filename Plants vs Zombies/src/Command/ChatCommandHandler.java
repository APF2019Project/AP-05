package Command;

import Chat.Message;
import Main.Menu;
import Main.User;
import org.json.simple.JSONObject;

public class ChatCommandHandler extends CommandHandler {
    User sender, receiver;
    private int replyId = -1;

    {
        this.commands = new Command[]{
                new Command(this::showChat, "show", ""),
                new Command(this::sendMessage, "send message", ""),
                new Command(this::endTurn, "end turn", ""),
                new Command(this::reply, "reply", ""),
                new Command(this::removeReply, "remove reply", ""),
        };
    }

    public ChatCommandHandler(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public void removeReply(InputCommand inputCommand) {
        replyId = -1;
        showChat(null);
    }

    private void reply(InputCommand inputCommand) {
        replyId = ((Long) inputCommand.getInputJsonObject().get("replyId")).intValue();
        showChat(null);
    }

    private void endTurn(InputCommand inputCommand) throws Exception {
        new Menu(menu.getConnection(), this).run();
    }

    public void sendMessage(InputCommand inputCommand) {
        String content = (String) inputCommand.getInputJsonObject().get("content");
        String photoPath = (String) inputCommand.getInputJsonObject().get("photoPath");
        new Message(content, sender, receiver, photoPath).setRepliedMessage(Message.getMessageById(replyId));
        replyId = -1;
        showChat(null);
    }

    public void showChat(InputCommand inputCommand) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chat", Message.getChatBetweenUsers(sender, receiver));
        if (replyId != -1) {
            Message message = Message.getMessageById(replyId);
            jsonObject.put("replyMessage", message.toJsonObject());
        }
        menu.getConnection().send("showChat", jsonObject);
    }
}
