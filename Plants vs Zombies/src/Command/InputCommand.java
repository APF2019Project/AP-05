package Command;

import org.json.simple.JSONObject;

public class InputCommand {
    final private Command command;
    final private JSONObject inputJsonObject;

    public InputCommand(Command command, JSONObject inputJsonObject) {
        this.command = command;
        this.inputJsonObject = inputJsonObject;
    }

    public Command getCommand() {
        return command;
    }

    public JSONObject getInputJsonObject() {
        return inputJsonObject;
    }
}
