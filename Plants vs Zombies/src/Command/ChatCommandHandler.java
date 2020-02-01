package Command;

import Chat.Message;
import Main.User;

public class ChatCommandHandler extends CommandHandler {
    User sender, receiver;

    {
        this.commands = new Command[]{
                new Command(this::showChat, "show chat", ""),
                new Command(this::sendMessage, "send message", ""),
        };
    }

    public ChatCommandHandler(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    private void sendMessage(InputCommand inputCommand) {
        String content = (String) inputCommand.getInputJsonObject().get("content");
        new Message(content, sender, receiver);
        showChat(null);
    }

    private void showChat(InputCommand inputCommand) {
        menu.getConnection().send("showChat", Message.getChatBetweenUsers(sender, receiver));
    }

}
