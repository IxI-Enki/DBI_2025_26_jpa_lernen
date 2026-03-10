package kh.entities;

import java.time.LocalDate;

public class Behandlung {

    private LocalDate datum;

    private int kosten;


    public Behandlung(LocalDate datum, int kosten) {
        this.datum = datum;
        this.kosten = kosten;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getKosten() {
        return kosten;
    }

    public void setKosten(int kosten) {
        this.kosten = kosten;
    }
}
