
package Home;

import Login.LoginController;
import PDFLib.PdfliibController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class HomeController implements Initializable {

    @FXML
    private Button home_btn;
    @FXML
    private Button Courses_btn;
    @FXML
    private Button pdf_btn;
    @FXML
    private Button logout_btn;
    @FXML
    private Button about_btn;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    private void join_btn(ActionEvent event) {
   
    System.out.println("تم الضغط على زر Join");
}



    private void login_btn(ActionEvent event) throws IOException {
        
          Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
        Login.LoginController re11 = new LoginController();
        Parent root11 = loader.load();
        stage.setScene(new Scene(root11));
        stage.show();
    }

    private void Courses_btn(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cousrses/coursespag.fxml"));
    Parent root = loader.load();

    
    Scene scene = new Scene(root);
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }

   


    @FXML
    private void logout_btn(ActionEvent event) throws IOException {
   
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
    Parent root = loader.load();

    
    Scene scene = new Scene(root);
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}

    @FXML
    private void home_btn(ActionEvent event) {
        
    }

    @FXML
    private void pdf_btn(ActionEvent event) throws IOException {
       
     FXMLLoader loader = new FXMLLoader(getClass().getResource("/PDFLib/pdfliib.fxml"));
    Parent root = loader.load();

    
    Scene scene = new Scene(root);
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }

    @FXML
    private void Courses_btn(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cousrses/coursespag.fxml"));
    Parent root = loader.load();

    
    Scene scene = new Scene(root);
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }

    @FXML
    private void about_btn(ActionEvent event) throws IOException {
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/About/about.fxml"));
    Parent root = loader.load();

    
    Scene scene = new Scene(root);
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
        
        
    }



    
}
