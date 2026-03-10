package jpa1.entities;

import javax.annotation.processing.Generated;

import org.hibernate.annotations.Struct;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Persons")
public class Person {
    
    @Id
    @Generated(value = { "" })
    private Long id;

    @Column(name = "Vorname", length = 128)
    private String firstName;
    
    @Column(name = "Nachname", length = 128)
    private String lastName;
    
    
    @Column(name = "Adresse", length = 1024)
    private Address address;
    

    public Person() {}

    public Person(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
