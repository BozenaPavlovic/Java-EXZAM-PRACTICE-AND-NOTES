package zad_1;

import java.io.*;
import java.util.ArrayList;

public class Izvještaj {

    /**
     * Generira i upisuje izvještaj u datoteku
     * @param uzorci lista UzorakKontrola objekata
     * @param putanjaDatoteke putanja do izlazne datoteke
     */
    public static void generirajIzvještaj(ArrayList<UzorakKontrola> uzorci, String putanjaDatoteke) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(putanjaDatoteke))) {

            // Pisanje podataka za svaki uzorak
            for (UzorakKontrola uzorak : uzorci) {
                String redak = uzorak.getOznaka() + " " + uzorak.getMaterijal() + " " +
                               uzorak.getLaboratorij() + " -> " + uzorak.getStatus();

                if (uzorak.jeKriticanZapis()) {
                    redak += " *";
                }

                pw.println(redak);
            }

            // Pisanje sažetka
            pw.println();
            pw.println("SAZETAK");
            pw.println("Ukupno ispravnih zapisa: " + uzorci.size());

            // Brojanje po statusima
            int prihvacen = 0, dodatna = 0, odbijen = 0;
            double sumTemperature = 0;

            for (UzorakKontrola uzorak : uzorci) {
                sumTemperature += uzorak.getTemperatura();

                switch (uzorak.getStatus()) {
                    case PRIHVACEN:
                        prihvacen++;
                        break;
                    case DODATNA_PROVJERA:
                        dodatna++;
                        break;
                    case ODBIJEN:
                        odbijen++;
                        break;
                }
            }

            pw.println("Prihvaćen: " + prihvacen);
            pw.println("Dodatna provjera: " + dodatna);
            pw.println("Odbijen: " + odbijen);

            // Prosječna temperatura
            if (uzorci.size() > 0) {
                double prosjecnaTemperatura = sumTemperature / uzorci.size();
                pw.printf("Prosječna temperatura: %.2f%n", prosjecnaTemperatura);
            }

            System.out.println("Izvještaj napisan u: " + putanjaDatoteke);

        } catch (IOException e) {
            System.err.println("Greška pri pisanju izvještaja: " + e.getMessage());
        }
    }
}
