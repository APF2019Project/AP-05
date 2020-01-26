import javafx.scene.media.Media;

import javax.media.*;
import java.io.File;
import java.io.IOException;

public class MediaPlayer {
    private static Player audioPlayer;

    public static void playBackgroundMusic() throws CannotRealizeException, IOException, NoPlayerException {
        audioPlayer = Manager.createRealizedPlayer(MediaPlayer.class.getResource("/Files/Crazy Dave.wav"));
        audioPlayer.start();
    }

    public static void closeBackgroundMusic() {
        audioPlayer.stop();
        audioPlayer.close();
    }
}
