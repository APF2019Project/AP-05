import javafx.scene.layout.Pane;

public class MenuItemPane extends Pane {
    private String name, help;

    MenuItemPane(String name, String help, InputMenuPane inputMenuPane) {
        super();
        this.name=name;
        this.help=help;
        inputMenuPane.setText(name);
        if (inputMenuPane != null) {
        }
    }


}
