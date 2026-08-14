package zad_1;

public class UzorakKontrola {

    private String oznaka;
    private String materijal;
    private String laboratorij;
    private int temperatura;
    private int vlaznost;
    private String pakiranje;

}

public UzorakKontrola(String oznaka, String materijal,
                      String laboratorij, int temperatura,
                      int vlaznost, String pakiranje) {

    this.oznaka = oznaka;
    this.materijal = materijal;
    this.laboratorij = laboratorij;
    this.temperatura = temperatura;
    this.vlaznost = vlaznost;
    this.pakiranje = pakiranje;
}


public Status izracunajStatus() {

    if (temperatura >= 2 && temperatura <= 8
            && vlaznost <= 60
            && pakiranje.equals("DA")) {

        return Status.PRIHVACEN;
    }

    if (pakiranje.equals("DA")
            && ((temperatura >= -1 && temperatura <= 1)
            || (temperatura >= 9 && temperatura <= 12)
            || (vlaznost >= 61 && vlaznost <= 75))) {

        return Status.DODATNA_PROVJERA;
    }

    return Status.ODBIJEN;
}

// && = SVE mora biti true
// || = BAREM JEDNO mora biti true, ILI
