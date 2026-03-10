package lifecycle.entities;

public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPayment() {
        super();
    }

    public CreditCardPayment(double amount, String currency, String cardNumber, String cardHolder) {
        super(amount, currency);
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
}
