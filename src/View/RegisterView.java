package View;

import Controller.RegisterController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView {
    private JFrame frame;
    private JTextField nameField, emailField, phoneField, dobField;
    private JPasswordField pinField;
    private JButton registerBtn;

    public void show() {
        frame = new JFrame("Register");
        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(30, 30, 120, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 30, 220, 25);
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 70, 120, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 70, 220, 25);
        frame.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, 110, 120, 25);
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(160, 110, 220, 25);
        frame.add(phoneField);

        JLabel dobLabel = new JLabel("Date of Birth (yyyy-mm-dd):");
        dobLabel.setBounds(30, 150, 200, 25);
        frame.add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(230, 150, 150, 25);
        frame.add(dobField);

        JLabel pinLabel = new JLabel("PIN (4 digits):");
        pinLabel.setBounds(30, 190, 120, 25);
        frame.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(160, 190, 220, 25);
        frame.add(pinField);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(160, 240, 120, 35);
        frame.add(registerBtn);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String dob = dobField.getText().trim();
                String pin = new String(pinField.getPassword()).trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || dob.isEmpty() || pin.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!pin.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(frame, "PIN must be exactly 4 digits!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                RegisterController controller = new RegisterController();
                boolean success = controller.register(name, email, phone, dob, pin);

                if (success) {
                    JOptionPane.showMessageDialog(frame,
                            "Registration successful!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose(); // Redirect to login or home
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Registration failed! Please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}
