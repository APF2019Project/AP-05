import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

interface Function {
    void accept(String string) throws Exception;
}

interface BiFunction {
    void accept(User user, String string) throws Exception;
}

public class Command {
    final private String regex;
    final private String help;
    private BiFunction biFunction;
    private Function function;

    public Command(BiFunction biFunction, String regex, String help) {
        this.biFunction = biFunction;
        this.regex = regex;
        this.help = help;
    }

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

    public boolean accept(User user, String command) throws Exception {
        if (Pattern.matches(regex, command)) {
            biFunction.accept(user, command);
            return true;
        }
        return false;
    }
}