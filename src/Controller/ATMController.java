package Controller;

import Service.ATMService;
import View.ATMServicesView;
import View.PINVerificationView;

import javax.swing.*;

public class ATMController {
    ATMService service = new ATMService();

    public boolean validateCard(String cardNumber) {
        return service.checkCardExists(cardNumber);
    }

    public void showATMServices(String cardNumber) {
        new ATMServicesView(cardNumber);
    }

    public void handlePinGeneration(String cardNumber) {
        String newPin = JOptionPane.showInputDialog("Enter new 4-digit ATM PIN:");
        if (newPin != null && newPin.matches("\\d{4}")) {
            if (service.generateOrChangeAtmPin(cardNumber, newPin)) {
                JOptionPane.showMessageDialog(null, "ATM PIN updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update ATM PIN");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid PIN. Must be exactly 4 digits.");
        }
    }

    public void handleWithdraw(String cardNumber) {
        new PINVerificationView(cardNumber, "withdraw");
    }

    public void handleBalanceCheck(String cardNumber) {
        new PINVerificationView(cardNumber, "balance");
    }

    public boolean verifyPin(String cardNumber, String enteredPin) {
        return service.verifyPin(cardNumber, enteredPin);
    }

    public void proceedAction(String cardNumber, String action) {
        if ("withdraw".equals(action)) {
            String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
            try {
                double amount = Double.parseDouble(amountStr);
                if (service.withdraw(cardNumber, amount)) {
                    JOptionPane.showMessageDialog(null, "Withdrawal successful");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance or transaction failed");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid amount entered");
            }
        } else if ("balance".equals(action)) {
            double balance = service.getBalance(cardNumber);
            JOptionPane.showMessageDialog(null, "Your Balance: ₹" + balance);
        }
    }
}