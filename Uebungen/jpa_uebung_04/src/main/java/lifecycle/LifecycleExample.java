package lifecycle;

import jakarta.persistence.*;
// import lifecycle.entities.Payment;

public class LifecycleExample {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LifecyclePU");
        EntityManager em = emf.createEntityManager();

        // TODO: Entities annotieren mit Inheritance (SINGLE_TABLE Strategy)

        em.getTransaction().begin();

        // TODO: Entity Lifecycle demonstrieren:
        // 1. persist() - Transient -> Managed
        // 2. detach() - Managed -> Detached
        // 3. merge() - Detached -> Managed
        // 4. refresh() - Managed mit DB-Werten aktualisieren
        // 5. remove() - Managed -> Removed

        // Payment payment = new Payment(100.0, "EUR");
        // em.persist(payment);

        em.getTransaction().commit();

        // TODO: Lifecycle Callbacks implementieren:
        // @PrePersist, @PostPersist, @PreUpdate, @PostUpdate
    }
}
