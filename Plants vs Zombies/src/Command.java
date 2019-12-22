import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class Command {
    final private BiConsumer<User, String> function;
    final private String regex;
    final private String help;

    public Command(BiConsumer<User, String> function, String regex, String help) {
        this.function = function;
        this.regex = regex;
        this.help = help;
    }

    public String getHelp() {
        return help;
    }

    public boolean accept(User user, String command) throws Exception {
        if (Pattern.matches(regex, command)) {
            function.accept(user, command);
            return true;
        }
        return false;
    }
}
