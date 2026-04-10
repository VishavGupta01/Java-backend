// JDBC => Java DataBase Connectivity
// Connecting Java applications to any type of Database (Local, Remote, Cloud, etc.)

/*
    Step1. Import the required Package and Download and add Database Specific .jar file in project files.
    Step2. Load and Register the Drivers.
    Step3. Establish the Connection between Java App and DataBase.
    Step4. Creating Statements.
    Step5. Execute the Query.
    Step6. Process the Result.
    Step7. Free the Resources & Terminate the Connection.
*/

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Basics {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        // Step2. Load and Register the Driver.
        // Class.forName(ClassName); => used to load a class but does not Instantiate it.
        // Class.forName(ClassName).newInstance(); => Instantiates the loaded class as-well.
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Step3. Establish the Connection between Java App and DataBase.
        Connection connect = DriverManager.getConnection(url, username, password);

        // Step4. Creating Statements.
        Statement statement = connect.createStatement();

        // Step5. Execute the Query.
        String insert = "INSERT INTO studentInfo(id, sname, sage, scity) VALUES (2, 'Vidula', 21, 'Patiala')";
        String update = "UPDATE studentInfo SET scity = 'Sangrur' WHERE id = 1";
        String delete = "DELETE FROM studentInfo WHERE id = 2";
        String select = "SELECT * FROM studentInfo";

        // Non-Select Queries => statement.executeUpdate("SQL Query") -> Returns Int (No. of Rows Affected)
        // System.out.println(statement.executeUpdate(delete) + " rows affected!");

        // Select Queries (Retrieval) => statement.executeQuery("SQL Query") -> Returns ResultSet (Data)
        //      ResultSet result = statement.executeQuery(select);
        //      while (result.next()) {
        //      System.out.println(result.getString("sname") + " " + result.getString("sage"));
        //  }

        // OR

        String query = "SELECT * FROM studentInfo";
        // statement.execute(query) -> returns True if query returns ResultSet (Data/Select Query) and False if query returns updatedRow count (Non-Select Query)
        boolean status = statement.execute(query);
        if(status){
            ResultSet rs = statement.executeQuery(select);
            while(rs.next()) {
                System.out.println(rs.getString("sname"));
            }
        } else {
            System.out.println(statement.getUpdateCount() + " rows affected!");
        }

        // Step6. Process the Result.

        // Step7. Free the Resources & Terminate the Connection.
        // result.close();
        statement.close();
        connect.close();
    }
}