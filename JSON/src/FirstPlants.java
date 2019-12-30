import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class FirstPlants {

    public static void main(String[] args) throws IOException, ParseException {
        String firstPlants = "[\"Peashooter\"," +
                "\"Snow Pea\"" +
                "\"Explode-o-nut\"" +
                "\"Scaredy-shroom\"" +
                "\"Cherry Bomb\"" +
                "\"Kernel-pult\"" +
                "\"Sunflower\"" +
                "]";
        parseAndSave(firstPlants, "firstPlants");
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
