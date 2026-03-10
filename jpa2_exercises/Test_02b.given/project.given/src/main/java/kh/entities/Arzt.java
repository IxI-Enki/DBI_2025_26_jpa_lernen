package kh.entities;

public class Arzt extends Person {
    
    String rang;

    public Arzt() {
    }

    public Arzt(String vorname, String nachname, String rang) {
        super(vorname, nachname);
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }
    
    
}
