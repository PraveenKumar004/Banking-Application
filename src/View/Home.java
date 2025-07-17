package View;

import javax.swing.*;
import java.awt.*;

public class Home {

    public JFrame win;
    public JButton openBtn;
    public JButton bankBtn;
    public JButton atmBtn;

    public Home() {
        win = new JFrame("SecureBank System");
        win.setSize(400, 350);
        win.setLocationRelativeTo(null); // Centered
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLayout(null);

        JLabel heading = new JLabel("Welcome to SecureBank", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBounds(50, 20, 300, 30);
        win.add(heading);

        openBtn = new JButton("Open Bank Account");
        openBtn.setBounds(100, 80, 200, 40);
        win.add(openBtn);

        bankBtn = new JButton("Bank Services");
        bankBtn.setBounds(100, 130, 200, 40);
        win.add(bankBtn);

        atmBtn = new JButton("ATM Services");
        atmBtn.setBounds(100, 180, 200, 40);
        win.add(atmBtn);

        JLabel footerLabel = new JLabel("SecureBank © 2025", SwingConstants.CENTER);
        footerLabel.setBounds(100, 240, 200, 25);
        win.add(footerLabel);

        // ========== Button Actions ==========
        openBtn.addActionListener(e -> {
            win.dispose();
            new RegisterView().show();
        });

        bankBtn.addActionListener(e -> {
            win.dispose();
            new LoginView().show();
        });

        atmBtn.addActionListener(e -> {
            win.dispose();
            new ATMLoginView();
        });
    }

    public void show() {
        win.setVisible(true);
    }
}
