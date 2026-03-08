package flights;

/*
    import java.util.Arrays;
    import java.util.Date;
    import java.util.List;

    import flights.entities.Booking;
    import flights.entities.FirstClassBooking;
    import flights.entities.Flight;
    import flights.entities.Plane;
    import flights.entities.Steward;
    import flights.entities.TireType;
    import jakarta.persistence.*;
*/

public class FlightBookingSystem {
    public FlightBookingSystem() {
    }


    public static void printSingleResult(Object result) {
        System.out.println(result.toString());
        }
        
/*
    private static void printResultList(List<? extends Object> results) {
        results.stream().forEach(r -> printSingleResult(r));
        }
            
*/

    public static void main(String[] args) throws Exception {
/*
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FlightsPU");

        EntityManager em = emf.createEntityManager();
        
        TireType goodYear = new TireType("GoodYear", "FlightTire223");
        TireType michelin = new TireType("Michelin", "M9612");
        
        Plane airbusA380 = new Plane("Airbus 380", "A-76543", 380);
        Plane jumbo = new Plane("Boeing 777", "A-33243", 250);
        
        Steward jutta = new Steward("Jutta Müller");
        
        Flight f1 = new Flight("FR9021", new Date(), "Ryanair", "STN", "DUB");
        Flight f2 = new Flight("LH456", new Date(), "Lufthansa", "FRA", "SIN");
        Flight f3 = new Flight("AA1234", new Date(), "American Airlines", "JFK",
        "LAX");
        
        
        Booking b1 = new Booking("Alice", "Smith", 450);
        Booking b2 = new Booking("Jonathan", "Vanderbilt", 12500);
        Booking b3 = new Booking("Bo", "Li", 15);
        Booking b4 = new Booking("Sarah", "Miller-Jones", 890);
        FirstClassBooking b5 = new FirstClassBooking("Kevin", "Freebie", 0,
        "Lachsfilet in Champagnersauce", true, true);
        
        //em.getTransaction().begin();
        
        
        // Beispiel zur Verwendung von printResultList
        var resultList = Arrays.asList(b1, b2, b5);
        printResultList(resultList);
*/
    }
}
