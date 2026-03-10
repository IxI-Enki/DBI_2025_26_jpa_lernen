package de.dbi.jpa.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@NamedQuery(
    name = "Book.findByTitle",
    query = "SELECT b FROM Book b WHERE b.title LIKE :title"
)
@NamedQuery(
    name = "Book.findExpensive",
    query = "SELECT b FROM Book b WHERE b.price > :minPrice ORDER BY b.price DESC"
)
@NamedQuery(
    name = "Book.findByPublisher",
    query = "SELECT b FROM Book b WHERE b.publisher.name = :publisherName"
)
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false, length = 300)
    private String title;
    
    @Column(name = "isbn", unique = true, length = 50)
    private String isbn;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();
    
    public Book() {
    }
    
    public Book(String title, String isbn, BigDecimal price) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
    }
    
    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }
    
    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }
    
    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', price=" + price + "}";
    }
}
