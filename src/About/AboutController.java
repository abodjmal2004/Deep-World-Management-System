package About;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.awt.Desktop;
import java.net.URI;

public class AboutController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    
    @FXML
    private void updat_btn(MouseEvent event) {
        openSimpleUrl("https://t.me/G0C_C");
    }

    
    @FXML
    private void sport_btn(MouseEvent event) {
        openSimpleUrl("https://t.me/xw_25aa"); 
    }

    
    private void openSimpleUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
