package Command;

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

    public boolean accept(String input) throws Exception {
        if (Pattern.matches(regex, input)) {
            function.accept(new InputCommand(this, input));
            return true;
        }
        return false;
    }
}