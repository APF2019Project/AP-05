package Command;

import Main.Menu;
import Main.User;

public class AllUsersCommandHandler extends CommandHandler {
    {
        this.commands = new Command[] {
                new Command(this::EnterChat, "enter chat", ""),
        };
    }

    private void EnterChat(InputCommand inputCommand) throws Exception {
        String otherUsername = (String) inputCommand.getInputJsonObject().get("username");
        User otherUser = User.getUserByUsername(otherUsername);
        if(otherUser == null) {
            throw new Exception("user doesn't exist anymore!");
        }

        new Menu(menu.getConnection(), new ChatCommandHandler(menu.getConnection().getUser(), otherUser)).run();
    }
}
