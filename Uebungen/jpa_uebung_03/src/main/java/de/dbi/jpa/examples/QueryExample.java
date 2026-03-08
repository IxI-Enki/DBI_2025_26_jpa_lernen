package de.dbi.jpa.examples;

import de.dbi.jpa.entities.*;
import de.dbi.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Demonstriert JPQL Queries und Named Queries.
 */
public class QueryExample {
    
    public static void main(String[] args) {
        JpaUtil.initialize();
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            System.out.println("=== JPA Übung 03: JPQL und Named Queries ===\n");
            
            // Testdaten erstellen
            createTestData(em);
            
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            
            // 1. Einfache JPQL Query
            System.out.println("[1] Einfache Query: Alle Bücher");
            TypedQuery<Book> query1 = em.createQuery("SELECT b FROM Book b", Book.class);
            List<Book> books = query1.getResultList();
            printResults(books);
            
            // 2. Query mit WHERE und Named Parameter
            System.out.println("\n[2] Query mit Named Parameter: Bücher teurer als 20 EUR");
            TypedQuery<Book> query2 = em.createQuery(
                    "SELECT b FROM Book b WHERE b.price > :minPrice", Book.class);
            query2.setParameter("minPrice", new BigDecimal("20.00"));
            List<Book> expensiveBooks = query2.getResultList();
            printResults(expensiveBooks);
            
            // 3. Query mit JOIN
            System.out.println("\n[3] Query mit JOIN: Bücher mit Publisher");
            TypedQuery<Book> query3 = em.createQuery(
                    "SELECT b FROM Book b JOIN b.publisher p WHERE p.city = :city", Book.class);
            query3.setParameter("city", "München");
            List<Book> booksFromMunich = query3.getResultList();
            printResults(booksFromMunich);
            
            // 4. Query mit Aggregation
            System.out.println("\n[4] Aggregation: Durchschnittspreis");
            TypedQuery<Double> query4 = em.createQuery(
                    "SELECT AVG(b.price) FROM Book b", Double.class);
            Double avgPrice = query4.getSingleResult();
            System.out.println("  Durchschnittspreis: " + avgPrice + " EUR");
            
            // 5. Named Query (Named Parameter)
            System.out.println("\n[5] Named Query: Autor nach Nachname");
            TypedQuery<Author> query5 = em.createNamedQuery("Author.findByLastName", Author.class);
            query5.setParameter("lastName", "Müller");
            List<Author> authors = query5.getResultList();
            printResults(authors);
            
            // 6. Named Query (Positional Parameter)
            System.out.println("\n[6] Named Query: Anzahl Autoren in Stadt (Positional)");
            TypedQuery<Long> query6 = em.createNamedQuery("Author.countByCity", Long.class);
            query6.setParameter(1, "Berlin");
            Long count = query6.getSingleResult();
            System.out.println("  Anzahl Autoren in Berlin: " + count);
            
            // 7. Named Query für Books
            System.out.println("\n[7] Named Query: Teure Bücher");
            TypedQuery<Book> query7 = em.createNamedQuery("Book.findExpensive", Book.class);
            query7.setParameter("minPrice", new BigDecimal("15.00"));
            List<Book> expensive = query7.getResultList();
            printResults(expensive);
            
            // 8. Query mit LIKE
            System.out.println("\n[8] Query mit LIKE: Bücher mit 'Java' im Titel");
            TypedQuery<Book> query8 = em.createNamedQuery("Book.findByTitle", Book.class);
            query8.setParameter("title", "%Java%");
            List<Book> javaBooks = query8.getResultList();
            printResults(javaBooks);
            
            tx.commit();
            
            System.out.println("\n[OK] Beispiel erfolgreich abgeschlossen!");
            
        } catch (Exception e) {
            System.err.println("[ERROR] Fehler: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            JpaUtil.close();
        }
    }
    
    private static void createTestData(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            Publisher pub1 = new Publisher("O'Reilly", "München");
            Publisher pub2 = new Publisher("Springer", "Berlin");
            em.persist(pub1);
            em.persist(pub2);
            
            Author author1 = new Author("Max", "Müller", "Berlin");
            Author author2 = new Author("Anna", "Schmidt", "Berlin");
            Author author3 = new Author("Tom", "Weber", "Hamburg");
            em.persist(author1);
            em.persist(author2);
            em.persist(author3);
            
            Book book1 = new Book("Java für Anfänger", "978-1234567890", new BigDecimal("29.99"));
            book1.setPublicationDate(LocalDate.of(2023, 1, 15));
            book1.setPublisher(pub1);
            book1.addAuthor(author1);
            em.persist(book1);
            
            Book book2 = new Book("Advanced Java", "978-0987654321", new BigDecimal("49.99"));
            book2.setPublicationDate(LocalDate.of(2023, 6, 20));
            book2.setPublisher(pub1);
            book2.addAuthor(author1);
            book2.addAuthor(author2);
            em.persist(book2);
            
            Book book3 = new Book("Datenbanken Grundlagen", "978-1122334455", new BigDecimal("19.99"));
            book3.setPublicationDate(LocalDate.of(2024, 3, 10));
            book3.setPublisher(pub2);
            book3.addAuthor(author3);
            em.persist(book3);
            
            tx.commit();
            System.out.println("[OK] Testdaten erstellt\n");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    private static <T> void printResults(List<T> results) {
        System.out.println("  Anzahl: " + results.size());
        for (T result : results) {
            System.out.println("    - " + result);
        }
    }
}
