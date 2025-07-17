package Controller;

import Model.UserModel;
import Service.TransactionService;

public class BankController {
    private TransactionService txnService;

    public BankController() {
        txnService = new TransactionService();
    }

    public boolean handleDeposit(UserModel user, double amount) {
        return txnService.deposit(user.getId(), amount);
    }

    public boolean handleWithdraw(UserModel user, double amount) {
        return txnService.withdraw(user.getId(), amount);
    }

    public boolean handleTransfer(UserModel user, String receiverAccNo, double amount) {
        // ✅ Extract user ID and pass it to the service
        return txnService.transfer(user.getId(), receiverAccNo, amount);
    }

    public String fetchTransactions(UserModel user) {
        return txnService.getTransactions(user.getId());
    }

    public double getUpdatedBalance(UserModel user) {
        return txnService.fetchBalance(user.getId());
    }
}
