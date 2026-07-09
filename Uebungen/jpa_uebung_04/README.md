# JPA Übung 04: Entity Lifecycle und Inheritance

## Aufgabe

Erweitern Sie die gegebenen Java-Klassen und demonstrieren Sie Entity Lifecycle und Inheritance.

## Anforderungen

1. **Entities annotieren**
   - Payment als Basis-Entity
   - CreditCardPayment und PayPalPayment als Sub-Entities
   - @Inheritance(strategy = InheritanceType.SINGLE_TABLE)

2. **Entity Lifecycle**
   - persist(): Transient -> Managed
   - detach(): Managed -> Detached
   - merge(): Detached -> Managed
   - refresh(): Managed mit DB-Werten aktualisieren
   - remove(): Managed -> Removed

3. **Lifecycle Callbacks**
   - @PrePersist: Vor dem Persistieren
   - @PostPersist: Nach dem Persistieren
   - @PreUpdate: Vor dem Update
   - @PostUpdate: Nach dem Update

4. **Inheritance Strategies**
   - SINGLE_TABLE: Alle in einer Tabelle
   - Demonstrieren Sie die Unterschiede

## Hinweise

- Verwenden Sie PostgreSQL
- Beobachten Sie die generierten SQL-Queries
