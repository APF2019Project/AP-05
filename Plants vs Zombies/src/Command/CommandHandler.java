package Command;

import Main.Menu;
import org.json.simple.JSONObject;

public abstract class CommandHandler {
    protected Command[] commands;
    protected String firstLineDescription = "";
    protected Menu menu;

    public void setFirstLineDescription() {
        // nothing
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void accept(JSONObject jsonObject) throws Exception {
        for (Command command : commands) {
            if (command.accept(jsonObject))
                return;
        }
        if (!(jsonObject.get("command")).equals("end turn")) {
            throw new Exception("invalid command");
        }
    }

    public String help() throws Exception {
        StringBuilder result = new StringBuilder();
        if (!firstLineDescription.isEmpty()) {
            result.append(firstLineDescription).append("\n");
        }
        result.append("Commands:\n");
        for (Command command : commands)
            result.append(command.getHelp()).append("\n");
        return result.toString();
    }
}

