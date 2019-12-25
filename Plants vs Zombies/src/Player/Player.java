package Player;

import Main.Creature;
import Main.Menu;
import Main.User;

import java.util.ArrayList;

public abstract class Player {
    private static Player currentPlayer;
    private int sunInGame;
    private User user;
    private ArrayList<Creature> creaturesOnHand = new ArrayList<>();

    public int getSunInGame() {
        return sunInGame;
    }

    public void setSunInGame(int sunInGame) {
        this.sunInGame = sunInGame;
    }

    public Player(User user) {
        this.user = user;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
    }

    public void addCreaturesOnHand(Creature creature) {
        creaturesOnHand.add(creature);
    }

    public void removeCreaturesOnHand(Creature creature) {
        creaturesOnHand.remove(creature);
    }

    public ArrayList<Creature> getCreaturesOnHand() {
        return creaturesOnHand;
    }

    public User getUser() {
        return user;
    }

    public void setCurrentMenu(Menu menu) throws Exception {
        user.setCurrentMenu(menu);
    }

    abstract void doAction();

    abstract void pickCards();

    public void setMeCurrentPlayer(){
        setCurrentPlayer(this);
    }

    public void addSun(int sun) {
        this.sunInGame += sun;
    }
}
