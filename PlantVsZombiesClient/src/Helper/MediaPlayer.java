package Helper;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import java.io.IOException;
import java.net.URISyntaxException;

public class MediaPlayer {
    private static Player audioPlayer;

    public static void playBackgroundMusic() throws CannotRealizeException, IOException, NoPlayerException, URISyntaxException {
        audioPlayer = Manager.createRealizedPlayer(MediaPlayer.class.getResource("../Files/Crazy Dave.wav"));
        audioPlayer.start();
    }

    public static void closeBackgroundMusic() {
        audioPlayer.stop();
        audioPlayer.close();
    }
}
