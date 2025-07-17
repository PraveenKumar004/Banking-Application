package View;

import Controller.ATMController;

import javax.swing.*;
import java.awt.*;

public class ATMServicesView {
    private JFrame frame;
    private ATMController controller;
    private String cardNumber;

    public ATMServicesView(String cardNumber) {
        this.cardNumber = cardNumber;
        this.controller = new ATMController();

        frame = new JFrame("ATM Services");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Welcome to Secure ATM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); 

        JButton pinBtn = new JButton("Generate / Change PIN");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");


        pinBtn.addActionListener(e -> controller.handlePinGeneration(cardNumber));
        withdrawBtn.addActionListener(e -> controller.handleWithdraw(cardNumber));
        balanceBtn.addActionListener(e -> controller.handleBalanceCheck(cardNumber));

        buttonPanel.add(pinBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(balanceBtn);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
