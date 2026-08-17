package Login;

import deep_word.DB_deepword;
import deep_word.Shortcuts;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label; 
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DialogUtil;



public class LoginController implements Initializable {

    @FXML
    private TextField user_tf; // إدخال اسم المستخدم

    @FXML
    private PasswordField pass_tf; // إدخال كلمة المرور

    @FXML
    private Label massge_logen; // هذا هو العنصر الذي يعرض الرسائل للمستخدم

    @FXML
    private Button login_btn;

    @FXML
    private Button register_btn;
    @FXML
    private MFXButton exit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater(() -> {
            
            Node any = login_btn; 
            if (any != null && any.getScene() != null) {
                var root = any.getScene().getRoot();
                var stage = (Stage) any.getScene().getWindow();

               
                Shortcuts.installExitOnEsc(root);

                
                Shortcuts.installEnterFires(root, login_btn);

                
                Shortcuts.makeDefault(login_btn);

                
                Shortcuts.hookWindowCloseConfirmation(stage);
            }
        });
    }

    @FXML
    private void register_btn(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register/Register.fxml"));
        Parent root = loader.load();

        
        Stage registerStage = new Stage();
        registerStage.setScene(new Scene(root));
        registerStage.show();

        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void login_btn(ActionEvent event) {
        String username = user_tf.getText();
        String password = pass_tf.getText();

        if (username.isEmpty() || password.isEmpty()) {
            massge_logen.setText(" أدخل اسم المستخدم وكلمة المرور");
            return;
        }

        try {
            Connection conn = DB_deepword.getConnection();
            if (conn == null) {
                massge_logen.setText(" لا يمكن الاتصال بقاعدة البيانات");
                return;
            }

            String query = "SELECT * FROM users WHERE users = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ المستخدم موجود
                if (username.equals("admin") && password.equals("admin")) {
                    // ️ فتح صفحة  Admin
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/adminpage.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    // ️ فتح الصفحة العادية للمستخدم
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/home.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } else {
                massge_logen.setText("❌ اسم المستخدم أو كلمة المرور غير صحيحة");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            massge_logen.setText("❌ حدث خطأ أثناء تسجيل الدخول");
        }
    }

    @FXML
    private void exit(ActionEvent event) {
    
        Shortcuts.requestExit((Node) event.getSource());

       
    }

    @FXML
    private void Forgot_Password(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/restpasswerd/restpass.fxml"));
        Parent root = loader.load();

        
        Stage restpassStage = new Stage();
        restpassStage.setScene(new Scene(root));
        restpassStage.show();
        
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
