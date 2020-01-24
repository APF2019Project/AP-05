import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
        Label label=new Label();
        label.setText("frehwui");
        Node[] nodes=new Node[]{
                new TextField(),
                new PasswordField(),
        };
        InputMenuPane inputMenuPane=new InputMenuPane(nodes);
        pane.getChildren().add(inputMenuPane);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
