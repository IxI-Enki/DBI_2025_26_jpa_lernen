package flights.entities;

public class Plane {
    private String pType;
    private String pCode;
    private int seats;

    public Plane(String pType, String pCode, int seats) {
        this.pType = pType;
        this.pCode = pCode;
        this.seats = seats;
    }

    public String getpType() {
        return pType;
    }
    public void setpType(String pType) {
        this.pType = pType;
    }
    public String getpCode() {
        return pCode;
    }
    public void setpCode(String pCode) {
        this.pCode = pCode;
    }
    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Plane [pType=" + pType + ", pCode=" + pCode + ", seats=" + seats + "]";
    }
}
