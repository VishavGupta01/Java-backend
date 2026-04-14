import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class boilerplateJDBC {
    public static void main(String[] args) {
        Connection connect = null;
        PreparedStatement statement = null;
        Scanner scan = new Scanner(System.in);

        try {
            connect = jdbcUtil.getConnection();
            String query = ""; // With Placeholders
            statement = connect.prepareStatement(query);

            // Input and setting placeholders with userInput

            int rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                jdbcUtil.closeResources(connect, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}