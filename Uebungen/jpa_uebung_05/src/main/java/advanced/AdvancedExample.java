package advanced;

import jakarta.persistence.*;
// import advanced.entities.Order;
// import advanced.entities.OrderItem;

public class AdvancedExample {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdvancedPU");
        EntityManager em = emf.createEntityManager();

        // # TODO: Entities annotieren Order und OrderItem als Entities

        em.getTransaction().begin();

        // # TODO: N+1 Problem demonstrieren
        // 1. Lade alle Orders
        // 2. Zugriff auf items führt zu weiteren Queries
        // 3. Lösung: JOIN FETCH verwenden

        // # TODO: Batch-Operations
        // 1000 OrderItems in einem Batch einfügen

        // # TODO: Fetch-Strategien
        // LAZY vs EAGER demonstrieren

        em.getTransaction().commit();
    }
}
