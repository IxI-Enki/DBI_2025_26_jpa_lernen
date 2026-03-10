package kh;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import kh.entities.Arzt;
import kh.entities.Behandlung;
import kh.entities.Fachbereich;
import kh.entities.Patient;
import kh.entities.Versicherung;

public class KHSystem {
    public KHSystem() {
    }

    public static void printSingleResult(Object result) {
        System.out.println(result.toString());
    }

    private static void printResultList(List<? extends Object> results) {
        results.stream().forEach(r -> printSingleResult(r));
    }

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("KHPU");
        EntityManager em = emf.createEntityManager();

        Fachbereich cardio = new Fachbereich("Kardiologie");
        Fachbereich ortho = new Fachbereich("Orthopädie");
        Fachbereich neuro = new Fachbereich("Neurologie");

        Versicherung oegk = new Versicherung("Gesetzlich", "ÖGK");
        Versicherung pva = new Versicherung("Gesetzlich", "PVA");
        Versicherung merkur = new Versicherung("Privat", "Merkur");

        Arzt arzt1 = new Arzt("Thomas", "Gruber", "Primar");
        Arzt arzt2 = new Arzt("Sabine", "Huber", "Oberärztin");
        Arzt arzt3 = new Arzt("Michael", "Kern", "Assistenzarzt");

        Patient p1 = new Patient("Lukas", "Schmidt", "1029010185");
        Patient p2 = new Patient("Anna", "Wagner", "4432120590");
        Patient p3 = new Patient("Felix", "Mayer", "9876150375");
        Patient p4 = new Patient("Sophie", "Bauer", "1122080899");
        Patient p5 = new Patient("Jakob", "Wallner", "5566030460");

        Behandlung b1 =new Behandlung(LocalDate.of(2026, 1, 10), 120);
        Behandlung b2 =new Behandlung(LocalDate.of(2026, 1, 15), 80);
        Behandlung b3 =new Behandlung(LocalDate.of(2026, 1, 20), 450);
        Behandlung b4 =new Behandlung(LocalDate.of(2026, 1, 22), 200);
        Behandlung b5 =new Behandlung(LocalDate.of(2026, 2, 1), 150);
        Behandlung b6 =new Behandlung(LocalDate.of(2025, 12, 12), 300);
        Behandlung b7 =new Behandlung(LocalDate.of(2026, 1, 5), 300);
        Behandlung b8 =new Behandlung(LocalDate.now().minusDays(10), 90);
        Behandlung b9 =new Behandlung(LocalDate.now().minusDays(5), 60);
        Behandlung b10 =new Behandlung(LocalDate.now().minusDays(1), 1000);
        Behandlung b11 =new Behandlung(LocalDate.now(), 250);
    }
}
