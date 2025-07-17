package Model;

public class UserModel {
    private int id;
    private String name;
    private String accountNumber;
    private String pin;
    private double balance;
    private String createdAt;

    private String email;
    private String phone;
    private String dob; 

    public UserModel() {
    }

    public UserModel(int id, String name, String accountNumber, String pin, double balance,
                     String createdAt, String email, String phone, String dob) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.createdAt = createdAt;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
