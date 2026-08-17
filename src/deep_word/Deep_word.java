
package deep_word;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;


public class Deep_word extends Application{

    
    public static void main(String[] args) {
        launch(args);
        
  
        
        
        
    }

    @Override
    public void start(Stage stage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.show();
        
        
       
    }
    
}
