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
