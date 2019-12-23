public abstract class Player {
    private static Player currentPlayer;
    private int sunInGame;
    private User user;

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
    }

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setCurrentMenu(Menu menu){
        user.setCurrentMenu(menu);
    }

    abstract void doAction();
    public void addSun(int sun){
        this.sunInGame+=sun;
    }
}
