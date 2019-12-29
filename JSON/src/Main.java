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
        parseAndSave(lilypad, "plants/lilypad/lilypad");

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
        parseAndSave(tall_nut, "plants/tall_nut");

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
        parseAndSave(wall_nut, "plants/wall_nut");

        String explodonut = "{\"name\": \"Explode-o-nut\", \"disposable\": false" +
                "\"coolDown\" : 5" +
                "\"fullHp\" : 3" +
                "\"price\" : 4" +
                "\"reloadTime\" : 0" +
                "\"shield\": null" +
                "\"cactus\": true" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(explodonut, "plants/explodonut");

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
        parseAndSave(magnetshroom, "plants/magnetshroom/magnetshroom");

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
        parseAndSave(sunflower, "plants/sunflower/sunflower");

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
        parseAndSave(twinsunflower, "plants/sunflower/twinsunflower");

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
                "\"activeRange\" : -1"
                + "}";
        parseAndSave(jalapeno, "plants/mine/jalapeno");

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
                "\"activeRange\" : -1"
                + "}";
        parseAndSave(cherry, "plants/mine/cherry");

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
                "\"activeRange\" : 0"
                + "}";
        parseAndSave(mine, "plants/mine/mine");

        String watermine = "{\"name\": \"Tangle Kelp\", \"disposable\": true" +
                "\"coolDown\" : 3" +
                "\"fullHp\" : 100" +
                "\"price\" : 3" +
                "\"reloadTime\" : 0" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : true" +
                "\"deltaX\" : 0" +
                "\"deltaY\" : 0" +
                "\"activeRange\" : 0"
                + "}";
        parseAndSave(watermine, "plants/mine/watermine");

        String Lawnmower = "{\"name\": \"Lawnmower\", \"disposable\": true" +
                "\"coolDown\" : 0" +
                "\"fullHp\" : 1000" +
                "\"price\" : 0" +
                "\"reloadTime\" : 0" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false" +
                "\"deltaX\" : 1000" +
                "\"deltaY\" : 0" +
                "\"activeRange\" : 0"
                + "}";
        parseAndSave(watermine, "plants/mine/lawnmower");

        String peashootergun = "{\"name\": \"Peashooter Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String peashooter = "{\"name\": \"Peashooter\", \"disposable\": false" +
                "\"coolDown\" : 2" +
                "\"fullHp\" : 2" +
                "\"price\" : 2" +
                "\"reloadTime\" : 2" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(peashootergun, "guns/peashootergun");
        parseAndSave(peashooter, "plants/shooter/peashooter");

        String snowpeagun = "{\"name\": \"Snow Pea Gun\", " +
                "\"icy\": true" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String snowpea = "{\"name\": \"Snow Pea\", \"disposable\": false" +
                "\"coolDown\" : 3" +
                "\"fullHp\" : 3" +
                "\"price\" : 3" +
                "\"reloadTime\" : 3" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(snowpeagun, "guns/snowpeagun");
        parseAndSave(snowpea, "plants/shooter/snowpea");

        String cabbagepultgun = "{\"name\": \"Cabbage-pult Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": false" +
                "\"damage\": 2" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String cabbagepult = "{\"name\": \"Cabbage-pult\", \"disposable\": false" +
                "\"coolDown\" : 3" +
                "\"fullHp\" : 2" +
                "\"price\" : 2" +
                "\"reloadTime\" : 2" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(cabbagepultgun, "guns/cabbagepultgun");
        parseAndSave(cabbagepult, "plants/shooter/cabbagepult");

        String repeatergun = "{\"name\": \"Repeater Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 2"
                + "}";
        String repeater = "{\"name\": \"Repeater\", \"disposable\": false" +
                "\"coolDown\" : 4" +
                "\"fullHp\" : 4" +
                "\"price\" : 3" +
                "\"reloadTime\" : 3" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(repeatergun, "guns/repeatergun");
        parseAndSave(repeater, "plants/shooter/repeater");

        String cactusgun = "{\"name\": \"Cactus Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String cactus = "{\"name\": \"Cactus\", \"disposable\": false" +
                "\"coolDown\" : 4" +
                "\"fullHp\" : 5" +
                "\"price\" : 5" +
                "\"reloadTime\" : 2" +
                "\"shield\": null" +
                "\"cactus\": true" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(cactusgun, "guns/cactusgun");
        parseAndSave(cactus, "plants/shooter/cactus");

        String gatlingpeagun = "{\"name\": \"Gatling Pea Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 4"
                + "}";
        String gatlingpea = "{\"name\": \"Gatling Pea\", \"disposable\": false" +
                "\"coolDown\" : 4" +
                "\"fullHp\" : 3" +
                "\"price\" : 5" +
                "\"reloadTime\" : 5" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(gatlingpeagun, "guns/gatlingpeagun");
        parseAndSave(gatlingpea, "plants/shooter/gatlingpea");

        String scaredyshroomgun = "{\"name\": \"Scaredy-shroom Gun\", " +
                "\"icy\": false" +
                "\"shy\": true" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String scaredyshroom = "{\"name\": \"Scaredy-shroom\", \"disposable\": false" +
                "\"coolDown\" : 2" +
                "\"fullHp\" : 1" +
                "\"price\" : 1" +
                "\"reloadTime\" : 2" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(scaredyshroomgun, "guns/scaredyshroomgun");
        parseAndSave(scaredyshroom, "plants/shooter/scaredyshroom");

        String kernelpultgun = "{\"name\": \"Kernel-pult Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": true" +
                "\"projectile\": false" +
                "\"damage\": 0" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String kernelpult = "{\"name\": \"Kernel-pult\", \"disposable\": false" +
                "\"coolDown\" : 3" +
                "\"fullHp\" : 2" +
                "\"price\" : 3" +
                "\"reloadTime\" : 4" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(kernelpultgun, "guns/kernelpultgun");
        parseAndSave(kernelpult, "plants/shooter/kernelpult");

        String splitpeagun = "{\"name\": \"Split Pea Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String splitpea = "{\"name\": \"Split Pea\", \"disposable\": false" +
                "\"coolDown\" : 4" +
                "\"fullHp\" : 3" +
                "\"price\" : 4" +
                "\"reloadTime\" : 1" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(splitpeagun, "guns/doublesided/splitpeagun");
        parseAndSave(splitpea, "plants/shooter/splitpea");

        String melonpultgun = "{\"name\": \"Melon-pult Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": false" +
                "\"damage\": 3" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String melonpult = "{\"name\": \"Melon-pult\", \"disposable\": false" +
                "\"coolDown\" : 3" +
                "\"fullHp\" : 3" +
                "\"price\" : 3" +
                "\"reloadTime\" : 4" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(melonpultgun, "guns/melonpultgun");
        parseAndSave(melonpult, "plants/shooter/melonpult");

        String icymelonpultgun = "{\"name\": \"Winter Melon Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": false" +
                "\"damage\": 3" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String icymelonpult = "{\"name\": \"Winter Melon\", \"disposable\": false" +
                "\"coolDown\" : 3" +
                "\"fullHp\" : 3" +
                "\"price\" : 3" +
                "\"reloadTime\" : 4" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(icymelonpultgun, "guns/icymelonpultgun");
        parseAndSave(icymelonpult, "plants/shooter/icymelonpult");

        String threerowgun = "{\"name\": \"Threepeater Gun\", " +
                "\"icy\": false" +
                "\"shy\": false" +
                "\"sleepy\": false" +
                "\"projectile\": true" +
                "\"damage\": 1" +
                "\"gunShotsPerRound\": 1"
                + "}";
        String threerow = "{\"name\": \"Threepeater\", \"disposable\": false" +
                "\"coolDown\" : 4" +
                "\"fullHp\" : 5" +
                "\"price\" : 4" +
                "\"reloadTime\" : 4" +
                "\"shield\": null" +
                "\"cactus\": false" +
                "\"peppery\" : false" +
                "\"waterProof\" : false"
                + "}";
        parseAndSave(threerow, "guns/threerow/threerowgun");
        parseAndSave(threerowgun, "plants/shooter/threepeater");
    }

    static void parseAndSave(String jsonString, String path) throws ParseException, IOException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        FileWriter fileWriter = new FileWriter(path);
        System.out.println(jsonObject.toJSONString());
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();
    }
}
