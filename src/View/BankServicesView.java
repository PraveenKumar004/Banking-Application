package View;

import Controller.BankController;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;

public class BankServicesView {
    private JFrame frame;
    private JLabel balanceText;
    private UserModel user;
    private BankController controller;

    public BankServicesView(UserModel user) {
        this.user = user;
        this.controller = new BankController(); // Use controller instead of directly using service
    }

    public void show() {
        frame = new JFrame("Bank Dashboard - " + user.getName());
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel heading = new JLabel("Welcome, " + user.getName());
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBounds(20, 20, 300, 30);
        frame.add(heading);

        balanceText = new JLabel("Balance: ₹" + user.getBalance());
        balanceText.setFont(new Font("Arial", Font.PLAIN, 18));
        balanceText.setBounds(20, 60, 300, 25);
        frame.add(balanceText);

        JLabel accNoLabel = new JLabel("Account No: " + user.getAccountNumber());
        accNoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        accNoLabel.setBounds(20, 90, 300, 25);
        frame.add(accNoLabel);

        JButton profileBtn = new JButton("Profile");
        profileBtn.setBounds(360, 20, 100, 30);
        frame.add(profileBtn);

        JButton depBtn = new JButton("Deposit");
        JButton wdrBtn = new JButton("Withdraw");
        JButton trfBtn = new JButton("Transfer");
        JButton txnBtn = new JButton("Transactions");

        depBtn.setBounds(50, 150, 150, 30);
        wdrBtn.setBounds(250, 150, 150, 30);
        trfBtn.setBounds(50, 200, 150, 30);
        txnBtn.setBounds(250, 200, 150, 30);

        frame.add(depBtn);
        frame.add(wdrBtn);
        frame.add(trfBtn);
        frame.add(txnBtn);

        JButton backBtn = new JButton("Back to Home");
        backBtn.setBounds(150, 270, 180, 35);
        frame.add(backBtn);

        // === Action Listeners ===

        profileBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "Name: " + user.getName() +
                        "\nAccount No: " + user.getAccountNumber() +
                        "\nBalance: ₹" + user.getBalance() +
                        "\nCreated At: " + user.getCreatedAt(),
                "Profile Details", JOptionPane.INFORMATION_MESSAGE));

        depBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter amount to deposit:");
            try {
                double amt = Double.parseDouble(input);
                if (amt > 0 && controller.handleDeposit(user, amt)) {
                    JOptionPane.showMessageDialog(frame, "Amount Deposited Successfully!");
                    updateBalance();
                } else {
                    JOptionPane.showMessageDialog(frame, "Deposit failed!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        wdrBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter amount to withdraw:");
            try {
                double amt = Double.parseDouble(input);
                if (amt > 0 && controller.handleWithdraw(user, amt)) {
                    JOptionPane.showMessageDialog(frame, "Amount Withdrawn Successfully!");
                    updateBalance();
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        trfBtn.addActionListener(e -> {
            String toAcc = JOptionPane.showInputDialog("Enter recipient account number:");
            String amtStr = JOptionPane.showInputDialog("Enter amount to transfer:");

            try {
                double amt = Double.parseDouble(amtStr);
                if (amt > 0 && controller.handleTransfer(user, toAcc, amt)) {
                    JOptionPane.showMessageDialog(frame, "Transfer successful!");
                    updateBalance();
                } else {
                    JOptionPane.showMessageDialog(frame, "Transfer failed!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        txnBtn.addActionListener(e -> {
            String txnData = controller.fetchTransactions(user);
            JOptionPane.showMessageDialog(frame, txnData.isEmpty() ? "No transactions." : txnData,
                    "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new Home().show();
        });

        frame.setVisible(true);
    }

    private void updateBalance() {
        double updatedBalance = controller.getUpdatedBalance(user);
        user.setBalance(updatedBalance);
        balanceText.setText("Balance: ₹" + user.getBalance());
    }
}
