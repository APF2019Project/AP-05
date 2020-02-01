package Main;

import Chat.Message;
import Objects.Creature;
import Objects.Plant;
import Objects.Zombie;
import Player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class User {
    static private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Creature> unlockedCreatures = new ArrayList<>();
    private int coinForShop = 0;
    private String username, password;
    private int killingEnemyCount;
    private Player player;
    private String imageAddress = "../Profile Pictures/profile" + (new Random().nextInt(6) + 1) + ".png";

    public User(String username, String password, Void addFromFile) throws Exception {
        if (!validNewUsername(username) || !validNewPassword(password)) {
            throw new Exception("username or password invalid");
        }
        this.username = username;
        this.password = password;
        allUsers.add(this);
    }

    public User(String username, String password) throws Exception {
        if (!validNewUsername(username) || !validNewPassword(password)) {
            throw new Exception("username or password invalid");
        }
        this.username = username;
        this.password = password;
        addFirstCreatures();
        allUsers.add(this);
        saveAllUsers();
    }

    public static synchronized void saveAllUsers() throws Exception {
        JSONArray usersJsonArray = new JSONArray();
        for (User user : allUsers) {
            JSONObject userJsonObject = new JSONObject();
            userJsonObject.put(FieldNames.username.name(), user.getUsername());
            userJsonObject.put(FieldNames.password.name(), user.getPassword());
            JSONArray unlockedCreaturesJsonArray = new JSONArray();
            for (Creature unlockedCreature : user.getUnlockedCreatures()) {
                unlockedCreaturesJsonArray.add(unlockedCreature.getName());
            }
            userJsonObject.put(FieldNames.unlockedCreatures.name(), unlockedCreaturesJsonArray);
            userJsonObject.put(FieldNames.killingEnemyCount.name(), user.getKillingEnemyCount());
            userJsonObject.put(FieldNames.coinForShop.name(), user.getCoinForShop());
            userJsonObject.put(FieldNames.imageAddress.name(), user.getImageAddress());
            usersJsonArray.add(userJsonObject);
        }
        new JSONHandler(new File(GameData.usersJSONFilePath)).set(FieldNames.users, usersJsonArray);
    }

    public static User login(String username, String password) throws Exception {
        for (User user : allUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new Exception("username or password is invalid");
    }

    public static void deleteUser(String username, String password) throws Exception {
        User user = getUserByUsernameAndPassword(username, password);
        if (user == null) {
            throw new Exception("incorrect username or password");
        }
        allUsers.remove(user);
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public static User getUserByUsernameAndPassword(String username, String password) {
        for (User user : allUsers)
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        return null;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void gameEnded(boolean win) throws Exception {
        killingEnemyCount += player.getKillingEnemyCount();
        if (win) {
            coinForShop += GameData.winReward;
        }
        saveAllUsers();
    }

    private void addFirstCreatures() {
        unlockedCreatures.addAll(Plant.getFirstPlants());
        unlockedCreatures.addAll(Zombie.getFirstZombies());
    }

    public Creature getUnlockedCreatureByName(String creatureName) {
        for (Creature creature : unlockedCreatures) {
            if (creature.getName().equals(creatureName)) {
                return creature;
            }
        }
        return null;
    }

    public boolean buyCreatureFromShop(Creature creature) throws Exception {
        if (getCoinForShop() < creature.getPriceInShop()) {
            return false;
        }
        setCoinForShop(getCoinForShop() - creature.getPriceInShop());
        unlockedCreatures.add(creature);
        return true;
    }

    public ArrayList<Creature> getLockedCreatures() {
        ArrayList<Creature> lockedCreatures = new ArrayList<>();
        for (Creature creature : Creature.getAllCreatures()) {
            if (getUnlockedCreatureByName(creature.getName()) == null) {
                lockedCreatures.add(creature);
            }
        }
        return lockedCreatures;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws Exception {
        this.username = username;
    }

    public int getCoinForShop() {
        return coinForShop;
    }

    public void setCoinForShop(int coinForShop) throws Exception {
        this.coinForShop = coinForShop;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        this.password = password;
    }

    private boolean validNewUsername(String username) {
        return getUserByUsername(username) == null && username.length() >= 3 && username.length() <= 30;
    }

    private boolean validNewPassword(String password) {
        return password.length() >= 3 && password.length() <= 30;
    }

    public void changeUsername(String username) throws Exception {
        if (!validNewUsername(username)) {
            throw new Exception("username invalid");
        }
        setUsername(username);
    }

    public void changePassword(String password) throws Exception {
        if (!validNewPassword(password)) {
            throw new Exception("password invalid");
        }
        setPassword(password);
    }

    public int getKillingEnemyCount() {
        return killingEnemyCount;
    }

    public void setKillingEnemyCount(int killingEnemyCount) throws Exception {
        this.killingEnemyCount = killingEnemyCount;
    }

    public ArrayList<Creature> getUnlockedCreatures() {
        return unlockedCreatures;
    }
}
