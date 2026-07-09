package lifecycle.entities;

public class PayPalPayment extends Payment {
    private String email;
    private String transactionId;

    public PayPalPayment() {
        super();
    }

    public PayPalPayment(double amount, String currency, String email, String transactionId) {
        super(amount, currency);
        this.email = email;
        this.transactionId = transactionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
