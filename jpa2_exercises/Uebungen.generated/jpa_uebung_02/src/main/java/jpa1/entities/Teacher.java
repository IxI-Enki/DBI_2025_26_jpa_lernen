package jpa1.entities;

public class Teacher extends Person {
    int salary;
    String room;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, Address address, int salary, String room) {
        super(firstName, lastName, address);
        this.salary = salary;
        this.room = room;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    
}
