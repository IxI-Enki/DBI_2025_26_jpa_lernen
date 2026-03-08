# JPA Übung 01: Flugbuchungssystem

## Aufgabe

Erweitern Sie die gegebenen Java-Klassen so, dass diese als Entitäten gespeichert und geladen werden können.

## Anforderungen

1. **Objektmodell persistieren**
   - Entities annotieren (@Entity, @Id, @GeneratedValue, @Table)
   - Beziehungen ausbilden (@ManyToOne, @OneToMany, @ManyToMany)
   - Assoziativtabellen vermeiden wo möglich
   - Testdaten in Beziehung setzen und persistieren

2. **Erweiterte Annotationen**
   - Membervariable `operator` der Entity Flight soll in Spalte `flightoperator` gespeichert werden
   - Beim Persistieren eines Flugs sollen automatisch alle damit in Beziehung stehenden Buchungen gespeichert werden

3. **JPQL Queries**
   - Alle Steward-Entitäten
   - Flüge mit Flugzeugen > 200 Sitzplätze
   - Buchungen mit Preis > 400 (mit Fetch-Join für Flight)
   - Buchungen für Alice Smith (Named Parameters)

4. **Named Queries**
   - Flight.lessThanTwoBookings: Flüge mit weniger als 2 Buchungen
   - Plane.findByPType: Flugzeuge nach Typ (Positional Parameter)
   - TireType.findForPlane: Reifentyp für Flugzeug (Named Parameter)

## Hinweise

- Verwenden Sie PostgreSQL (oder H2 für lokale Tests)
- Die persistence.xml ist bereits vorbereitet
- Die Testdaten sind in FlightBookingSystem.java vorhanden
