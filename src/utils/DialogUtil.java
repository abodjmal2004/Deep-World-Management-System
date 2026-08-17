package utils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogUtil {

    //  يظهر رسالة تأكيد الخروج
    public static void showExitDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("تأكيد الخروج");
        alert.setHeaderText(null);
        alert.setContentText("هل تريد الخروج من التطبيق؟");

        ButtonType yes = new ButtonType("نعم");
        ButtonType no = new ButtonType("لا");

        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            Platform.exit();
        }
    }

    public static void enableEscToExit(Scene scene) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
