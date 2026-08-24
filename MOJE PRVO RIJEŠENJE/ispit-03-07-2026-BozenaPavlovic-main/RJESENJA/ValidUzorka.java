public class ValidUzorka {
    public static String provjeriIspravnost(String[] dijelovi, String pakiranje, int temp, int vlaznost) {
        if (dijelovi.length != 6) {
            return "Neispravan broj polja.";
        }
        if (!pakiranje.equals("DA") && !pakiranje.equals("NE")) {
            return "Polje pakiranje mora biti DA ili NE.";
        }
        if (temp < -20 || temp > 40) {
            return "Temperatura izvan raspona [-20, 40].";
        }
        if (vlaznost < 0 || vlaznost > 100) {
            return "Vlažnost izvan raspona [0, 100].";
        }
        return "OK";
    }
}