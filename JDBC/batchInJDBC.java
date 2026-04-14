import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class batchInJDBC { // Used only for Non-Select Queries
    public static void main(String[] args) {
        Connection connect = null;
        PreparedStatement statement = null;
        Scanner scan = new Scanner(System.in);

        try {
            connect = jdbcUtil.getConnection();
            String query = "UPDATE studentInfo SET sage=? where id=?"; // With Placeholders
            statement = connect.prepareStatement(query);

            // Input and setting placeholders with userInput
            statement.setInt(1, 20);
            statement.setInt(2, 1);
            statement.addBatch();

            // Next Batch of Inputs
            // ...

            int[] rowsAffected = statement.executeBatch();
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