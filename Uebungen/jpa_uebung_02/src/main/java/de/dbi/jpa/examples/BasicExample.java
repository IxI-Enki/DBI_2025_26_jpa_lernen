package de.dbi.jpa.examples;

import de.dbi.jpa.entities.*;
import de.dbi.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;

/**
 * Grundlegendes Beispiel für JPA Entities und Beziehungen.
 */
public class BasicExample {
    
    public static void main(String[] args) {
        JpaUtil.initialize();
        EntityManager em = JpaUtil.getEntityManager();
        
        try {
            System.out.println("=== JPA Übung 02: Einfache Entities und Beziehungen ===\n");
            
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            
            // 1. Teacher erstellen
            System.out.println("[1] Erstelle Lehrer...");
            Teacher teacher1 = new Teacher("Max", "Mustermann", "max.mustermann@schule.de");
            em.persist(teacher1);
            System.out.println("  " + teacher1);
            
            // 2. Courses erstellen
            System.out.println("\n[2] Erstelle Kurse...");
            Course course1 = new Course("Java Programmierung", "Grundlagen der Java-Programmierung", 5);
            course1.setTeacher(teacher1);
            em.persist(course1);
            System.out.println("  " + course1);
            
            Course course2 = new Course("Datenbanken", "Einführung in Datenbanken", 4);
            course2.setTeacher(teacher1);
            em.persist(course2);
            System.out.println("  " + course2);
            
            // 3. Students erstellen
            System.out.println("\n[3] Erstelle Studenten...");
            Student student1 = new Student("Anna", "Schmidt", "ST001");
            student1.setDateOfBirth(LocalDate.of(2000, 5, 15));
            em.persist(student1);
            System.out.println("  " + student1);
            
            Student student2 = new Student("Tom", "Müller", "ST002");
            student2.setDateOfBirth(LocalDate.of(2001, 8, 22));
            em.persist(student2);
            System.out.println("  " + student2);
            
            // 4. Students zu Courses hinzufügen
            System.out.println("\n[4] Verknüpfe Studenten mit Kursen...");
            course1.addStudent(student1);
            course1.addStudent(student2);
            course2.addStudent(student1);
            System.out.println("  Student " + student1.getFirstName() + " ist in " + 
                             student1.getCourses().size() + " Kursen");
            System.out.println("  Student " + student2.getFirstName() + " ist in " + 
                             student2.getCourses().size() + " Kursen");
            
            tx.commit();
            
            // 5. Daten auslesen
            System.out.println("\n[5] Lese Daten aus...");
            tx = em.getTransaction();
            tx.begin();
            
            Teacher loadedTeacher = em.find(Teacher.class, teacher1.getId());
            System.out.println("  Lehrer: " + loadedTeacher);
            System.out.println("  Kurse des Lehrers: " + loadedTeacher.getCourses().size());
            
            Course loadedCourse = em.find(Course.class, course1.getId());
            System.out.println("  Kurs: " + loadedCourse);
            System.out.println("  Studenten im Kurs: " + loadedCourse.getStudents().size());
            
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
}
