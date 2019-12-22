import java.util.ArrayList;

public class User {
    static private ArrayList<User> allUsers;
    private ArrayList<Creature> unlockedCreatures;
    private int coin = 0;
    private String username, password;
    private Menu currentMenu = Main.loginMenu;

    public static User login(String username,String password) throws Exception {
        for(User user:allUsers){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        throw new Exception("username of password is invalid");
    }

    public User(String username, String password) throws Exception {
        if(getUserByUsername(username) != null) {
            throw new Exception("username invalid");
        }
        this.username = username;
        this.password = password;
        allUsers.add(this);
    }

    private static void addUser(User user){
        allUsers.add(user);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public static boolean usernameIsValid(String username) {
        return getUserByUsername(username) != null && !username.contains(" ");
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Creature> getUnlockedCreatures() {
        return unlockedCreatures;
    }

    public void UnlockCreature(Creature creature) {
    }
}
