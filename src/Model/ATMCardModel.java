package Model;

public class ATMCardModel {
    private int cardId;
    private int userId;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String status;

    public ATMCardModel() {
    }

    public ATMCardModel(int cardId, int userId, String cardNumber, String expiryDate, String cvv, String status) {
        this.cardId = cardId;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.status = status;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
