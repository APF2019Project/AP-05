import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.concurrent.TimeUnit;

public class PrettyLabel {

    final static int cycleCount = 60, sleepTime = 40, secondCycleCount = 2, scale = 4;

    public static Double askStop(int value) {
        value %= (2 * cycleCount);
        if (cycleCount <= value) {
            value = 2 * cycleCount - value;
        }
        return value / (double) (cycleCount);
    }

    static int labelCount = 0;

    public static void playBackgroundColorAnimation(Label label) {
        labelCount++;
        int labelId = labelCount;
        new Thread(() -> {
            int[] value = {0, 0, 0};
            //0,1,3,2,6,7,5,4
            //1,2,1,4,1,2,1,4
            int[] indexOfValue = new int[]{0, 1, 0, 2, 0, 1, 0, 2};
            int[] changeOfValue = new int[]{1, 1, -1, 1, 1, -1, -1, -1};
            for (int i = 0, j = 0; labelId == labelCount;
                 i = (i + 1) % (secondCycleCount * cycleCount), j = i / cycleCount) {
                LinearGradient linearGradient = new LinearGradient(askStop(i) * 4 / 5.0, askStop(i),
                        askStop(i) * 2 / 5.0, 0.6, true, CycleMethod.REFLECT,
                        new Stop(0, new Color(
                                askStop(value[1]) / 2, 1, askStop(value[0]) * 3 / 4, 1)),
                        new Stop(askStop(i), new Color(
                                askStop(value[1]) / 2, 0.5, askStop(value[0]) * 3 / 4, 0.6)),
                        new Stop(1, new Color(
                                askStop(value[1]) / 2, 0.7, askStop(value[0]) * 3 / 4, 0.8))
                );
                value[indexOfValue[j]] += changeOfValue[j];
                label.setTextFill(linearGradient);
                label.setRotate(askStop(i * 4) * scale * 2 - scale);
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
