package zad_1;

public class UzorakKontrola {
    private String oznaka;
    private String materijal;
    private String laboratorij;
    private int temperatura;
    private int vlaznost;
    private String pakiranje;
    private Status status;

    public UzorakKontrola(String oznaka, String materijal, String laboratorij,
                          int temperatura, int vlaznost, String pakiranje) {
        this.oznaka = oznaka;
        this.materijal = materijal;
        this.laboratorij = laboratorij;
        this.temperatura = temperatura;
        this.vlaznost = vlaznost;
        this.pakiranje = pakiranje;
        this.status = izracunajStatus();
    }

    public Status izracunajStatus() {
        // PRIHVACEN: temp 2-8°C, vlaznost ≤60%, pakiranje = DA
        if (temperatura >= 2 && temperatura <= 8 && vlaznost <= 60 && "DA".equals(pakiranje)) {
            return Status.PRIHVACEN;
        }

        // DODATNA_PROVJERA: pakiranje = DA AND (temp -1 to 1 OR temp 9-12 OR vlaznost 61-75)
        if ("DA".equals(pakiranje)) {
            if ((temperatura >= -1 && temperatura <= 1) ||
                (temperatura >= 9 && temperatura <= 12) ||
                (vlaznost >= 61 && vlaznost <= 75)) {
                return Status.DODATNA_PROVJERA;
            }
        }

        // ODBIJEN: sve ostale ispravne zapise
        return Status.ODBIJEN;
    }

    public boolean jeKriticanZapis() {
        // true ako: temp < -5 OR temp > 15 OR vlaznost > 85
        return temperatura < -5 || temperatura > 15 || vlaznost > 85;
    }

    // Getteri
    public String getOznaka() {
        return oznaka;
    }

    public String getMaterijal() {
        return materijal;
    }

    public String getLaboratorij() {
        return laboratorij;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public int getVlaznost() {
        return vlaznost;
    }

    public String getPakiranje() {
        return pakiranje;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UzorakKontrola{" +
                "oznaka='" + oznaka + '\'' +
                ", materijal='" + materijal + '\'' +
                ", laboratorij='" + laboratorij + '\'' +
                ", temperatura=" + temperatura +
                ", vlaznost=" + vlaznost +
                ", pakiranje='" + pakiranje + '\'' +
                ", status=" + status +
                '}';
    }
}
