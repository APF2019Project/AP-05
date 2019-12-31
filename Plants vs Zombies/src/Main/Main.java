package Main;

import Command.LoginCommandHandler;
import Command.MainMenuCommandHandler;
import Command.ProfileCommandHandler;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // in do func baraye fazaye baadi bayad styleshoon kolan avaz she
    public static String scanLine() {
        String line = scanner.nextLine();
        clearScreen();
        return line;
    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static void clearScreen() {
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
    }

    public static void main(String[] args) throws Exception {
        try{
            GameData.run();
            Menu loginMenu = new Menu(null, new LoginCommandHandler());
            loginMenu.run();
        }catch (Exception e){
            e.printStackTrace();
        }
        User.saveAllUsers();
    }
}
