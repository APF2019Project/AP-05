import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Solver implements Runnable {
    private ArrayList<File> input;
    private String destination;
    private int remain;

    Solver(String destination, ArrayList<File> input, int remain) {
        this.input = input;
        this.destination = destination;
        this.remain = remain;
    }

    public void run() {
        ArrayList<Integer> allNumber = new ArrayList<>();
        for (File file : input) {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    if (number % 2 == remain) {
                        allNumber.add(number);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(allNumber);
        StringBuilder data = new StringBuilder();
        for (int number : allNumber) {
            data.append(number);
            data.append(' ');
        }
        try {
            Files.write(Paths.get(destination), data.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<File> input = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            input.add(new File(scanner.nextLine()));
        }

        Solver even = new Solver(scanner.nextLine(), input, 0);
        Solver odd = new Solver(scanner.nextLine(), input, 1);

        Thread t1 = new Thread(even);
        Thread t2 = new Thread(odd);
        t1.start();
        t2.start();
    }
}
