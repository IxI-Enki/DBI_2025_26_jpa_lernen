# JPA Übung 05: Erweiterte Konzepte

## Aufgabe

Erweitern Sie die gegebenen Java-Klassen und demonstrieren Sie erweiterte JPA-Konzepte.

## Anforderungen

1. **Entities annotieren**
   - Order und OrderItem als Entities
   - Beziehung Order -> OrderItem (@OneToMany)

2. **N+1 Problem**
   - Demonstrieren Sie das Problem: 1 Query für Orders, N Queries für Items
   - Lösen Sie es mit JOIN FETCH

3. **Batch-Operations**
   - Implementieren Sie Batch-Insert für 1000 OrderItems
   - Konfigurieren Sie hibernate.jdbc.batch_size

4. **Fetch-Strategien**
   - LAZY vs EAGER demonstrieren
   - Zeigen Sie die Performance-Unterschiede

5. **Performance-Optimierung**
   - Pagination für große Ergebnismengen
   - Second-Level Cache (optional)

## Hinweise

- Verwenden Sie PostgreSQL
- Beobachten Sie die generierten SQL-Queries
- Messen Sie die Performance-Unterschiede
