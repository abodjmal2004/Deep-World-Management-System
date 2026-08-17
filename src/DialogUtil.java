import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import javafx.application.Platform;

public class DialogUtil {

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
        // إذا اختار "لا" لا يحدث شيء
    }
}
