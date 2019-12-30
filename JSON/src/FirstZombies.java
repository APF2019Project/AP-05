import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class FirstZombies {

    public static void main(String[] args) throws IOException, ParseException {
        String firstZombies = "[\"Zombie\"," +
                "\"Football Zombie\"" +
                "\"Screen Door Zombie\"" +
                "\"Zomboni\"" +
                "\"Balloon Zombie\"" +
                "\"Bungee Zombie\"" +
                "\"Conehead Zombie\"" +
                "]";
        parseAndSave(firstZombies, "firstZombies");
    }

    static void parseAndSave(String jsonString, String path) throws ParseException, IOException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonObject = (JSONArray) jsonParser.parse(jsonString);
        FileWriter fileWriter = new FileWriter(path);
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();
    }
}
