public class StatusKalkulator {
    public static String odrediStatus(UzorakKontrola u) {
        String pakiranje = u.getPakiranje();
        int temp = u.getTemperatura();
        int vlaznost = u.getVlaznost();

        if (pakiranje.equals("DA")) {
            if (temp >= 2) {
                if (temp <= 8) {
                    if (vlaznost <= 60) {
                        return UzorakKontrola.PRIHVACEN;
                    }
                }
            }
        }

        if (pakiranje.equals("DA")) {
            if (temp >= -1) {
                if (temp <= 1) {
                    return UzorakKontrola.DODATNA_PROVJERA;
                }
            }
            if (temp >= 9) {
                if (temp <= 12) {
                    return UzorakKontrola.DODATNA_PROVJERA;
                }
            }
            if (vlaznost >= 61) {
                if (vlaznost <= 75) {
                    return UzorakKontrola.DODATNA_PROVJERA;
                }
            }
        }

        return UzorakKontrola.ODBIJEN;
    }


    public static boolean jeKritican(UzorakKontrola u) {
        if (u.getTemperatura() < -5) {
            return true;
        }
        if (u.getTemperatura() > 15) {
            return true;
        }
        if (u.getVlaznost() > 85) {
            return true;
        }
        return false;
    }
}