package Command;

import Main.Connection;
import Main.JSONHandler;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class CustomCardCommandHandler extends CommandHandler {
    private String address;

    {
        this.commands = new Command[]{
                new Command(this::showFields, "show fields", ""),
                new Command(this::create, "create", ""),
        };
    }

    public CustomCardCommandHandler(String address) {
        this.address = address;
    }

    private void create(InputCommand inputCommand) throws Exception {
        JSONObject jsonObject = inputCommand.getInputJsonObject();
        try {
            String className = "Objects." + address.substring(address.indexOf('/') + 1);
            if(className.equals("Objects.")) {
                className = "Objects." + address.substring(0, address.length() - 1);
            }
            System.out.println(className);
            System.out.println(className);
            System.out.println(className);
            System.out.println(className);
            System.out.println(className);

            Class<?> clss = Class.forName(className);
            clss.getConstructor(JSONHandler.class).newInstance(new JSONHandler(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String name = (String) jsonObject.get("name");
        File file = new File("JSON/" + address + "/" + name);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();
    }

    private void showFields(InputCommand inputCommand) {
        File directory = new File("JSON/" + address);
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                try {
                    menu.getConnection().send("showFields",
                            new JSONHandler(file).getJsonObject());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


}
