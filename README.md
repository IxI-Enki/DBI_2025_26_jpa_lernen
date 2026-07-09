# JPA Mastery · DBI 2025/26

**Interaktive Lernplattform für Java Persistence API (JPA)**  
HTL Leonding · DBI/NVS · Schuljahr 2025/26

---

## Live-App

<table>
<tr>
<td width="120" align="center">
<strong>Starten</strong><br><br>
<a href="https://ixi-enki.github.io/DBI_2025_26_jpa_lernen/">
<img src="https://img.shields.io/badge/→_JPA_Mastery-007396?style=for-the-badge&labelColor=1a1a2e" alt="JPA Mastery oeffnen">
</a>
</td>
<td>

**[https://ixi-enki.github.io/DBI_2025_26_jpa_lernen/](https://ixi-enki.github.io/DBI_2025_26_jpa_lernen/)**

Die Web-App ist der **zentrale Einstieg** für das Lernen und Üben:

- **89 interaktive Aufgaben** in **8 Modulen** — von Entity-Grundlagen bis JPQL und Lifecycle
- Sofort im **Browser** nutzbar — keine Installation nötig
- Validierung, Fortschritt, Cheat Sheet und Wiederholungsmodus
- Ausgerichtet auf DBI/NVS-Lehrplan und Matura-Vorbereitung

</td>
</tr>
</table>

> Bookmark empfohlen: [JPA Mastery](https://ixi-enki.github.io/DBI_2025_26_jpa_lernen/) — direkt loslegen, jederzeit wiederholen.

---

## Module (Web-App)

| Modul | Thema |
|-------|-------|
| 1 | Entity & Primärschlüssel |
| 2 | Column Mapping & Datentypen |
| 3 | EntityManager & Lifecycle |
| 4 | Relationships |
| 5 | Vererbung & Performance |
| 6 | JPQL & NamedQuery |
| 7 | JPQL Vertieft |
| 8 | Konfiguration & Lifecycle |

---

## Maven-Übungen (Repository)

Neben der Web-App enthält dieses Repository **5 didaktisch aufbauende JPA-Übungen** als eigenständige Maven-Projekte.  
Alle Übungen sind **Ausgangsprojekte** mit einfachen Java-Klassen **ohne JPA-Annotationen** — Aufgabe ist es, die Klassen zu annotieren und die JPA-Funktionalität zu implementieren.

### Übung 01: Flugbuchungssystem
**Schwierigkeit**: Hoch  
**Themen**: Komplexe Beziehungen, JPQL, Named Queries, Inheritance

### Übung 02: Einfache Entities und Beziehungen
**Schwierigkeit**: Niedrig  
**Themen**: Grundlagen, `@Entity`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`, Entity Lifecycle

### Übung 03: JPQL und Named Queries
**Schwierigkeit**: Mittel  
**Themen**: JPQL Queries, Named Queries, Parameter, Aggregationen, Fetch-Join

### Übung 04: Entity Lifecycle und Inheritance
**Schwierigkeit**: Mittel  
**Themen**: Entity States, Inheritance Strategies, Lifecycle Callbacks

### Übung 05: Erweiterte Konzepte
**Schwierigkeit**: Hoch  
**Themen**: N+1 Problem, Performance, Fetch-Strategien, Batch-Operations

### Struktur

```tree
Uebungen/
├── jpa_uebung_01/    # Flugbuchungssystem (komplex)
├── jpa_uebung_02/    # Grundlagen
├── jpa_uebung_03/    # Queries
├── jpa_uebung_04/    # Lifecycle & Inheritance
└── jpa_uebung_05/    # Erweiterte Konzepte
```

### Lernpfad

1. **Web-App**: Module 1–8 durcharbeiten → [JPA Mastery](https://ixi-enki.github.io/DBI_2025_26_jpa_lernen/)
2. **Start**: Übung 02 (Grundlagen)
3. **Vertiefung**: Übung 03 (Queries)
4. **Erweitert**: Übung 04 (Lifecycle)
5. **Praxistest**: Übung 01 (Komplexes Beispiel)
6. **Optimierung**: Übung 05 (Performance)

---

## Voraussetzungen (Maven-Übungen)

- Java 21
- Maven 3.6 oder höher
- PostgreSQL (oder H2 für lokale Tests)

## Verwendung

Jede Übung ist ein eigenständiges Maven-Projekt:

```powershell
cd Uebungen/jpa_uebung_01
mvn clean compile
```

## Technologien

- **JPA 3.0** (Jakarta Persistence)
- **Hibernate 7.1.4**
- **PostgreSQL** (empfohlen) oder **H2** (für Tests)
- **Maven**

## Hinweise

- Alle Übungen enthalten **Ausgangsprojekte** ohne JPA-Annotationen
- Die `persistence.xml` ist bereits vorbereitet
- Jede Übung hat eine README mit konkreten Aufgaben
- Die Main-Klassen enthalten TODO-Kommentare als Anleitung

## Lizenz

Für Übungszwecke erstellt.
