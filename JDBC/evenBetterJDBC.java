import java.sql.*;
import java.util.Scanner;

public class evenBetterJDBC {
    public static void main (String[] args) {
        Connection connect = null;
        PreparedStatement pStatement = null;
        Scanner scan = new Scanner(System.in);

        try {
            connect = jdbcUtil.getConnection();

            String query = "INSERT INTO studentInfo(id, sname, sage, scity) VALUES (?, ?, ?, ?)";

            pStatement = connect.prepareStatement(query);

            // User Input
            System.out.println("Enter id: ");
            Integer id = scan.nextInt();
            System.out.println("Enter name: ");
            String name = scan.next();
            System.out.println("Enter age: ");
            Integer age = scan.nextInt();
            System.out.println("Enter city: ");
            String city = scan.next();

            //Setting user input in the Query's placeholders (?)
            pStatement.setInt(1, id);
            pStatement.setString(2, name);
            pStatement.setInt(3, age);
            pStatement.setString(4, city);

            int rowsAffected = pStatement.executeUpdate();
            System.out.println(rowsAffected + " rows affected!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                jdbcUtil.closeResources(connect, pStatement);
                // PreparedStatement can be closed with same method as Prepared Statement is a child of Statement.
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}