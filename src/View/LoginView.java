package View;

import Controller.LoginController;
import Model.UserModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    JFrame frame;
    JTextField accountField;
    JPasswordField pinField;
    JButton loginButton;

    public void show() {
        frame = new JFrame("Login");
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel accLabel = new JLabel("Account Number:");
        accLabel.setBounds(20, 20, 150, 25);
        frame.add(accLabel);

        accountField = new JTextField();
        accountField.setBounds(160, 20, 150, 25);
        frame.add(accountField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(20, 60, 150, 25);
        frame.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(160, 60, 150, 25);
        frame.add(pinField);

        loginButton = new JButton("Login");
        loginButton.setBounds(110, 100, 100, 30);
        frame.add(loginButton);

        frame.setVisible(true);

        // Action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accNo = accountField.getText().trim();
                String pin = new String(pinField.getPassword()).trim();

                if (accNo.isEmpty() || pin.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LoginController controller = new LoginController();
                UserModel user = controller.authenticate(accNo, pin);

                if (user != null) {
                    JOptionPane.showMessageDialog(frame, "Login successful! Welcome " + user.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose(); 
                    BankServicesView dashboard = new BankServicesView(user);
                    dashboard.show();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}
