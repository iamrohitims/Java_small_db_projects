import java.sql.*;
import java.util.*;

public class crudOperations {
    private Connection connection;

    public crudOperations(String host, String username, String password, String databaseName) throws SQLException {
        String url = "jdbc:mysql://" + host + "/" + databaseName;
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public void create(String tableName, String values) throws SQLException {
        String sql = "INSERT INTO " + tableName + " VALUES (" + values + ")";
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Inserted into " + tableName);
    }

    public ResultSet read(String table, String condition) throws SQLException {
        String query = "SELECT * FROM " + table;
        if (!condition.isEmpty()) {
            query += " WHERE " + condition;
        }
        //System.out.println("Executing SQL query: " + query); // print out the SQL query
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }


    public void update(String tableName, String setClause, String condition) throws SQLException {
        String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + condition;
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Updated " + tableName);
    }

    public void delete(String tableName, String condition) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + condition;
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Deleted from " + tableName);
    }


    public void close() throws SQLException {
        this.connection.close();
    }

    public static void main(String[] args) throws RuntimeException {



        try {
            crudOperations database = new crudOperations("localhost", "root", "root", "reservation_system");
            Scanner scanner = new Scanner(System.in);
            while (true) {
            try{
            ResultSet allUsers = database.read("users", "");
            while (allUsers.next()) {
                int id = allUsers.getInt("id");
                String fName = allUsers.getString("username");
                String lName = allUsers.getString("password");
                String userEmail = allUsers.getString("email");
                String userPhone = allUsers.getString("phone");
                String userCreated = allUsers.getString("created_at");
                System.out.println(id + " " + fName + " " + lName + " " + userEmail + " " + userPhone + " " + userCreated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

                // display menu
                System.out.println("\nPlease select an option:");
                System.out.println("1. Insert New User");
                System.out.println("2. Update User");
                System.out.println("3. Delete User");
                System.out.println("4. Exit");

                // get user input
                int option = scanner.nextInt();

                switch (option) {

                    case 1:
                    // get input for new user
                        System.out.println("\nInsert New User");
                        scanner.nextLine(); // consume newline character
                        System.out.print("Enter Full Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String password = scanner.next();
                        System.out.print("Enter Email Address: ");
                        String email = scanner.next();
                        System.out.print("Enter Phone Number: ");
                        String phone = scanner.next();

                    // insert new user into database
                    database.create("users", "DEFAULT, '" + firstName + "', '" + password + "', '" + email + "', '" + phone + "', CURRENT_TIMESTAMP");

                    break;


                    case 2:
                        // get input for user to update
                        System.out.println("\nUpdate User");
                        System.out.print("Enter User ID: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Full Name: ");
                        String updateFullName = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String updatePassword = scanner.next();
                        System.out.print("Enter Email Address: ");
                        String updateEmail = scanner.next();
                        System.out.print("Enter Phone Number: ");
                        String updatePhone = scanner.next();

                        // update user in database
                        database.update("users", "username = '" + updateFullName + "', password = '" + updatePassword +
                                "', email = '" + updateEmail + "', phone = '" + updatePhone + "'", "id = " + updateId);

                        break;

                    case 3:
                        // get input for user to delete
                        System.out.println("\nDelete User");
                        System.out.print("Enter User ID: ");
                        int deleteId = scanner.nextInt();

                        try {
                            // delete user from database
                            String condition = "id = " + deleteId;
                            database.delete("users", condition);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 4:
                        System.exit(0); // exit the program

                    default:
                        System.out.println("Invalid option!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
