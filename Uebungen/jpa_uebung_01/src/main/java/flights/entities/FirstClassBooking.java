package flights.entities;

public class FirstClassBooking extends Booking {
    private String menue;
    boolean welcomeDrink;
    boolean vip;

    public FirstClassBooking(String firstName, String lastName, int price, String menue, boolean welcomeDrink,
            boolean vip) {
        super(firstName, lastName, price);
        this.menue = menue;
        this.welcomeDrink = welcomeDrink;
        this.vip = vip;
    }


    public String getMenue() {
        return menue;
    }
    public void setMenue(String menue) {
        this.menue = menue;
    }
    public boolean isWelcomeDrink() {
        return welcomeDrink;
    }
    public void setWelcomeDrink(boolean welcomeDrink) {
        this.welcomeDrink = welcomeDrink;
    }

    @Override
    public String toString() {
        return "FirstClassBooking [menue=" + menue + ", welcomeDrink=" + welcomeDrink + ", vip=" + vip
                + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getPrice()="
                + getPrice() + "]";
    }

    
}
