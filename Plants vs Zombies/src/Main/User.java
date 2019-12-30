package Main;

import Player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class User {
    static private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Creature> unlockedCreatures = new ArrayList<>();
    private int coinForShop = 0;
    private String username, password;
    private int killingEnemyCount;
    private Player player;

    static {
        try {
            JSONHandler usersJsonHandler = new JSONHandler(new File(GameData.usersJSONFilePath));
            JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
            for (Object object : jsonArray) {
                JSONObject userJsonObject = (JSONObject) object;
                User user = new User((String) userJsonObject.get(FieldNames.username.name()),
                        (String) userJsonObject.get(FieldNames.password.name()));
                user.setKillingEnemyCount(((Long) userJsonObject.get(FieldNames.killingEnemyCount.name())).intValue());
                user.setCoinForShop(((Long) userJsonObject.get(FieldNames.coinForShop.name())).intValue());
                JSONArray unlockedCreaturesJsonArray =
                        (JSONArray) userJsonObject.get(FieldNames.unlockedCreatures.name());
                for (Object creatureObject : unlockedCreaturesJsonArray) {
                    String creatureName = (String) creatureObject;
                    user.unlockedCreatures.add(Creature.getCreatureByName(creatureName));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static void saveAllUsers() throws Exception {
        JSONArray usersJsonArray = new JSONArray();
        for (User user : allUsers) {
            JSONObject userJsonObject = new JSONObject();
            userJsonObject.put(FieldNames.username.name(), user.getUsername());
            userJsonObject.put(FieldNames.password.name(), user.getPassword());
            JSONArray unlockedCreaturesJsonArray = new JSONArray();
            for (Creature unlockedCreature : user.unlockedCreatures) {
                unlockedCreaturesJsonArray.add(unlockedCreature.getName());
            }
            userJsonObject.put(FieldNames.unlockedCreatures.name(), unlockedCreaturesJsonArray);
            userJsonObject.put(FieldNames.killingEnemyCount.name(), user.getKillingEnemyCount());
            userJsonObject.put(FieldNames.coinForShop.name(), user.getCoinForShop());
            usersJsonArray.add(userJsonObject);
        }
        new JSONHandler(new File(GameData.usersJSONFilePath)).set(FieldNames.users, usersJsonArray);
    }

    public User(String username, String password) throws Exception {
        if (!validNewUsername(username) || !validNewPassword(password)) {
            throw new Exception("username or password invalid");
        }
        this.username = username;
        this.password = password;
        allUsers.add(this);
    }

    public static User login(String username, String password) throws Exception {
        for (User user : allUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new Exception("username of password is invalid");
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

    public Creature getUnlockedCreatureByName(String creatureName) {
        for (Creature creature : unlockedCreatures) {
            if (creature.getName().equals(creatureName)) {
                return creature;
            }
        }
        return null;
    }

    public void setKillingEnemyCount(int killingEnemyCount) throws Exception {
        this.killingEnemyCount = killingEnemyCount;
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

    public ArrayList<Creature> getUnlockedCreatures() {
        return unlockedCreatures;
    }
}
