package Main;

import Player.Player;

import java.util.ArrayList;

public class User {
    static private ArrayList<User> allUsers=new ArrayList<>();
    private ArrayList<Creature> unlockedCreatures=new ArrayList<>();
    private int coin = 0;
    private String username, password;
    private int killingEnemyCount;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
        this.username = username;
    }

    public void changePassword(String password) throws Exception {
        if (!validNewPassword(password)) {
            throw new Exception("password invalid");
        }
        this.password = password;
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
