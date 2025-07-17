package Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import Util.DBConnection;

public class RegisterService {

    public boolean registerNewUser(String name, String email, String phone, String dob, String pin) {
        String checkPhoneQuery = "SELECT * FROM users WHERE phone = ?";
        String insertUserQuery = "INSERT INTO users (name, email, phone, dob, account_number, pin, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertCardQuery = "INSERT INTO atm_cards (user_id, card_number, expiry_date, cvv, pin, status) VALUES (?, ?, ?, ?, ?, 'active')";

        String accountNumber = generateAccountNumber();

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement checkPhoneStmt = conn.prepareStatement(checkPhoneQuery);
            checkPhoneStmt.setString(1, phone);
            ResultSet rsPhone = checkPhoneStmt.executeQuery();
            if (rsPhone.next()) {
                System.out.println("Phone already exists.");
                return false;
            }

            PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, phone);
            insertStmt.setString(4, dob); 
            insertStmt.setString(5, accountNumber);
            insertStmt.setString(6, pin);
            insertStmt.setDouble(7, 0.0);

            int rows = insertStmt.executeUpdate();
            if (rows == 0) return false;

            ResultSet keys = insertStmt.getGeneratedKeys();
            int userId = keys.next() ? keys.getInt(1) : -1;
            if (userId == -1) return false;


            String cardNumber = accountNumber + String.format("%04d", new Random().nextInt(10000));
            String expiryDate = new SimpleDateFormat("yyyy-MM-dd").format(
                    new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 5)) // 5 years from now
            );
            String cvv = String.format("%03d", new Random().nextInt(1000));
            String atmPin = ""; // Blank initially

            PreparedStatement cardStmt = conn.prepareStatement(insertCardQuery);
            cardStmt.setInt(1, userId);
            cardStmt.setString(2, cardNumber);
            cardStmt.setString(3, expiryDate);
            cardStmt.setString(4, cvv);
            cardStmt.setString(5, atmPin);
            cardStmt.executeUpdate();

            
            createAccountFile(name, accountNumber, email, phone, dob);
            createATMCardFile(name, cardNumber);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createAccountFile(String name, String accountNumber, String email, String phone, String dob) {
        try {
            String folderPath = "D:/BankApplication/Bank_Application/BankAccounts";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();

            String fileName = name.replaceAll("\\s+", "_") + "_" + accountNumber + ".txt";
            String filePath = folderPath + "/" + fileName;

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("Account Created Successfully!\n");
            writer.write("=============================\n");
            writer.write("Name           : " + name + "\n");
            writer.write("Email          : " + email + "\n");
            writer.write("Phone          : " + phone + "\n");
            writer.write("Date of Birth  : " + dob + "\n");
            writer.write("Account Number : " + accountNumber + "\n");
            writer.write("PIN            : ****\n");
            writer.write("Balance        : ₹0.00\n");
            writer.write("Created At     : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Error in create account file.");
        }
    }

    private void createATMCardFile(String name, String cardNumber) {
        try {
            String folderPath = "D:/BankApplication/Bank_Application/ATM_Cards";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();

            String fileName = name.replaceAll("\\s+", "_") + "_ATMCard.txt";
            String filePath = folderPath + "/" + fileName;

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(cardNumber);
            writer.close();

        } catch (Exception e) {
            System.out.println("Error in atm file.");
        }
    }

    public static String generateAccountNumber() {
        try (Connection conn = DBConnection.getConnection()) {
            String countQuery = "SELECT COUNT(*) FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(countQuery);
            int count = 0;
            if (rs.next()) count = rs.getInt(1);

            count++;
            String timePart = new SimpleDateFormat("HHmmss").format(new Date());
            String countPart = String.format("%03d", count);
            String randomPart = String.valueOf(100 + new Random().nextInt(900));

            return timePart + countPart + randomPart;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
