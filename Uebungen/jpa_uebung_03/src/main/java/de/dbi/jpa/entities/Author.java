package de.dbi.jpa.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@NamedQuery(
    name = "Author.findByLastName",
    query = "SELECT a FROM Author a WHERE a.lastName = :lastName"
)
@NamedQuery(
    name = "Author.countByCity",
    query = "SELECT COUNT(a) FROM Author a WHERE a.city = ?1"
)
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    
    @Column(name = "city", length = 100)
    private String city;
    
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
    
    public Author() {
    }
    
    public Author(String firstName, String lastName, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }
    
    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
    
    @Override
    public String toString() {
        return "Author{id=" + id + ", name='" + firstName + " " + lastName + "', city='" + city + "'}";
    }
}
