package RIJESENJA.zad_2;

import java.io.*;
import java.util.ArrayList;

public class AUX_RW {

    // Privatni konstruktor - ova klasa se ne instancira
    private AUX_RW() {
        throw new AssertionError("Ova klasa ne bi trebala biti instancirana");
    }

    /**
     * Upisuje aktivnosti u tekstualnu datoteku
     * Format: naziv;tema;tip;trajanje;napomena
     */
    public static void upisiAktivnosti(ArrayList<AktivnostUcenja> aktivnosti, String putanjaDatoteke) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(putanjaDatoteke))) {
            for (AktivnostUcenja aktivnost : aktivnosti) {
                String redak = aktivnost.getNaziv() + ";" +
                        aktivnost.getTema() + ";" +
                        aktivnost.getTip() + ";" +
                        aktivnost.getTrajanje() + ";" +
                        aktivnost.getNapomena();
                pw.println(redak);
            }
            System.out.println("Aktivnosti uspješno spremljene u: " + putanjaDatoteke);
        } catch (IOException e) {
            System.err.println("Greška pri pisanju datoteke: " + e.getMessage());
        }
    }

    /**
     * Učitava aktivnosti iz tekstualne datoteke
     * Format: naziv;tema;tip;trajanje;napomena
     */
    public static ArrayList<AktivnostUcenja> ucitajAktivnosti(String putanjaDatoteke) {
        ArrayList<AktivnostUcenja> aktivnosti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(putanjaDatoteke))) {
            String redak;
            while ((redak = br.readLine()) != null) {
                redak = redak.trim();
                if (redak.isEmpty()) {
                    continue;
                }

                String[] dijelovi = redak.split(";");
                if (dijelovi.length != 5) {
                    System.err.println("Upozorenje: Redak ima pogrešan broj polja: " + redak);
                    continue;
                }

                try {
                    String naziv = dijelovi[0].trim();
                    String tema = dijelovi[1].trim();
                    TipAktivnosti tip = TipAktivnosti.valueOf(dijelovi[2].trim());
                    int trajanje = Integer.parseInt(dijelovi[3].trim());
                    String napomena = dijelovi[4].trim();

                    AktivnostUcenja aktivnost = new AktivnostUcenja(naziv, tema, tip, trajanje, napomena);
                    aktivnosti.add(aktivnost);
                } catch (NumberFormatException e) {
                    System.err.println("Upozorenje: Trajanje nije broj: " + redak);
                } catch (IllegalArgumentException e) {
                    System.err.println("Upozorenje: Nepoznat tip aktivnosti: " + redak);
                }
            }
            System.out.println("Aktivnosti uspješno učitane iz: " + putanjaDatoteke);
        } catch (FileNotFoundException e) {
            System.err.println("Datoteka nije pronađena: " + putanjaDatoteke);
        } catch (IOException e) {
            System.err.println("Greška pri čitanju datoteke: " + e.getMessage());
        }

        return aktivnosti;
    }
}