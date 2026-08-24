package zad_1;

public class Validacija {

    /**
     * Provjerava je li redak u pravilnom formatu i vrijednosti su li ispravne
     * @param redak redak iz datoteke
     * @return true ako je redak validan
     */
    public static boolean jeValidan(String redak) {
        if (redak == null || redak.trim().isEmpty()) {
            return false;
        }

        String[] dijelovi = redak.split(";");

        // Mora imati točno 6 polja
        if (dijelovi.length != 6) {
            return false;
        }

        String oznaka = dijelovi[0].trim();
        String materijal = dijelovi[1].trim();
        String laboratorij = dijelovi[2].trim();
        String temperaturaStr = dijelovi[3].trim();
        String vlaznostStr = dijelovi[4].trim();
        String pakiranje = dijelovi[5].trim();

        // Provjeravanje praznih polja
        if (oznaka.isEmpty() || materijal.isEmpty() || laboratorij.isEmpty()) {
            return false;
        }

        // Provjeravanje pakiranje (DA ili NE)
        if (!"DA".equals(pakiranje) && !"NE".equals(pakiranje)) {
            return false;
        }

        // Parsiranje temperature
        int temperatura;
        try {
            temperatura = Integer.parseInt(temperaturaStr);
        } catch (NumberFormatException e) {
            return false;
        }

        // Provjeravanje raspona temperature (-20 do 40)
        if (temperatura < -20 || temperatura > 40) {
            return false;
        }

        // Parsiranje vlažnosti
        int vlaznost;
        try {
            vlaznost = Integer.parseInt(vlaznostStr);
        } catch (NumberFormatException e) {
            return false;
        }

        // Provjeravanje vlažnosti (0 do 100)
        if (vlaznost < 0 || vlaznost > 100) {
            return false;
        }

        return true;
    }

    /**
     * Vraća kratko objašnjenje zašto redak nije validan
     */
    public static String dobiPorukuGreske(String redak) {
        if (redak == null || redak.trim().isEmpty()) {
            return "Prazan redak";
        }

        String[] dijelovi = redak.split(";");

        if (dijelovi.length != 6) {
            return "Pogrešan broj polja (očekuje se 6)";
        }

        String oznaka = dijelovi[0].trim();
        String materijal = dijelovi[1].trim();
        String laboratorij = dijelovi[2].trim();
        String temperaturaStr = dijelovi[3].trim();
        String vlaznostStr = dijelovi[4].trim();
        String pakiranje = dijelovi[5].trim();

        if (oznaka.isEmpty() || materijal.isEmpty() || laboratorij.isEmpty()) {
            return "Prazno polje";
        }

        if (!"DA".equals(pakiranje) && !"NE".equals(pakiranje)) {
            return "Pakiranje nije DA niti NE";
        }

        try {
            int temperatura = Integer.parseInt(temperaturaStr);
            if (temperatura < -20 || temperatura > 40) {
                return "Temperatura van raspona -20 do 40";
            }
        } catch (NumberFormatException e) {
            return "Temperatura nije broj";
        }

        try {
            int vlaznost = Integer.parseInt(vlaznostStr);
            if (vlaznost < 0 || vlaznost > 100) {
                return "Vlažnost van raspona 0 do 100";
            }
        } catch (NumberFormatException e) {
            return "Vlažnost nije broj";
        }

        return "Nepoznata greška";
    }
}
