package RIJESENJA.zad_1;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String putanjaPodataka = "1ZADATAKTsve/src/DATA/podaci_zad_1.txt";
        String putanjaIzvjestaja = "1ZADATAKTsve/src/RIJESENJA/zad_1/izvjestaj.txt";

        // Učitavanje podataka
        System.out.println("Učitavanje podataka iz: " + putanjaPodataka);
        ArrayList<UzorakKontrola> uzorci = UcitavanjePodataka.ucitajPodatke(putanjaPodataka);

        System.out.println("Učitano " + uzorci.size() + " ispravnih zapisa.");

        // Generiranje izvještaja
        if (!uzorci.isEmpty()) {
            Izvjestaj.generirajIzvjestaj(uzorci, putanjaIzvjestaja);
        } else {
            System.out.println("Nema ispravnih zapisa za obradu.");
        }
    }
}