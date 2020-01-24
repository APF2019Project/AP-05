import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {
    @FXML private TextField username, password;
    @FXML private Label title;

    int i = 0;

    @FXML void initialize(){
        //PrettyLabel.playBackgroundColorAnimation(title);
    }

}
