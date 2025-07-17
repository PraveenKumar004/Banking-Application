package Service;

import Model.UserModel;
import Util.DBConnection;

import java.sql.*;

public class TransactionService {
	
    

    public boolean deposit(int userId, double amount) {
        try {
        	Connection con=DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET balance = balance + ? WHERE id = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            int updated = ps.executeUpdate();

            if (updated > 0) {
                PreparedStatement txn = con.prepareStatement(
                    "INSERT INTO transactions (user_id, type, amount, details) VALUES (?, 'deposit', ?, 'Cash deposit')");
                txn.setInt(1, userId);
                txn.setDouble(2, amount);
                txn.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean withdraw(int userId, double amount) {
        try {
        	Connection con=DBConnection.getConnection();
            PreparedStatement balStmt = con.prepareStatement(
                "SELECT balance FROM users WHERE id = ?");
            balStmt.setInt(1, userId);
            ResultSet rs = balStmt.executeQuery();

            if (rs.next() && rs.getDouble("balance") >= amount) {
                PreparedStatement update = con.prepareStatement(
                    "UPDATE users SET balance = balance - ? WHERE id = ?");
                update.setDouble(1, amount);
                update.setInt(2, userId);
                int updated = update.executeUpdate();

                if (updated > 0) {
                    PreparedStatement txn = con.prepareStatement(
                        "INSERT INTO transactions (user_id, type, amount, details) VALUES (?, 'withdraw', ?, 'Cash withdrawal')");
                    txn.setInt(1, userId);
                    txn.setDouble(2, amount);
                    txn.executeUpdate();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean transfer(int fromUserId, String toAccountNumber, double amount) {
        try {
        	Connection con=DBConnection.getConnection();
            PreparedStatement recvStmt = con.prepareStatement(
                "SELECT id FROM users WHERE account_number = ?");
            recvStmt.setString(1, toAccountNumber);
            ResultSet recvRs = recvStmt.executeQuery();

            if (!recvRs.next()) return false;
            int toUserId = recvRs.getInt("id");

            // Check balance
            PreparedStatement balStmt = con.prepareStatement(
                "SELECT balance FROM users WHERE id = ?");
            balStmt.setInt(1, fromUserId);
            ResultSet balRs = balStmt.executeQuery();

            if (balRs.next() && balRs.getDouble("balance") >= amount) {
                // Deduct from sender
                PreparedStatement deduct = con.prepareStatement(
                    "UPDATE users SET balance = balance - ? WHERE id = ?");
                deduct.setDouble(1, amount);
                deduct.setInt(2, fromUserId);
                deduct.executeUpdate();

                // Add to receiver
                PreparedStatement add = con.prepareStatement(
                    "UPDATE users SET balance = balance + ? WHERE id = ?");
                add.setDouble(1, amount);
                add.setInt(2, toUserId);
                add.executeUpdate();

                // Log transactions
                PreparedStatement logSender = con.prepareStatement(
                    "INSERT INTO transactions (user_id, type, amount, details) VALUES (?, 'transfer', ?, ?)");
                logSender.setInt(1, fromUserId);
                logSender.setDouble(2, amount);
                logSender.setString(3, "Transfer to " + toAccountNumber);
                logSender.executeUpdate();

                PreparedStatement logReceiver = con.prepareStatement(
                    "INSERT INTO transactions (user_id, type, amount, details) VALUES (?, 'receive', ?, ?)");
                logReceiver.setInt(1, toUserId);
                logReceiver.setDouble(2, amount);
                logReceiver.setString(3, "Received from User ID: " + fromUserId);
                logReceiver.executeUpdate();

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public double fetchBalance(int userId) {
        try {
        	Connection con=DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT balance FROM users WHERE id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public String getTransactions(int userId) {
        StringBuilder sb = new StringBuilder();
        try {
        	Connection con=DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT type, amount, timestamp, details FROM transactions WHERE user_id = ? ORDER BY timestamp DESC LIMIT 10");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("timestamp")).append(" - ")
                  .append(rs.getString("type")).append(" ₹")
                  .append(rs.getDouble("amount")).append(" (")
                  .append(rs.getString("details")).append(")\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
