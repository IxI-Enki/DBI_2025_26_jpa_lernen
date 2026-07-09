package de.dbi.jpa.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final String PERSISTENCE_UNIT_NAME = "query-jpa";
    private static EntityManagerFactory entityManagerFactory;
    
    public static void initialize() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
    }
    
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            initialize();
        }
        return entityManagerFactory.createEntityManager();
    }
    
    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
