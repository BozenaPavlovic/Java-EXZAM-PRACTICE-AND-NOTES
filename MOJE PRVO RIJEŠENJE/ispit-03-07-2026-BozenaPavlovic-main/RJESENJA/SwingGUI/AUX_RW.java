package SwingGUI;

import SwingGUI.TipAktivnosti;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class AUX_RW {

    private AUX_RW() {
    }

    public static void spremiTXT(String putanja, List<AktivnostUcenja> aktivnosti) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(putanja))) {
            for (AktivnostUcenja a : aktivnosti) {
                bw.write(a.getNaziv() + ";" +
                        a.getTema() + ";" +
                        a.getTip().name() + ";" +
                        a.getTrajanje() + ";" +
                        (a.getNapomena() != null ? a.getNapomena() : "") + "\n");
            }
        } catch (IOException e) {
            System.err.println("Greška kod spremanja: " + e.getMessage());
        }
    }

    public static List<AktivnostUcenja> ucitajTXT(String putanja) {
        List<AktivnostUcenja> aktivnosti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(putanja))) {
            String linija;
            while ((linija = br.readLine()) != null) {
                if (linija.trim().isEmpty()) continue;

                String[] dijelovi = linija.split(";");
                if (dijelovi.length < 4) continue;

                String naziv = dijelovi[0].trim();
                String tema = dijelovi[1].trim();
                TipAktivnosti tip = TipAktivnosti.valueOf(dijelovi[2].trim());
                int trajanje = Integer.parseInt(dijelovi[3].trim());
                String napomena = dijelovi.length > 4 ? dijelovi[4].trim() : "";

                aktivnosti.add(new AktivnostUcenja(naziv, tema, tip, trajanje, napomena));
            }
        } catch (IOException e) {
            System.err.println("Greška kod učitavanja: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Neispravan tip aktivnosti: " + e.getMessage());
        }

        return aktivnosti;
    }
}