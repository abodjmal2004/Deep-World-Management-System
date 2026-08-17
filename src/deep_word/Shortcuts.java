
package deep_word;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Optional;

public final class Shortcuts {
    private Shortcuts() {}

 
    public static boolean confirmExit(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (owner != null) alert.initOwner(owner);
        alert.setTitle(title != null ? title : "تأكيد الخروج");
        alert.setHeaderText(null);
        alert.setContentText(message != null ? message : "هل تريد الخروج من التطبيق؟");

        ButtonType yes = new ButtonType("نعم", ButtonBar.ButtonData.YES);
        ButtonType no  = new ButtonType("لا",  ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> res = alert.showAndWait();
        return res.orElse(no) == yes;
    }

   
    public static void requestExit(Node eventSource) {
        Window w = windowOf(eventSource);
        if (confirmExit(w, null, null)) {
            Platform.exit();
        }
    }

   
    public static void hookWindowCloseConfirmation(Stage stage) {
        if (stage == null) return;
        stage.setOnCloseRequest(e -> {
            boolean ok = confirmExit(stage, null, null);
            if (!ok) e.consume();
        });
    }


    public static void installExitOnEsc(Node root) {
        if (root == null) return;
        root.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                e.consume();
                Window w = windowOf(root);
                if (confirmExit(w, null, null)) Platform.exit();
            }
        });
    }

   
    public static void installEnterFires(Node root, Button... targets) {
        if (root == null || targets == null || targets.length == 0) return;
        root.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                for (Button b : targets) {
                    if (b != null && b.isVisible() && !b.isDisabled()) {
                        e.consume();
                        b.fire();
                        break;
                    }
                }
            }
        });
    }

  
    public static void installEnterAction(Node root, Runnable action) {
        if (root == null || action == null) return;
        root.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                e.consume();
                action.run();
            }
        });
    }


    public static void makeDefault(Button button) {
        if (button != null) button.setDefaultButton(true);
    }

    
    private static Window windowOf(Node n) {
        if (n == null || n.getScene() == null) return null;
        return n.getScene().getWindow();
    }
}

