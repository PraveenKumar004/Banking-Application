package View;

import Controller.ATMController;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ATMLoginView {

    private JFrame frame;
    private JButton uploadBtn, proceedBtn;
    private JLabel infoLabel;
    private String cardNumber;
    private ATMController controller;

    public ATMLoginView() {
        controller = new ATMController();

        frame = new JFrame("ATM Card Login");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Insert Your ATM Card", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(title, BorderLayout.NORTH);


        infoLabel = new JLabel("Please upload your ATM card file.", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(infoLabel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        uploadBtn = new JButton("Upload Card File");
        proceedBtn = new JButton("Proceed");
        proceedBtn.setEnabled(false); 

        uploadBtn.addActionListener(e -> uploadCardFile());
        proceedBtn.addActionListener(e -> {
            frame.dispose();
            controller.showATMServices(cardNumber);
        });

        buttonPanel.add(uploadBtn);
        buttonPanel.add(proceedBtn);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void uploadCardFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                
                cardNumber = reader.readLine().trim();

                if (controller.validateCard(cardNumber)) {
                    infoLabel.setText("Card detected: " + cardNumber);
                    proceedBtn.setEnabled(true);
                } else {
                    infoLabel.setText("Invalid Card Number!");
                    proceedBtn.setEnabled(false);
                }
            } catch (IOException e) {
                infoLabel.setText("Error reading the card file.");
            }
        }
    }
}
