import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        return DriverManager.getConnection(url, username, password);
    }

    public static void closeResources(Connection connect, Statement statement) throws SQLException{
        connect.close();
        statement.close();
    }
}
