# JPA Übung 03: JPQL und Named Queries

## Aufgabe

Erweitern Sie die gegebenen Java-Klassen und implementieren Sie verschiedene JPQL Queries.

## Anforderungen

1. **Entities annotieren**
   - Product und Category als Entities
   - Beziehung Product -> Category (@ManyToOne)

2. **JPQL Queries**
   - Alle Produkte selektieren
   - Produkte mit Preis > X (Named Parameter)
   - Produkte einer Kategorie (Positional Parameter)
   - Aggregation: Durchschnittspreis aller Produkte

3. **Named Queries**
   - Category.findByName: Kategorie nach Name finden

4. **Fetch-Join**
   - Produkte mit Kategorie in einer Query laden (JOIN FETCH)

## Hinweise

- Verwenden Sie PostgreSQL
- Demonstrieren Sie den Unterschied zwischen normaler Query und Fetch-Join
