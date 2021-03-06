package Main;

import Chat.Message;
import Objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameData {
    public final static int slices = 10;//hatman zoj
    public final static String winMessage = "آقا خیلی تبریک میگم! شما بردی";
    public final static String loseMessage = "آقا معذرت میخوام شما خیلی ضعیفی. باختی";
    public final static int mapRowCount = 6;
    public final static int mapPlantColCount = 9 + 2;
    public final static int mapColCount = mapPlantColCount * slices + slices / 2;
    public final static int creatureOnHandSize = 7;
    public final static int numberOfWavesInDayAndWaterMode = 3;
    public final static int numberOfWavesInRailMode = 10;
    public final static int numberOfWavesInZombieMode = 10;
    public final static int winReward = 200;
    public final static boolean[] isWaterInWaterMapMode = {false, false, true, true, false, false};
    public final static boolean[] isWaterInDayMapMode = {false, false, false, false, false, false};
    public final static String positiveNumber = "([0-9]{1,20})";
    public static int speedOfGunShot = 14;
    public static int shyDistanceLimit;
    public static int iceSlowDownTime;
    public static int iceSlowDownPercent = 50;
    public static int sleepySlowDownTime;
    public static int sleepySlowDownPercent = 100;
    public static int cactusDamage = 1;
    public static int PepperDamage = 1;
    public static int maxMagnetRange, MagnetEatingTime;
    static public ArrayList<String> DryModeAvailablePlantName = new ArrayList<>();
    static public ArrayList<String> WetModeAvailablePlantName = new ArrayList<>();
    static public int inf = 100000000;
    public static String usersJSONFilePath = "JSON/users";
    public static String messagesJSONFilePath = "JSON/messages";
    private static Connection AIConnection;

    public static Connection getAIConnection() {
        try {
            User.deleteUser("AI User", "1234");
            new User("AI User", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        AIConnection = new Connection(User.getUserByUsername("AI User"));
        return AIConnection;
    }

    private static void addLilyPadClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Plant/LilyPad").listFiles())) {
            if (file.isFile()) {
                new LilyPad(new JSONHandler(file));
            }
            else {
                throw new Exception("there are some unknown folders in json directory");
            }
        }
    }

    private static void addMagnetShroomClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Plant/MagnetShroom").listFiles())) {
            if (file.isFile()) {
                new MagnetShroom(new JSONHandler(file));
            }
        }
    }

    private static void addMineClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Plant/Mine").listFiles())) {
            if (file.isFile()) {
                new Mine(new JSONHandler(file));
            }
        }
    }

    private static void addShooterClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Plant/Shooter").listFiles())) {
            if (file.isFile()) {
                new Shooter(new JSONHandler(file));
            }
        }
    }

    private static void addSunFlowerClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Plant/SunFlower").listFiles())) {
            if (file.isFile()) {
                new SunFlower(new JSONHandler(file));
            }
        }
    }

    private static void addFirstPlants() throws Exception {
        FileReader fileReader = new FileReader("JSON/firstPlants");
        JSONArray jsonArray = (JSONArray) (new JSONParser()).parse(fileReader);
        for (Object object : jsonArray) {
            String plantName = (String) object;
            Plant plant = Plant.getPlantByName(plantName.toLowerCase());

            if (plant == null) {
                throw new Exception("bug in addFirstPlants method: " + plantName + " doesn't exist");
            }
            Plant.addFirstPlant(plant);
        }
        fileReader.close();
        fileReader = new FileReader("JSON/plantInZombieMode");
        jsonArray = (JSONArray) (new JSONParser()).parse(fileReader);
        for (Object object : jsonArray) {
            String plantName = (String) object;
            Plant plant = Plant.getPlantByName(plantName.toLowerCase());

            DryModeAvailablePlantName.add(plantName.toLowerCase());

            if (plant == null) {
                throw new Exception("bug in addFirstPlants method: " + plantName + " doesn't exist");
            }
        }
        fileReader.close();
        fileReader = new FileReader("JSON/plantInZombieModeWet");
        jsonArray = (JSONArray) (new JSONParser()).parse(fileReader);
        for (Object object : jsonArray) {
            String plantName = (String) object;
            Plant plant = Plant.getPlantByName(plantName.toLowerCase());
            WetModeAvailablePlantName.add(plantName.toLowerCase());

            if (plant == null) {
                throw new Exception("bug in addFirstPlants method: " + plantName + " doesn't exist");
            }
        }
        for (int i = 0; i < 3; i++) {
            if (!WetModeAvailablePlantName.get(i).equals("Lily Pad")) {
                for (int j = i + 1; j < WetModeAvailablePlantName.size(); j++) {
                    if (WetModeAvailablePlantName.get(j).equals("Lily Pad")) {
                        WetModeAvailablePlantName.set(j, WetModeAvailablePlantName.get(i));
                        WetModeAvailablePlantName.set(i, "Lily Pad");
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < WetModeAvailablePlantName.size(); i++) {
            System.out.println(WetModeAvailablePlantName.get(i));
        }
        fileReader.close();
    }

    private static void addFirstZombies() throws Exception {
        FileReader fileReader = new FileReader("JSON/firstZombies");
        JSONArray jsonArray = (JSONArray) (new JSONParser()).parse(fileReader);
        for (Object object : jsonArray) {
            String zombieName = (String) object;
            Zombie zombie = Zombie.getZombieByName(zombieName.toLowerCase());
            if (zombie == null) {
                throw new Exception("bug in addFirstZombies method: " + zombieName + " doesn't exist");
            }
            Zombie.addFirstZombie(zombie);
        }
        fileReader.close();
    }

    private static void addAllPlants() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Plant/").listFiles())) {
            if (file.isFile()) {
                new Plant(new JSONHandler(file));
            }
        }
        addLilyPadClass();
        addMagnetShroomClass();
        addMineClass();
        addShooterClass();
        addSunFlowerClass();
        addFirstPlants();
    }

    private static void addDoubleSidedGunClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Gun/DoubleSidedGun").listFiles())) {
            if (file.isFile()) {
                new DoubleSidedGun(new JSONHandler(file));
            }
        }
    }

    private static void addThreeRowGunClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Gun/ThreeRowGun").listFiles())) {
            if (file.isFile()) {
                new ThreeRowGun(new JSONHandler(file));
            }
        }
    }

    private static void addAllGuns() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Gun/").listFiles())) {
            if (file.isFile()) {
                new Gun(new JSONHandler(file));
            }
        }
        addDoubleSidedGunClass();
        addThreeRowGunClass();
    }

    private static void addAllShield() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Shield/").listFiles())) {
            if (file.isFile()) {
                new Shield(new JSONHandler(file));
            }
        }
    }

    private static void addAllZombies() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/Zombie/").listFiles())) {
            if (file.isFile()) {
                JSONHandler jsonHandler = new JSONHandler(file);
                jsonHandler.put(FieldNames.price, jsonHandler.getInt(FieldNames.fullHp) * 5L);
                new Zombie(jsonHandler);
            }
        }
        addFirstZombies();
    }

    private static void addAllUsers() throws Exception {
        JSONHandler usersJsonHandler = new JSONHandler(new File(GameData.usersJSONFilePath));
        JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
        for (Object object : jsonArray) {
            JSONObject userJsonObject = (JSONObject) object;
            User user = new User((String) userJsonObject.get(FieldNames.username.name()),
                    (String) userJsonObject.get(FieldNames.password.name()), null);
            user.setKillingEnemyCount(((Long) userJsonObject.get(FieldNames.killingEnemyCount.name())).intValue());
            user.setCoinForShop(((Long) userJsonObject.get(FieldNames.coinForShop.name())).intValue());
            JSONArray unlockedCreaturesJsonArray =
                    (JSONArray) userJsonObject.get(FieldNames.unlockedCreatures.name());
            for (Object creatureObject : unlockedCreaturesJsonArray) {
                String creatureName = (String) creatureObject;
                Creature creature = Creature.getCreatureByName(creatureName);
                if (creature == null) {
                    throw new Exception("bug in addAllUser method");
                }
                user.getUnlockedCreatures().add(creature);
                creature.setRemainInShop(creature.getRemainInShop()-1);
            }
            if (userJsonObject.get(FieldNames.imageAddress.name()) != null)
                user.setImageAddress((String) userJsonObject.get(FieldNames.imageAddress.name()));
        }
    }

    private static void addAllMessages() throws Exception {
        JSONHandler messagesJsonHandler = new JSONHandler(new File(GameData.messagesJSONFilePath));
        JSONArray jsonArray = (JSONArray) messagesJsonHandler.getFromJSONObject(FieldNames.messages);
        for (Object object : jsonArray) {
            JSONObject messageJsonObject = (JSONObject) object;
            String sender = (String) messageJsonObject.get(Chat.FieldNames.senderUsername.name());
            String receiver = (String) messageJsonObject.get(Chat.FieldNames.receiverUsername.name());
            Message message;
            if (receiver.equals("GlobalChat")) {
                message = new Message((String) messageJsonObject.get(Chat.FieldNames.content.name()),
                        User.getUserByUsername(sender),
                        null,
                        ((String) messageJsonObject.get(Chat.FieldNames.photoPath.name())),
                        ((Long) messageJsonObject.get(Chat.FieldNames.id.name())).intValue()
                );
            }
            else {
                message = new Message((String) messageJsonObject.get(Chat.FieldNames.content.name()),
                        User.getUserByUsername(sender),
                        User.getUserByUsername(receiver),
                        ((String) messageJsonObject.get(Chat.FieldNames.photoPath.name())),
                        ((Long) messageJsonObject.get(Chat.FieldNames.id.name())).intValue()
                );
            }
            if (messageJsonObject.containsKey("repliedId")) {
                int repliedId = ((Long) messageJsonObject.get("repliedId")).intValue();
                message.setRepliedMessage(Message.getMessageById(repliedId));
            }
        }
    }
    static HashMap<String,String> className=new HashMap<String,String>();
    public static String undoLowerCase(String name){
        if(className.containsKey(name)){
            return className.get(name);
        }
        return null;
    }
    private static void lowerCaseClassName(){
        String[] allClassName={"sa ","ba"};
        for(String s:allClassName){

        }
    }
    public static void run() throws Exception {
        addAllShield();
        addAllGuns();
        addAllPlants();
        addAllZombies();
        addAllUsers();
        addAllMessages();
        lowerCaseClassName();
        AIConnection = new Connection(User.getUserByUsername("AI User"));
    }
}