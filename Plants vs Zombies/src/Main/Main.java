package Main;

import Command.LeaderboardCommandHandler;
import Command.LoginCommandHandler;
import Command.MainMenuCommandHandler;
import Command.ProfileCommandHandler;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static Menu loginMenu=new Menu(null,new LoginCommandHandler());
    public static Menu mainMenu=new Menu(loginMenu,new MainMenuCommandHandler());
    public static Menu leaderboard=new Menu(loginMenu, new LeaderboardCommandHandler());
    public static Menu profileMenu=new Menu(mainMenu, new ProfileCommandHandler());
    public static Menu playMenu;
    public static Menu shopMenu;
    public static Menu dayAndWaterGameModeMenu;
    public static Menu railGameModeMenu;
    public static Menu zombieGameModeMenu;
    public static Menu pvpGameModeMenu;


    // in do func baraye fazaye baadi bayad styleshoon kolan avaz she
    public static String scanLine() {
        return scanner.nextLine();
    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) {

    }
}
