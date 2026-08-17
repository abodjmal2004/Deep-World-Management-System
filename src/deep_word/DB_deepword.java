package deep_word;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB_deepword {

   
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/deep_word?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; 

    
    private static final Logger logger = Logger.getLogger(DB_deepword.class.getName());


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ تم الاتصال بقاعدة البيانات بنجاح");
        } catch (SQLException e) {
            System.out.println("❌ فشل الاتصال بقاعدة البيانات");
            logger.log(Level.SEVERE, "❌ فشل الاتصال بقاعدة البيانات", e);
        }
        return conn;
    }

    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ تم غلق الاتصال بقاعدة البيانات");
            } catch (SQLException e) {
                System.out.println("❌ فشل في غلق الاتصال بقاعدة البيانات");
                logger.log(Level.SEVERE, "❌ فشل في غلق الاتصال بقاعدة البيانات", e);
            }
        }
    }
}
