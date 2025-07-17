package Service;

import java.sql.*;

import Util.DBConnection;

public class ATMService {
    private Connection con;

    public ATMService() {
        try {
        	con = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCardExists(String cardNumber) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM atm_cards WHERE card_number = ? AND status = 'active'");
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean generateOrChangeAtmPin(String cardNumber, String newPin) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE atm_cards SET pin = ? WHERE card_number = ?");
            ps.setString(1, newPin);
            ps.setString(2, cardNumber);
            int updated = ps.executeUpdate(); 
            return updated > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyPin(String cardNumber, String enteredPin) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM atm_cards WHERE card_number = ? AND pin = ? AND status = 'active'");
            ps.setString(1, cardNumber);
            ps.setString(2, enteredPin);
            ResultSet rs = ps.executeQuery();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean withdraw(String cardNumber, double amount) {
        try {
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(
                "SELECT id, balance FROM users WHERE id = (SELECT user_id FROM atm_cards WHERE card_number = ?)");
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                double balance = rs.getDouble("balance");

                if (balance >= amount) {
                    PreparedStatement update = con.prepareStatement(
                        "UPDATE users SET balance = balance - ? WHERE id = ?");
                    update.setDouble(1, amount);
                    update.setInt(2, userId);
                    update.executeUpdate();

                    PreparedStatement txn = con.prepareStatement(
                        "INSERT INTO transactions (user_id, type, amount, details) VALUES (?, 'withdraw', ?, 'ATM withdrawal')");
                    txn.setInt(1, userId);
                    txn.setDouble(2, amount);
                    txn.executeUpdate();

                    con.commit();
                    return true;
                }
            }
            con.rollback();
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
        }
        return false;
    }


    public double getBalance(String cardNumber) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT balance FROM users WHERE id = (SELECT user_id FROM atm_cards WHERE card_number = ?)");
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}