import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        JSONParser jsonParser = new JSONParser();
        String lilypad = "{\"name\": \"Lily Pad\", \"disposable\": false" +
                "\"coolDown\" : 1" +
                "\"fullHp\" : 1" +
                "\"price\" : 0" +
                "\"reloadTime\" : 1" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : true"
                + "}";
        JSONObject jsonObject = (JSONObject) jsonParser.parse(lilypad);
        FileWriter fileWriter = new FileWriter("plants/lilypad/lilypad");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String tall_nut = "{\"name\": \"Tall-nut\", \"disposable\": false" +
                "\"coolDown\" : 6" +
                "\"fullHp\" : 6" +
                "\"price\" : 4" +
                "\"reloadTime\" : 1" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(tall_nut);
        fileWriter = new FileWriter("plants/tall_nut");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String wall_nut = "{\"name\": \"Wall-nut\", \"disposable\": false" +
                "\"coolDown\" : 4" +
                "\"fullHp\" : 4" +
                "\"price\" : 2" +
                "\"reloadTime\" : 1" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(wall_nut);
        fileWriter = new FileWriter("plants/wall_nut");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String magnetshroom = "{\"name\": \"Magnet-shroom\", \"disposable\": false" +
                "\"coolDown\" : 2" +
                "\"fullHp\" : 2" +
                "\"price\" : 1" +
                "\"reloadTime\" : 3" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(magnetshroom);
        fileWriter = new FileWriter("plants/magnetshroom/magnetshroom");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String sunflower = "{\"name\": \"Sunflower\", \"disposable\": false" +
                "\"coolDown\" : 2" +
                "\"fullHp\" : 2" +
                "\"price\" : 1" +
                "\"reloadTime\" : 2" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false" +
                "\"numberOfSuns\" : 1"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(sunflower);
        fileWriter = new FileWriter("plants/sunflower/sunflower");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String twinsunflower = "{\"name\": \"Twin Sunflower\", \"disposable\": false" +
                "\"coolDown\" : 5" +
                "\"fullHp\" : 2" +
                "\"price\" : 3" +
                "\"reloadTime\" : 2" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false" +
                "\"numberOfSuns\" : 2"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(twinsunflower);
        fileWriter = new FileWriter("plants/sunflower/twinsunflower");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String jalapeno = "{\"name\": \"Jalapeno\", \"disposable\": true" +
                "\"coolDown\" : 5" +
                "\"fullHp\" : 1000" +
                "\"price\" : 4" +
                "\"reloadTime\" : 0" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false" +
                "\"deltaX\" : 1000" +
                "\"deltaY\" : 0" +
                "\"activeRange\" : 1000"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(jalapeno);
        fileWriter = new FileWriter("plants/mine/jalapeno");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String cherry = "{\"name\": \"Cherry Bomb\", \"disposable\": true" +
                "\"coolDown\" : 4" +
                "\"fullHp\" : 1000" +
                "\"price\" : 2" +
                "\"reloadTime\" : 0" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : true" +
                "\"deltaX\" : 1" +
                "\"deltaY\" : 1" +
                "\"activeRange\" : 5"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(cherry);
        fileWriter = new FileWriter("plants/mine/cherry");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();

        String mine = "{\"name\": \"Potato Mine\", \"disposable\": true" +
                "\"coolDown\" : 3" +
                "\"fullHp\" : 1" +
                "\"price\" : 2" +
                "\"reloadTime\" : 1" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false" +
                "\"deltaX\" : 0" +
                "\"deltaY\" : 0" +
                "\"activeRange\" : 2"
                + "}";
        jsonObject = (JSONObject) jsonParser.parse(mine);
        fileWriter = new FileWriter("plants/mine/mine");
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();
    }
}
