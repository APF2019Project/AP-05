import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

interface Function {
    void accept(String string) throws Exception;
}

public class Command {
    final private String regex;
    final private String help;
    private Function function;

    public Command(Function function, String regex, String help) {
        this.function = function;
        this.regex = regex;
        this.help = help;
    }

    public String getHelp() {
        return help;
    }

    public boolean accept(String command) throws Exception {
        if (Pattern.matches(regex, command)) {
            function.accept(command);
            return true;
        }
        return false;
    }
}