package jpa1.entities;

import java.util.ArrayList;
import java.util.List;

public class Address {

    private Long id;

    private String city;
    private String zipCode;
    private String num;

    private Coordinate coordinate;

    private List<Person> persons = new ArrayList<>();

    public Address(String city, String zipCode, String num) {
        this.city = city;
        this.zipCode = zipCode;
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
