package Command;

public class InputCommand {
    final private Command command;
    final private String input;

    public InputCommand(Command command, String input) {
        this.command = command;
        this.input = input;
    }

    public Command getCommand() {
        return command;
    }

    public String getInput() {
        return input;
    }
}
