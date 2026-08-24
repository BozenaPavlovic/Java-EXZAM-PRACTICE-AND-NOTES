import java.io.*;
import java.util.ArrayList;

public class ObradaUzoraka {

    private String putanjaUlaz;
    private String putanjaIzvjestaj;
    private String putanjaGreske;
    private ArrayList<UzorakKontrola> ispravniUzorci;

    private int brojPrihvacenih = 0;
    private int brojDodatnih = 0;
    private int brojOdbijenih = 0;
    private double prosjecnaTemp = 0;

    public ObradaUzoraka(String putanjaUlaz, String putanjaIzvjestaj, String putanjaGreske) {
        this.putanjaUlaz = putanjaUlaz;
        this.putanjaIzvjestaj = putanjaIzvjestaj;
        this.putanjaGreske = putanjaGreske;
        this.ispravniUzorci = new ArrayList<UzorakKontrola>(); // Popravljeno dodjeljivanje
    }

    public void pokreniProces() {
        ucitajIValidirajPodatke();
        izracunajStatistiku();
        zapisiIzvjestaj();
    }

    private void ucitajIValidirajPodatke() {
        try (BufferedReader br = new BufferedReader(new FileReader(putanjaUlaz));
             BufferedWriter bwGreske = new BufferedWriter(new FileWriter(putanjaGreske))) {

            String linija;
            int brojRetka = 0;

            while ((linija = br.readLine()) != null) {
                brojRetka++;
                if (linija.trim().isEmpty()) {
                    continue;
                }

                String[] dijelovi = linija.split(";");

                if (dijelovi.length != 6) {
                    bwGreske.write(brojRetka + " -> " + linija + " -> Neispravan broj polja.\n");
                    continue;
                }

                String oznaka = dijelovi[0].trim();
                String materijal = dijelovi[1].trim();
                String laboratorij = dijelovi[2].trim();
                String pakiranje = dijelovi[5].trim();

                try {
                    int temperatura = Integer.parseInt(dijelovi[3].trim());
                    int vlaznost = Integer.parseInt(dijelovi[4].trim());

                    String validacijaRezultat = ValidUzorka.provjeriIspravnost(dijelovi, pakiranje, temperatura, vlaznost);

                    if (!validacijaRezultat.equals("OK")) {
                        bwGreske.write(brojRetka + " -> " + linija + " -> " + validacijaRezultat + "\n");
                        continue;
                    }

                    ispravniUzorci.add(new UzorakKontrola(oznaka, materijal, laboratorij, temperatura, vlaznost, pakiranje));

                } catch (NumberFormatException e) {
                    bwGreske.write(brojRetka + " -> " + linija + " -> Temperatura ili vlažnost nisu cijeli brojevi.\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Greška kod čitanja/pisanja: " + e.getMessage());
        }
    }

    private void izracunajStatistiku() {
        double sumaTemperatura = 0;

        for (int i = 0; i < ispravniUzorci.size(); i++) {
            UzorakKontrola u = ispravniUzorci.get(i);
            String status = u.izracunajStatus();

            if (status.equals(UzorakKontrola.PRIHVACEN)) {
                brojPrihvacenih++;
            } else if (status.equals(UzorakKontrola.DODATNA_PROVJERA)) {
                brojDodatnih++;
            } else if (status.equals(UzorakKontrola.ODBIJEN)) {
                brojOdbijenih++;
            }

            sumaTemperatura += u.getTemperatura();
        }

        if (!ispravniUzorci.isEmpty()) {
            prosjecnaTemp = sumaTemperatura / ispravniUzorci.size();
        }
    }

    private void zapisiIzvjestaj() {
        try (BufferedWriter bwIzvjestaj = new BufferedWriter(new FileWriter(putanjaIzvjestaj))) {

            for (int i = 0; i < ispravniUzorci.size(); i++) {
                UzorakKontrola u = ispravniUzorci.get(i);
                String status = u.izracunajStatus();

                String oznakaKriticih = "";
                if (u.jeKriticanZapis()) {
                    oznakaKriticih = " *";
                }

                bwIzvjestaj.write(u.getOznaka() + " " + u.getMaterijal() + " " + u.getLaboratorij() + " -> " + status + oznakaKriticih + "\n");
            }

            bwIzvjestaj.write("...\n");
            bwIzvjestaj.write("SAZETAK\n");
            bwIzvjestaj.write("Ukupno ispravnih zapisa: " + ispravniUzorci.size() + "\n");
            bwIzvjestaj.write("Prihvaćen: " + brojPrihvacenih + "\n");
            bwIzvjestaj.write("Dodatna provjera: " + brojDodatnih + "\n");
            bwIzvjestaj.write("Odbijen: " + brojOdbijenih + "\n");
            bwIzvjestaj.write("Prosječna temperatura: " + String.format("%.2f", prosjecnaTemp) + "\n");

        } catch (IOException e) {
            System.err.println("Greška kod pisanja izvještaja: " + e.getMessage());
        }
    }
}