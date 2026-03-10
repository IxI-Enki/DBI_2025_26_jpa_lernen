package kh.entities;

public class Patient extends Person {
    
    private String svNr;
    
    public Patient(String vorname, String nachname, String svNr) {
        super(vorname, nachname);
        this.svNr = svNr;
    }

    public String getSvNr() {
        return svNr;
    }

    public void setSvNr(String svNr) {
        this.svNr = svNr;
    }


}
