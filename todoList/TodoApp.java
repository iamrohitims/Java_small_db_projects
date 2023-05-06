import java.sql.*;
import java.util.Scanner;

public class TodoApp {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/todolist";

    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            // create table if it does not exist
            String sql = "CREATE TABLE IF NOT EXISTS todos " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " description VARCHAR(255), " +
                    " completed BOOLEAN, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);

            // menu
            Scanner scanner = new Scanner(System.in);
            int choice = -1;
            while (choice != 0) {
                System.out.println("\nTODO List App");
                System.out.println("1. Add new todo");
                System.out.println("2. Mark todo as completed");
                System.out.println("3. List all todos");
                System.out.println("0. Exit");
                System.out.print("\nEnter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("\nEnter todo description: ");
                        scanner.nextLine(); // consume the newline character
                        String description = scanner.nextLine();
                        sql = "INSERT INTO todos (description, completed) VALUES ('" + description + "', false)";
                        stmt.executeUpdate(sql);
                        System.out.println("\nTodo added successfully.");
                        break;
                    case 2:
                        System.out.print("\nEnter todo ID to mark as completed: ");
                        int id = scanner.nextInt();
                        sql = "UPDATE todos SET completed = true WHERE id = " + id;
                        int rowsAffected = stmt.executeUpdate(sql);
                        if (rowsAffected == 0) {
                            System.out.println("\nTodo not found.");
                        } else {
                            System.out.println("\nTodo marked as completed.");
                        }
                        break;
                    case 3:
                        sql = "SELECT * FROM todos";
                        ResultSet rs = stmt.executeQuery(sql);
                        System.out.println("\nID\tDescription\tCompleted");
                        while (rs.next()) {
                            int todoId = rs.getInt("id");
                            String todoDescription = rs.getString("description");
                            boolean completed = rs.getBoolean("completed");
                            System.out.println(todoId + "\t" + todoDescription + "\t" + completed);
                        }
                        break;
                    case 0:
                        System.out.println("\nGoodbye!");
                        break;
                    default:
                        System.out.println("\nInvalid choice.");
                }
            }
            scanner.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
