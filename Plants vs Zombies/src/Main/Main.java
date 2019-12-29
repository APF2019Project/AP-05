package Main;

import Command.LoginCommandHandler;
import Command.MainMenuCommandHandler;
import Command.ProfileCommandHandler;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // in do func baraye fazaye baadi bayad styleshoon kolan avaz she
    public static String scanLine() {
        return scanner.nextLine();
    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) throws Exception {
        GameData.run();
        Menu loginMenu = new Menu(null, new LoginCommandHandler());
        loginMenu.run();
    }
}
