import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.concurrent.TimeUnit;

public class PrettyLabel {

    final static int cycleCount = 80, sleepTime = 50;

    public static Double askStop(int value) {
        if (cycleCount <= value) {
            value = 2 * cycleCount - value;
        }
        return value / (double) (cycleCount);
    }

    public static void playBackgroundColorAnimation(Label label) {
        new Thread(() -> {
            for (int i = 0; ; i = (i + 1) % (2 * cycleCount)) {
                LinearGradient linearGradient = new LinearGradient(askStop(i) * 4 / 5.0, askStop(i), askStop(i) * 2 / 5.0, 0.6, true, CycleMethod.REFLECT,
                        new Stop(0, new Color(0 / 255.0, 110 / 255.0, 5 / 255.0, 1)),
                        new Stop(askStop(i), new Color(180 / 255.0, 190 / 255.0, 0 / 255.0, 1)),
                        new Stop(1, new Color(95 / 255.0, 130 / 255.0, 0 / 255.0, 1))
                );
                label.setTextFill(linearGradient);
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }).start();
    }
}
