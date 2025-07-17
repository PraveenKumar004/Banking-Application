package View;

import Controller.ATMController;

import javax.swing.*;
import java.awt.*;

public class PINVerificationView {
    private JFrame frame;
    private JPasswordField pinField;
    private ATMController controller;

    public PINVerificationView(String cardNumber, String action) {
        controller = new ATMController();

        frame = new JFrame("PIN Verification");
        frame.setSize(350, 180);
        frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));


        JLabel label = new JLabel("Enter Your 4-digit ATM PIN", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(label, BorderLayout.NORTH);

      
        JPanel inputPanel = new JPanel(new FlowLayout());
        pinField = new JPasswordField(10);
        inputPanel.add(new JLabel("PIN:"));
        inputPanel.add(pinField);
        frame.add(inputPanel, BorderLayout.CENTER);

        
        JButton verifyBtn = new JButton("Verify");
        verifyBtn.addActionListener(e -> {
            String enteredPin = new String(pinField.getPassword());

            if (enteredPin.length() != 4 || !enteredPin.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(frame, "PIN must be 4 digits");
                return;
            }

            if (controller.verifyPin(cardNumber, enteredPin)) {
                frame.dispose();
                controller.proceedAction(cardNumber, action);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid PIN");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(verifyBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
