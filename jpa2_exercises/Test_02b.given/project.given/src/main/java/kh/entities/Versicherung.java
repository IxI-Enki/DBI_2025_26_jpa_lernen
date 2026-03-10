package kh.entities;

public class Versicherung {

    private String typ;

    private String name;

    public Versicherung(String typ, String name) {
        this.typ = typ;
        this.name = name;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
