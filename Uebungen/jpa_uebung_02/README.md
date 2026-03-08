# JPA Übung 02: Einfache Entities und Beziehungen

## Aufgabe

Erweitern Sie die gegebenen Java-Klassen so, dass diese als Entitäten gespeichert und geladen werden können.

## Anforderungen

1. **Entities annotieren**
   - @Entity, @Id, @GeneratedValue für alle Entities
   - @Column mit verschiedenen Optionen (nullable, length, etc.)
   - @Table für Tabellennamen

2. **Beziehungen ausbilden**
   - Person -> Address: @ManyToOne (mit CascadeType.PERSIST)
   - Address -> Person: @OneToMany (mappedBy)
   - School -> Pupil: @OneToMany mit @JoinColumn
   - Coordinate als @Embeddable in Address

3. **Inheritance**
   - Person als Basis-Entity mit @Inheritance(strategy = InheritanceType.JOINED)
   - Pupil und Teacher als Sub-Entities

4. **Entity Lifecycle demonstrieren**
   - persist, detach, merge, refresh, remove

5. **JPQL Queries**
   - Einfache Queries
   - Queries mit Navigation im Objektgraphen
   - Named Query für Person.findByLastName

6. **N+1 Problem**
   - Demonstrieren Sie das Problem
   - Lösen Sie es mit JOIN FETCH

## Hinweise

- Verwenden Sie PostgreSQL
- Die persistence.xml ist bereits vorbereitet
- Der Code in JPA1.java zeigt die gewünschten Operationen
