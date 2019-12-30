import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class Zombie {
    public static void main(String[] args) throws IOException, ParseException {
        String zombie = "{\"name\": \"Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 2" +
                "\"reloadTime\": 0" +
                "\"shield\": null" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(zombie, "zombie/zombie");

        String football_zombie = "{\"name\": \"Football Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 4" +
                "\"reloadTime\": 0" +
                "\"shield\": null" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": false" +
                "\"peaHasEffect\": true" +
                "\"speed\": 3" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(football_zombie, "zombie/football_zombie");

        String coneheadzombie = "{\"name\": \"Conehead Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 3" +
                "\"reloadTime\": 0" +
                "\"shield\": null" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(coneheadzombie, "zombie/coneheadzombie");

        String bucketheadzombieHat = "{\"name\": \"Buckethead Zombie Shield\"," +
                "\"fullHp\": 1" +
                "\"material\": \"metal\"" +
                "}";
        String bucketheadzombie = "{\"name\": \"Buckethead Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 3" +
                "\"reloadTime\": 0" +
                "\"shield\": \"Buckethead Zombie Shield\"" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(bucketheadzombieHat, "shield/bucketheadzombieshield");
        parseAndSave(bucketheadzombie, "zombie/bucketheadzombie");

        String zombonishield = "{\"name\": \"Zomboni Shield\"," +
                "\"fullHp\": 3" +
                "\"material\": \"plastic\"" +
                "}";
        String zomboni = "{\"name\": \"Zomboni\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 3" +
                "\"reloadTime\": 0" +
                "\"shield\": \"Zomboni Shield\"" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 100000" +
                "}";
        parseAndSave(zombonishield, "shield/zombonishield");
        parseAndSave(zomboni, "zombie/zomboni");

        String catapultzombieshield = "{\"name\": \"Catapult Zombie Shield\"," +
                "\"fullHp\": 2" +
                "\"material\": \"plastic\"" +
                "}";
        String catapultzombie = "{\"name\": \"Catapult Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 1" +
                "\"reloadTime\": 0" +
                "\"shield\": \"Catapult Zombie Shield\"" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 100000" +
                "}";
        parseAndSave(catapultzombieshield, "shield/catapultshield");
        parseAndSave(catapultzombie, "zombie/catapultzombie");

        String balloonzombie = "{\"name\": \"Balloon Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 3" +
                "\"reloadTime\": 0" +
                "\"shield\": null" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": false" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(balloonzombie, "zombie/balloonzombie");

        String newspaperzombieshield = "{\"name\": \"Newspaper Zombie Shield\"," +
                "\"fullHp\": 2" +
                "\"material\": \"plastic\"" +
                "}";
        String newspaperzombie = "{\"name\": \"Newspaper Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 2" +
                "\"reloadTime\": 0" +
                "\"shield\": \"Newspaper Zombie Shield\"" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(newspaperzombieshield, "shield/newspaperzombieshield");
        parseAndSave(newspaperzombie, "zombie/newspaperzombie");

        String targetzombieshield = "{\"name\": \"Target Zombie Shield\"," +
                "\"fullHp\": 3" +
                "\"material\": \"plastic\"" +
                "}";
        String targetzombie = "{\"name\": \"Target Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 3" +
                "\"reloadTime\": 0" +
                "\"shield\": \"Target Zombie Shield\"" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(targetzombieshield, "shield/targetzombieshield");
        parseAndSave(targetzombie, "zombie/targetzombie");

        String screendoorzombieshield = "{\"name\": \"Screen Door Zombie Shield\"," +
                "\"fullHp\": 4" +
                "\"material\": \"metal\"" +
                "}";
        String screendoorzombie = "{\"name\": \"Screen Door Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 2" +
                "\"reloadTime\": 0" +
                "\"shield\": \"Screen Door Zombie Shield\"" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(screendoorzombieshield, "shield/screendoorzombieshield");
        parseAndSave(screendoorzombie, "zombie/screendoorzombie");

        String giga = "{\"name\": \"Giga-gargantuar\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 6" +
                "\"reloadTime\": 0" +
                "\"shield\": null" +
                "\"swimmer\": false" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 1" +
                "\"power\": 10000000" +
                "\"powerWithShield\": 1000000" +
                "}";
        parseAndSave(giga, "zombie/giga");

        String snorkel = "{\"name\": \"Snorkel Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 2" +
                "\"reloadTime\": 0" +
                "\"shield\": null" +
                "\"swimmer\": true" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(snorkel, "zombie/snorkel");

        String dolphinridershield = "{\"name\": \"Dolphin Rider Zombie Shield\"," +
                "\"fullHp\": 2" +
                "\"material\": \"plastic\"" +
                "}";
        String dolphinrider = "{\"name\": \"Dolphin Rider Zombie\", " +
                "\"disposable\": false" +
                "\"coolDown\": 0" +
                "\"fullHp\": 2" +
                "\"reloadTime\": 0" +
                "\"shield\": \"Dolphin Rider Zombie\"" +
                "\"swimmer\": true" +
                "\"cactusHasEffect\": true" +
                "\"peaHasEffect\": true" +
                "\"speed\": 2" +
                "\"power\": 1" +
                "\"powerWithShield\": 1" +
                "}";
        parseAndSave(dolphinridershield, "shield/dolphinridershield");
        parseAndSave(dolphinrider, "zombie/dolphinrider");
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
