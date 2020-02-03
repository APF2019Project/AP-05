package Command;

import Chat.Message;
import Main.Menu;
import Main.User;

public class GlobalChatCommandHandler extends CommandHandler {
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

    public void sendMessage(InputCommand inputCommand) {
        String content = (String) inputCommand.getInputJsonObject().get("content");
        new Message(content, menu.getConnection().getUser(), null);
        showChat(null);
    }

    public void showChat(InputCommand inputCommand) {
        menu.getConnection().send("showChat", Message.getChatOfServer());
    }
}
