import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class PlantsInZombieMode {
    public static void main(String[] args) throws IOException, ParseException {
        String plants = "[\"Explode-o-nut\"" +
                "\"Scaredy-shroom\"" +
                "\"Snow Pea\"" +
                "\"Cabbage-pult\"" +
                "\"Threepeater\"" +
                "\"Gatling Pea\"" +
                "\"Potato Mine\"" +
                "]";
        String num = "[3," +
                "6," +
                "2," +
                "2," +
                "1," +
                "1," +
                "3" +
                "]";
        parseAndSave(plants, "dayMode/plants");
        parseAndSave(num, "dayMode/num");

        plants = "[\"Explode-o-nut\"" +
                "\"Scaredy-shroom\"" +
                "\"Snow Pea\"" +
                "\"Cabbage-pult\"" +
                "\"Gatling Pea\"" +
                "\"Potato Mine\"" +
                "\"Tangle Kelp\"" +
                "\"Lily Pad\"" +
                "]";
        num = "[3," +
                "6," +
                "2," +
                "2," +
                "1," +
                "1," +
                "2," +
                "3" +
                "]";
        parseAndSave(plants, "waterMode/plants");
        parseAndSave(num, "waterMode/num");
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
