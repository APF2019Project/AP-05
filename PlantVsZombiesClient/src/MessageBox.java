import javafx.scene.control.Alert;

public class MessageBox {
    static void showError(String message) {
        show(message, Alert.AlertType.WARNING);
    }

    static void show(String message, Alert.AlertType alertType){
        System.err.println(message);
        try {
            Alert alert = new Alert(alertType);
            alert.setTitle("Message");
            alert.setHeaderText(message);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static void showErrorAndExit(String message) {
        show(message, Alert.AlertType.ERROR);
        System.exit(-1);
    }
}
