import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Main extends Application {
    public static int cnt = 0;

    public static String getImageAddressByCreatureName(String name) {
        File directory = new File("../Gallery/");
        for (File dir : directory.listFiles()) {
            if (dir.getName().toLowerCase().equals(name)) {
                for (File file : dir.listFiles()) {
                    if (file.exists() && file.getName().contains("HD")) {
                        System.out.println(file.getPath());
                        return file.toURI().toString();
                    }
                }
            }
        }
        for (File dir : directory.listFiles()) {
            if (dir.getName().toLowerCase().equals(name)) {
                for (File file : dir.listFiles()) {
                    if (file.exists()) {
                        System.out.println(file.getPath());
                        return file.toURI().toString();
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuHandler.setStage(primaryStage);
        MediaPlayer.playBackgroundMusic();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(windowEvent -> {
            try {
                MenuHandler.getClient().sendExit();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaPlayer.closeBackgroundMusic();
            System.exit(0);
        });
    }
}
