public class UzorakKontrola {
    public static final String PRIHVACEN = "PRIHVACEN";
    public static final String DODATNA_PROVJERA = "DODATNA_PROVJERA";
    public static final String ODBIJEN = "ODBIJEN";

    private String oznaka;
    private String materijal;
    private String laboratorij;
    private int temperatura;
    private int vlaznost;
    private String pakiranje;

    public UzorakKontrola(String oznaka, String materijal, String laboratorij, int temperatura, int vlaznost, String pakiranje) {
        this.oznaka = oznaka;
        this.materijal = materijal;
        this.laboratorij = laboratorij;
        this.temperatura = temperatura;
        this.vlaznost = vlaznost;
        this.pakiranje = pakiranje;
    }

    public String izracunajStatus() {
        if (pakiranje.equals("DA")) {
            if (temperatura >= 2 && temperatura <= 8 && vlaznost <= 60) {
                return PRIHVACEN;
            }
            if ((temperatura >= -1 && temperatura <= 1) ||
                    (temperatura >= 9 && temperatura <= 12) ||
                    (vlaznost >= 61 && vlaznost <= 75)) {
                return DODATNA_PROVJERA;
            }
        }
        return ODBIJEN;
    }

    public boolean jeKriticanZapis() {
        return temperatura < -5 || temperatura > 15 || vlaznost > 85;
    }

    public String getOznaka() { return oznaka; }
    public String getMaterijal() { return materijal; }
    public String getLaboratorij() { return laboratorij; }
    public int getTemperatura() { return temperatura; }
    public int getVlaznost() { return vlaznost; }
    public String getPakiranje() { return pakiranje; }

    @Override
    public String toString() {
        return "Uzorak: " + oznaka + " (" + materijal + ")";
    }
}