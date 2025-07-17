package Model;

public class TransactionModel {
    private int txnId;
    private int userId;
    private String type;
    private double amount;
    private String timestamp;
    private String details;

    public TransactionModel() {
    }

    public TransactionModel(int txnId, int userId, String type, double amount, String timestamp, String details) {
        this.txnId = txnId;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.details = details;
    }

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
