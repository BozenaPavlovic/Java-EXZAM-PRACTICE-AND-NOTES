package SwingGUI;

public class AktivnostUcenja {
    private String naziv;
    private String tema;
    private TipAktivnosti tip;
    private int trajanje;
    private String napomena;

    public AktivnostUcenja(String naziv, String tema, TipAktivnosti tip, int trajanje, String napomena) {
        this.naziv = naziv;
        this.tema = tema;
        this.tip = tip;
        this.trajanje = trajanje;
        this.napomena = napomena;
    }

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

    @Override
    public String toString() {
        return "Naziv: " + naziv + ", Tema: " + tema +
                ", Tip: " + tip + ", Trajanje: " + trajanje + " min" +
                (napomena != null && !napomena.isEmpty() ? ", Napomena: " + napomena : "");
    }
}