package Service;

import Model.UserModel;
import java.sql.*;
import Util.DBConnection; 

public class LoginService {

    public UserModel login(String accountNumber, String pin) {
        String query = "SELECT * FROM users WHERE account_number = ? AND pin = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            stmt.setString(2, pin);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserModel user = new UserModel();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAccountNumber(rs.getString("account_number"));
                user.setPin(rs.getString("pin"));
                user.setBalance(rs.getDouble("balance"));
                user.setCreatedAt(rs.getString("created_at"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
