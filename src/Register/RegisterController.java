package Register;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

    @FXML
    private Button register_btn;
    @FXML
    private MFXButton exit;
    @FXML
    private TextField userre_tf;
    @FXML
    private PasswordField passre_tf;
    @FXML
    private TextField emailre_tf;
    @FXML
    private PasswordField conpassre_tf;
    @FXML
    private Label massge_labl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // لا شيء هنا الآن
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void registerre_btn(ActionEvent event) throws IOException, SQLException {
        String username = userre_tf.getText();
        String email = emailre_tf.getText();
        String password = passre_tf.getText();
        String confirmPassword = conpassre_tf.getText();

        // ✅ تحقق أن كل الحقول ممتلئة
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            massge_labl.setText("⚠️ الرجاء تعبئة جميع الحقول");
            return;
        }

        // ✅ تحقق أن كلمة المرور وتأكيدها متطابقان
        if (!password.equals(confirmPassword)) {
            massge_labl.setText("❌ كلمة المرور وتأكيدها غير متطابقين");
            return;
        }

        try {
            Connection conn = deep_word.DB_deepword.getConnection();
            if (conn == null) {
                massge_labl.setText("❌ فشل الاتصال بقاعدة البيانات");
                return;
            }

            // ✅ تحقق إذا كان اسم المستخدم موجود مسبقًا
            String checkQuery = "SELECT * FROM users WHERE users = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                massge_labl.setText("⚠️ اسم المستخدم موجود مسبقًا");
            } else {
                // ✅ إدخال المستخدم الجديد
                String insertQuery = "INSERT INTO users (users, emil, password) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, username);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password); // كلمة المرور غير مشفرة الآن

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    // ✅ تم التسجيل بنجاح - ننتقل إلى صفحة تسجيل الدخول
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
                    Parent root = loader.load();
                    Stage loginStage = new Stage();
                    loginStage.setScene(new Scene(root));
                    loginStage.show();

                    // ❌ إغلاق نافذة التسجيل
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                } else {
                    massge_labl.setText("❌ فشل في إنشاء الحساب");
                }

                insertStmt.close();
            }

            rs.close();
            checkStmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            massge_labl.setText("❌ حدث خطأ أثناء عملية التسجيل");
        }
    }

    private void clearFields() {
        userre_tf.clear();
        emailre_tf.clear();
        passre_tf.clear();
        conpassre_tf.clear();
    }

    @FXML
    private void login_btn(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
        Parent root = loader.load();

        // 2. فتح نافذة جديدة للتسجيل
        Stage registerStage = new Stage();
        registerStage.setScene(new Scene(root));
        registerStage.show();

        // 3. إغلاق نافذة تسجيل الدخول الحالية (Login)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        
    }
}
