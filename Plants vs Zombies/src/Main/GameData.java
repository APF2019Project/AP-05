package Main;

import java.util.ArrayList;

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
    public final static int mapRowCount = 6;
    public final static int mapColCount = 10;
    public final static boolean[] isWaterInWaterMapMode = {false, false, true, true, false, false};
    public final static boolean[] isWaterInDayMapMode = {false, false, false, false, false, false};
    private static User AIUser = null;
    static public ArrayList<String>DryModeAvailablePlantName;
    static public ArrayList<String> WetModeAvailablePlantName;
    static public int inf=100000000;

    static {
        try {
            AIUser = new User("AI User","Some Strong Password!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getAIUser(){
        return AIUser;
    }

    public void readData() {

    }
}