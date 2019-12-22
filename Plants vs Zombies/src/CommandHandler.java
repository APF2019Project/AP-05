public class CommandHandler {
    protected Command[] commands = {

    };

    public void accept(User user, String inputCommand) throws Exception {
        for (Command command : commands)
            if (command.accept(user, inputCommand))
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

