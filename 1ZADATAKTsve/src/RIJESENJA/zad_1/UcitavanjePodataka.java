package RIJESENJA.zad_1;

import java.io.*;
import java.util.ArrayList;

public class UcitavanjePodataka {

    /**
     * Učitava podatke iz datoteke i kreira listu UzorakKontrola objekata
     * @param putanjaDatoteke putanja do datoteke
     * @return ArrayList ispravnih UzorakKontrola objekata
     */
    public static ArrayList<UzorakKontrola> ucitajPodatke(String putanjaDatoteke) {
        ArrayList<UzorakKontrola> uzorci = new ArrayList<>();
        ArrayList<String> greske = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(putanjaDatoteke))) {
            String redak;
            int brojRetka = 0;

            while ((redak = br.readLine()) != null) {
                brojRetka++;
                redak = redak.trim();

                if (redak.isEmpty()) {
                    continue;
                }

                // Validiranje retka
                if (!Validacija.jeValidan(redak)) {
                    String poruka = brojRetka + " -> " + redak + " -> " + Validacija.dobiPorukuGreske(redak);
                    greske.add(poruka);
                    continue;
                }

                // Parsiranje ispravnog retka
                String[] dijelovi = redak.split(";");
                String oznaka = dijelovi[0].trim();
                String materijal = dijelovi[1].trim();
                String laboratorij = dijelovi[2].trim();
                int temperatura = Integer.parseInt(dijelovi[3].trim());
                int vlaznost = Integer.parseInt(dijelovi[4].trim());
                String pakiranje = dijelovi[5].trim();

                UzorakKontrola uzorak = new UzorakKontrola(oznaka, materijal, laboratorij, temperatura, vlaznost, pakiranje);
                uzorci.add(uzorak);
            }

            // Pisanje grešaka u datoteku
            if (!greske.isEmpty()) {
                upisiGreske(greske, "1ZADATAKTsve/src/RIJESENJA/zad_1/greske.txt");
            }

        } catch (IOException e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }

        return uzorci;
    }

    /**
     * Upisuje greške u datoteku
     */
    private static void upisiGreske(ArrayList<String> greske, String putanjaDatoteke) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(putanjaDatoteke))) {
            for (String greska : greske) {
                pw.println(greska);
            }
        } catch (IOException e) {
            System.err.println("Greška pri pisanju greške datoteke: " + e.getMessage());
        }
    }
}