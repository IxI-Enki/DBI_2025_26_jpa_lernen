package queries;

import jakarta.persistence.*;

public class QueriesExample {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("QueriesPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // TODO: Entities annotieren und Testdaten erstellen

        // TODO: JPQL Queries implementieren:
        // 1. Alle Produkte
        // 2. Produkte mit Preis > X (Named Parameter)
        // 3. Produkte einer Kategorie (Positional Parameter)
        // 4. Named Query für Category.findByName
        // 5. Aggregation: Durchschnittspreis
        // 6. JOIN FETCH für Produkte mit Kategorie

        em.getTransaction().commit();
    }
}
