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
    private static JSONHandler usersJsonHandler;

    static {
        try {
            usersJsonHandler = new JSONHandler(new File(GameData.usersJSONFilePath));
            JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
            for (Object object : jsonArray) {
                JSONObject userJsonObject = (JSONObject) object;
                new User((String) userJsonObject.get(FieldNames.username.name()),
                        (String) userJsonObject.get(FieldNames.password.name()), true);
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

    public User(String username, String password, boolean addFromFile) throws Exception {
        this.username = username;
        this.password = password;
        allUsers.add(this);
    }

    public User(String username, String password) throws Exception {
        if (!validNewUsername(username) || !validNewPassword(password)) {
            throw new Exception("username or password invalid");
        }
        JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(FieldNames.username, username);
        jsonObject.put(FieldNames.password, password);
        jsonArray.add(jsonObject);
        usersJsonHandler.set(FieldNames.users, jsonArray);
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
        JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
        int index = getIndexOfUserInFile(username);
        if (index == -1) {
            throw new Exception("bug in User class deleteUser method");
        }
        jsonArray.remove(index);
        usersJsonHandler.set(FieldNames.users, jsonArray);
        allUsers.remove(user);
    }

    private static void addUser(User user) {
        allUsers.add(user);
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

    public static boolean usernameIsValid(String username) {
        return getUserByUsername(username) != null && !username.contains(" ");
    }

    public Creature getUnlockedCreatureByName(String creatureName) {
        for (Creature creature : unlockedCreatures) {
            if (creature.getName().equals(creatureName)) {
                return creature;
            }
        }
        return null;
    }

    public boolean buyCreatureFromShop(Creature creature) {
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

    public static int getIndexOfUserInFile(String username) throws Exception {
        JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject userJsonObject = (JSONObject) jsonArray.get(i);
            String usernameOfIthUser = (String) userJsonObject.get(FieldNames.username.name());
            if (usernameOfIthUser.equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public void setUsername(String username) throws Exception {
        JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
        int index = getIndexOfUserInFile(this.username);
        if (index == -1) {
            throw new Exception("bug in User class setUsername method");
        }
        JSONObject userJsonObject = (JSONObject) jsonArray.get(index);
        String usernameOfIthUser = (String) userJsonObject.get(FieldNames.username.name());
        this.username = username;
        userJsonObject.put(FieldNames.username.name(), username);
        jsonArray.set(index, userJsonObject);
        usersJsonHandler.set(FieldNames.users, jsonArray);
    }

    public int getCoinForShop() {
        return coinForShop;
    }

    public void setCoinForShop(int coinForShop) {
        this.coinForShop = coinForShop;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        JSONArray jsonArray = (JSONArray) usersJsonHandler.getFromJSONObject(FieldNames.users);
        int index = getIndexOfUserInFile(this.username);
        if (index == -1) {
            throw new Exception("bug in User class setPassword method");
        }
        JSONObject userJsonObject = (JSONObject) jsonArray.get(index);
        String usernameOfIthUser = (String) userJsonObject.get(FieldNames.username.name());
        this.password = password;
        userJsonObject.put(FieldNames.password.name(), password);
        jsonArray.set(index, userJsonObject);
        usersJsonHandler.set(FieldNames.users, jsonArray);
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

    public void UnlockCreature(Creature creature) {
    }
}
