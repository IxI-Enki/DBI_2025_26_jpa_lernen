# JPA Übungen - DBI 2025/26

Dieses Repository enthält 5 didaktisch aufbauende JPA-Übungen für das DBI-Modul.

## Übersicht

Alle Übungen sind **Ausgangsprojekte** mit einfachen Java-Klassen **OHNE JPA-Annotationen**. Die Aufgabe ist es, die Klassen zu annotieren und die JPA-Funktionalität zu implementieren.

### Übung 01: Flugbuchungssystem
**Schwierigkeit**: Hoch  
**Themen**: Komplexe Beziehungen, JPQL, Named Queries, Inheritance  
**Basierend auf**: Testangabe (angabe.pdf)

### Übung 02: Einfache Entities und Beziehungen
**Schwierigkeit**: Niedrig  
**Themen**: Grundlagen, @Entity, @OneToMany, @ManyToOne, @ManyToMany, Entity Lifecycle

### Übung 03: JPQL und Named Queries
**Schwierigkeit**: Mittel  
**Themen**: JPQL Queries, Named Queries, Parameter, Aggregationen, Fetch-Join

### Übung 04: Entity Lifecycle und Inheritance
**Schwierigkeit**: Mittel  
**Themen**: Entity States, Inheritance Strategies, Lifecycle Callbacks

### Übung 05: Erweiterte Konzepte
**Schwierigkeit**: Hoch  
**Themen**: N+1 Problem, Performance, Fetch-Strategien, Batch-Operations

## Struktur

```tree
Uebungen/
├── jpa_uebung_01/    # Flugbuchungssystem (komplex)
├── jpa_uebung_02/    # Grundlagen
├── jpa_uebung_03/    # Queries
├── jpa_uebung_04/    # Lifecycle & Inheritance
└── jpa_uebung_05/    # Erweiterte Konzepte
```

## Voraussetzungen

- Java 21
- Maven 3.6 oder höher
- PostgreSQL (oder H2 für lokale Tests)

## Verwendung

Jede Übung ist ein eigenständiges Maven-Projekt:

```powershell
cd Uebungen/jpa_uebung_01
mvn clean compile
```

## Lernpfad

1. **Start**: Übung 02 (Grundlagen)
2. **Vertiefung**: Übung 03 (Queries)
3. **Erweitert**: Übung 04 (Lifecycle)
4. **Praxistest**: Übung 01 (Komplexes Beispiel)
5. **Optimierung**: Übung 05 (Performance)

## Technologien

- **JPA 3.0** (Jakarta Persistence)
- **Hibernate 7.1.4**
- **PostgreSQL** (empfohlen) oder **H2** (für Tests)
- **Maven**

## Hinweise

- Alle Übungen enthalten **Ausgangsprojekte** ohne JPA-Annotationen
- Die persistence.xml ist bereits vorbereitet
- Jede Übung hat eine README mit konkreten Aufgaben
- Die Main-Klassen enthalten TODO-Kommentare als Anleitung

## Lizenz

Für Übungszwecke erstellt.
