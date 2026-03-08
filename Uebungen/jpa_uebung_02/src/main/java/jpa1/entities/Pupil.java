package jpa1.entities;

public class Pupil extends Person {
    private String matNr;

    public Pupil(String firstName, String lastName, Address address, String matNr) {
        super(firstName, lastName, address);
        this.matNr = matNr;
    }

    public Pupil() {}

    public String getMatNr() {
        return matNr;
    }

    public void setMatNr(String matNr) {
        this.matNr = matNr;
    }

}
