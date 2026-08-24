package zad_2;

public class AktivnostUcenja {
    private String naziv;
    private String tema;
    private TipAktivnosti tip;
    private int trajanje;  // u minutama
    private String napomena;

    public AktivnostUcenja(String naziv, String tema, TipAktivnosti tip, int trajanje, String napomena) {
        this.naziv = naziv;
        this.tema = tema;
        this.tip = tip;
        this.trajanje = trajanje;
        this.napomena = napomena;
    }

    // Getteri
    public String getNaziv() {
        return naziv;
    }

    public String getTema() {
        return tema;
    }

    public TipAktivnosti getTip() {
        return tip;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public String getNapomena() {
        return napomena;
    }

    // Setteri
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setTip(TipAktivnosti tip) {
        this.tip = tip;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    @Override
    public String toString() {
        return naziv + " | " + tema + " | " + tip + " | " + trajanje + " min | " + napomena;
    }
}
