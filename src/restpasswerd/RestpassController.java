
package restpasswerd;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import deep_word.DB_deepword;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RestpassController implements Initializable {

    @FXML
    private MFXButton exit;
    @FXML
    private TextField userre_tf;            // اسم المستخدم
    @FXML
    private PasswordField newpassre_tf;     // كلمة المرور الجديدة
    @FXML
    private Button register_btn;            // زرّ التأكيد
    @FXML
    private PasswordField conpassrenew_tf;  // تأكيد كلمة المرور
    @FXML
    private Label lbl_mssge;                // لعرض الرسائل

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMessage("", "info");
    }

    @FXML
    private void exit(ActionEvent event) {
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registerre_btn(ActionEvent event) {
        String username = safe(getText(userre_tf));
        String newPass  = safe(getText(newpassre_tf));
        String confirm  = safe(getText(conpassrenew_tf));

        
        if (isEmpty(username) || isEmpty(newPass) || isEmpty(confirm)) {
            setMessage("⚠️ الرجاء تعبئة جميع الحقول.", "warn");
            return;
        }
        if (!newPass.equals(confirm)) {
            setMessage("❌ تأكيد كلمة المرور غير متطابق.", "error");
            return;
        }
        
        if (newPass.length() < 4) {
            setMessage("⚠️ كلمة المرور الجديدة قصيرة جدًا (الحد الأدنى المقترح 4 أحرف).", "warn");
            return;
        }

        final String CHECK_USER_SQL = "SELECT 1 FROM users WHERE users = ? LIMIT 1";
        final String UPDATE_SQL     = "UPDATE users SET password = ? WHERE users = ?";

        try (Connection conn = DB_deepword.getConnection()) {
            if (conn == null) {
                setMessage("❌ فشل الاتصال بقاعدة البيانات.", "error");
                return;
            }

            
            boolean userExists = false;
            try (PreparedStatement ps = conn.prepareStatement(CHECK_USER_SQL)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    userExists = rs.next();
                }
            }

            if (!userExists) {
                setMessage("⚠️ اسم المستخدم غير موجود.", "warn");
                return;
            }

            
            try (PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
                ps.setString(1, newPass);
                ps.setString(2, username);
                int updated = ps.executeUpdate();
                if (updated == 1) {
                    setMessage("✅ تم تغيير كلمة المرور بنجاح.", "success");
                    clearInputs();
                } else {
                    setMessage("❌ لم يتم التحديث. حاول لاحقًا.", "error");
                }
            }

        } catch (SQLException ex) {
            
            ex.printStackTrace();
            setMessage("❌ حدث خطأ أثناء تحديث كلمة المرور.", "error");
        }
    }

    

    private static String safe(String s) { return s == null ? "" : s.trim(); }
    private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }

    private static String getText(TextField tf) {
        return tf == null ? "" : tf.getText();
    }
    private static String getText(PasswordField pf) {
        return pf == null ? "" : pf.getText();
    }

    private void clearInputs() {
        if (userre_tf != null) userre_tf.clear();
        if (newpassre_tf != null) newpassre_tf.clear();
        if (conpassrenew_tf != null) conpassrenew_tf.clear();
    }

    
    private void setMessage(String msg, String level) {
        if (lbl_mssge == null) return;
        lbl_mssge.setText(msg);

        
        String color;
        switch (safe(level).toLowerCase()) {
            case "success": color = "#188038"; break; // أخضر
            case "error":   color = "#B00020"; break; // أحمر
            case "warn":    color = "#C26400"; break; // برتقالي
            default:        color = "#202124"; break; // رمادي/أسود افتراضي
        }
        lbl_mssge.setStyle("-fx-text-fill: " + color + ";");
    }

    @FXML
    private void Login_btn(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
        Parent root = loader.load();

        
        Stage registerStage = new Stage();
        registerStage.setScene(new Scene(root));
        registerStage.show();

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        
    }
}

