package game;
import java.util.ArrayList;

public abstract class CommandHandler {
    protected ArrayList<String> commands;
    abstract boolean matchAndDo(String command);
}