package Util;
import java.sql.*;

public class DBConnection {
	public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/BankApplication";
        String user = "root";
        String pass = "root";
        return DriverManager.getConnection(url, user, pass);
    }
}
