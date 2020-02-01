package Command;

import org.json.simple.JSONObject;

import java.util.regex.Pattern;

public class Command {
    final private String regex;
    final private String help;
    private Function function;


    public Command(Function function, String regex, String help) {
        this.function = function;
        this.regex = regex;
        this.help = help;
    }

    public Function getFunction() {
        return function;
    }

    public String getRegex() {
        return regex;
    }

    public String getHelp() {
        return help;
    }

    public boolean accept(JSONObject jsonObject) throws Exception {
        if (Pattern.matches(regex, (String) jsonObject.get("command"))) {
            function.accept(new InputCommand(this, (JSONObject) jsonObject.get("data")));
            return true;
        }
        return false;
    }
}