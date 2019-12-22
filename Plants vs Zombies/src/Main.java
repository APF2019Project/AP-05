import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static User currentUser;

    public static Menu loginMenu=new Menu(null,new loginCommandHandler());
    public static Menu mainMenu=new Menu(loginMenu,new MainMenuCommandHandler());
    public static Menu leaderboard=;
    public static Menu profileMenu;
    public static Menu playMenu;
    public static Menu collectionMenu;
    public static Menu shopMenu;
    public static Menu dayAndWaterMenu;
    public static Menu railMenu;
    public static Menu zombieMenu;
    public static Menu pvpMenu;

    public static String scanLine() {
        return scanner.nextLine();
    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) {

    }
}
