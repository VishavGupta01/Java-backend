import java.sql.*;

public class BetterJDBC {
    public static void main (String[] args) {
        Connection connect = null;
        Statement statement = null;

        try {
            connect = jdbcUtil.getConnection();
            statement = connect.createStatement();

            String query = "SELECT * FROM studentInfo";

            boolean status = statement.execute(query);
            if (status) {
                ResultSet rs = statement.executeQuery(query);
                while(rs.next()) {
                    System.out.println(rs.getString("sname"));
                }
            } else {
                System.out.println(statement.getUpdateCount() + " rows affected!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                jdbcUtil.closeResources(connect, statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}