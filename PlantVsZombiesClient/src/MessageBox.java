import javafx.scene.control.Alert;

public class MessageBox {
    static void showError(String message) {
        System.err.println(message);
        try {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setHeaderText(message);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static void showErrorAndExit(String message) {
        System.err.println(message);
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(message);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.exit(-1);
    }
}
