package flights.entities;
import java.util.Date;

public class Flight {
    
    private String flightNumber;

    private Date flightDate;
    
    private String operator;

    private String takeOffAirPort;

    private String landingAirPort;

    public Flight(String flightNumber, Date flightDate, String operator, String takeOffAirPort, String landingAirPort) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.operator = operator;
        this.takeOffAirPort = takeOffAirPort;
        this.landingAirPort = landingAirPort;
    }


    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTakeOffAirPort() {
        return takeOffAirPort;
    }

    public void setTakeOffAirPort(String takeOffAirPort) {
        this.takeOffAirPort = takeOffAirPort;
    }

    public String getLandingAirPort() {
        return landingAirPort;
    }

    public void setLandingAirPort(String landingAirPort) {
        this.landingAirPort = landingAirPort;
    }

    @Override
    public String toString() {
        return "Flight [ flightNumber=" + flightNumber + ", flightDate=" + flightDate
                + ", operator=" + operator + ", takeOffAirPort=" + takeOffAirPort + ", landingAirPort=" + landingAirPort
                + "]";
    }

 
    
    
}
