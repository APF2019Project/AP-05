package Command;

import Main.Menu;

public class CommandHandler {
    protected Command[] commands;
    protected Menu menu;

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void accept(String inputCommand) throws Exception {
        for (Command command : commands)
            if (command.accept(inputCommand))
                return;
        throw new Exception("invalid command");
    }

    public String help() throws Exception {
        StringBuilder result = new StringBuilder();
        for (Command command : commands)
            result.append(command.getHelp()).append("\n");
        return result.toString();
    }
}

