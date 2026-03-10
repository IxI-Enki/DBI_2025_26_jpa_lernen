package flights.entities;

public class Booking {
    private String firstName;
    private String lastName;
    private int price;

    public Booking(String firstName, String lastName, int price) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.price = price;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Booking [firstName=" + firstName + ", lastName=" + lastName + ", price=" + price + "]";
    }

}
