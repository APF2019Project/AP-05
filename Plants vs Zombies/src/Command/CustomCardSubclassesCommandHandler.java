package Command;

import Main.Menu;
import org.json.simple.JSONArray;

import java.io.File;
import java.util.Objects;

public class CustomCardSubclassesCommandHandler extends CommandHandler {
    private String type;

    {
        this.commands = new Command[]{
                new Command(this::showClasses, "show classes", ""),
                new Command(this::select, "select", ""),
        };
    }

    public CustomCardSubclassesCommandHandler(String type) {
        this.type = type;
    }

    private void select(InputCommand inputCommand) throws Exception {
        String name = (String) inputCommand.getInputJsonObject().get("name");
        if (name.equals("Normal"))
            name = "";
        new Menu(menu.getConnection(), new CustomCardCommandHandler(type + "/" + name)).run();
    }

    private void showClasses(InputCommand inputCommand) {
        JSONArray jsonArray = new JSONArray();
        File directory = new File("JSON/" + type);
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory())
                jsonArray.add(file.getName());
        }
        jsonArray.add("Normal");
        menu.getConnection().send("showClasses", jsonArray);
    }
}
