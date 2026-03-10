package jpa1;

import jakarta.persistence.*;
import jpa1.entities.Address;
import jpa1.entities.Pupil;

public class JPA1 {
    public JPA1() {
    }

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA1PU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Address huberAddress = new Address("Linz", "4020", "Garnisonstraße 2");
        Pupil p1 = new Pupil("Hannes", "Huber", huberAddress, "it2025");
        huberAddress.getPersons().add(p1);

        Pupil h2 = new Pupil("Herbert", "Huber", huberAddress, "123456");
        huberAddress.getPersons().add(h2);

        // #TODO: Entities annotieren und persistieren
        em.persist(huberAddress);
        em.persist(p1);
        em.persist(h2);

        em.getTransaction().commit();

        // #TODO: Weitere JPA-Operationen implementieren
        // - Entity Lifecycle (detach, merge, refresh, remove)
        // - JPQL Queries
        // - Named Queries
        // - Fetch-Join für N+1 Problem

    }
}
