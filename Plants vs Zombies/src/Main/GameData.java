package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class GameData {
    static int speedOfGunShot = 3;
    static int shyDistanceLimit;
    static int iceSlowDownTime;
    static int iceSlowDownPercent = 50;
    static int sleepySlowDownTime;
    static int sleepySlowDownPercent = 100;
    static int cactusDamage = 1;
    static int PepperDamage = 1;
    static int maxMagnetRange, MagnetEatingTime;
    static String usersJSONFilePath="JSON/users";
    public final static int mapRowCount = 6;
    public final static int mapColCount = 10;
    public final static boolean[] isWaterInWaterMapMode = {false, false, true, true, false, false};
    public final static boolean[] isWaterInDayMapMode = {false, false, false, false, false, false};
    public final static String positiveNumber = "([0-9]{1,20})";
    private static User AIUser;
    static public ArrayList<String> DryModeAvailablePlantName;
    static public ArrayList<String> WetModeAvailablePlantName;
    static public int inf = 100000000;

    public static User getAIUser() {
        return AIUser;
    }

    private static void readData() {

    }

    private static void addLilyPadClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/plants/lilypad").listFiles())) {
            if (file.isFile()) {
                new LilyPad(new JSONHandler(file));
            } else {
                throw new Exception("there are some unknown folders in json directory");
            }
        }
    }

    private static void addMagnetShroomClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/plants/magnetshroom").listFiles())) {
            if (file.isFile()) {
                new MagnetShroom(new JSONHandler(file));
            }
        }
    }

    private static void addMineClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/plants/mine").listFiles())) {
            if (file.isFile()) {
                new Mine(new JSONHandler(file));
            }
        }
    }
    private static void addShooterClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/plants/shooter").listFiles())) {
            if (file.isFile()) {
                new Shooter(new JSONHandler(file));
            }
        }
    }
    private static void addSunFlowerClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/plants/sunflower").listFiles())) {
            if (file.isFile()) {
                new SunFlower(new JSONHandler(file));
            }
        }
    }

    private static void addAllPlants() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/plants/").listFiles())) {
            if (file.isFile()) {
                new Plant(new JSONHandler(file));
            }
        }
        addLilyPadClass();
        addMagnetShroomClass();
        addMineClass();
        addShooterClass();
        addSunFlowerClass();
    }

    private static void addDoubleSidedGunClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/guns/doublesided").listFiles())) {
            if (file.isFile()) {
                new DoubleSidedGun(new JSONHandler(file));
            }
        }
    }
    private static void addThreeRowGunClass() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/guns/threerow").listFiles())) {
            if (file.isFile()) {
                new ThreeRowGun(new JSONHandler(file));
            }
        }
    }

    private static void addAllGuns() throws Exception {
        for (File file : Objects.requireNonNull(new File("JSON/guns/").listFiles())) {
            if (file.isFile()) {
                new Gun(new JSONHandler(file));
            }
        }
        addDoubleSidedGunClass();
        addThreeRowGunClass();
    }

    public static void run() throws Exception {
        AIUser = User.getUserByUsername("AI User");
        addAllGuns();
        addAllPlants();
    }
}