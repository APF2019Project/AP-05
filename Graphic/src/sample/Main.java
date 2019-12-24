package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 700, 800);

        Label label = new Label("HI");
        label.relocate(200, 200);
        label.setTextFill(Color.rgb(0, 250, 83));
        label.setFont(Font.font(50));
        root.getChildren().add(label);

        Circle circle = new Circle(100);
        circle.relocate(200, 200);
        circle.setFill(Color.rgb(0, 0, 100));
        root.getChildren().add(circle);

        Button button = new Button("button");
        root.getChildren().add(button);
        button.setOnMouseClicked(mouseEvent -> circle.setFill(Color.BROWN));

        Rectangle rectangle = new Rectangle(300, 300, 300, 300);
        root.getChildren().add(rectangle);

        KeyValue keyValue = new KeyValue(rectangle.xProperty(), 0);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Label timer = new Label("0");
        timer.relocate(500, 0);
        AnimationTimer animationTimer = new AnimationTimer() {
            private Long lastTime = 0L;
            private double time = 0;
            private long second = 1000000000;
            @Override
            public void handle(long l) {
                if(lastTime == 0)
                    lastTime = l;

                if(lastTime + second < l) {
                    lastTime = l;
                    time += 1;

                    timer.setText(Integer.toString((int)time));
                }
            }
        };
        root.getChildren().add(timer);
        animationTimer.start();
        root.getChildren().clear();

        int n = 5;
        Circle[] circles = new Circle[n];
        Random random = new Random();

        int[] vx = new int[n], vy = new int[n];

        for(int i = 0; i < n; i++) {
            circles[i] = new Circle(random.nextInt(500), random.nextInt(500), random.nextInt(30));
            circles[i].setFill(Color.rgb(random.nextInt(250), random.nextInt(250), random.nextInt(250)));
            vx[i] = random.nextInt(10);
            vy[i] = random.nextInt(10);
        }

        root.getChildren().addAll(circles);

        ArrayList<String> keys = new ArrayList<>();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String code = keyEvent.getCode().toString();
                if(!keys.contains(code))
                    keys.add(code);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String code = keyEvent.getCode().toString();
                keys.remove(code);
            }
        });

        new AnimationTimer() {
            private Long lastTime = 0L;
            private long time = 0;
            private long second = 1000000000;
            @Override
            public void handle(long l) {
                if (lastTime == 0)
                    lastTime = l;

                if (lastTime + second / 100 < l) {
                    lastTime = l;

                    for (int i = 0; i < n; i++) {
                        if(keys.contains("Y")) {
                            int x = ((int) circles[i].getCenterX() + vx[i]) % 500;
                            int y = ((int) circles[i].getCenterY() + vy[i]) % 500;

                            circles[i].setCenterX(x);
                            circles[i].setCenterY(y);
                        }
                    }
                }
            }
        }.start();

        button = new Button("change");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Group root = new Group();
                Scene scene = new Scene(root, 300, 300);
                Label label = new Label("HEY CHANGED");
                root.getChildren().add(label);

                Button back = new Button("back");
                back.relocate(150, 150);
                root.getChildren().add(back);

                back.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        });

        button.setFont(Font.font(50));
        root.getChildren().add(button);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
