import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class InputMenu{
    Node[] inputMenuNodes;
    Pane pane;
    public InputMenu(Node[] inputMenuNodes) throws IOException {
        super();
        pane = FXMLLoader.load(getClass().getResource("InputMenuPane.fxml"));
        this.inputMenuNodes = inputMenuNodes;
        GridPane gridPane = (GridPane) pane.lookup("#gridPane");
        for (Node node : inputMenuNodes) {
            gridPane.addColumn(0, node);
        }
        Button button = (Button) pane.lookup("#sendButton");
        button b
    }

    public void setOnSend(EventHandler<MouseEvent> eventHandler) {
        Button button = (Button) pane.lookup("#sendButton");
        button.setOnMouseClicked(eventHandler);
    }

    public void setText(String string) {
        Label label = (Label) pane.lookup("#label");
        label.setText(string);
    }

    public void run(Stage stage){
        Scene scene=stage.getScene();
        stage.setScene(new Scene(pane));
    }
}
