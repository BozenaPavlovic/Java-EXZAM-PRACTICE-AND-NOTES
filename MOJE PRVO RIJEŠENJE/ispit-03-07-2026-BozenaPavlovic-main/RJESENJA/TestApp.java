public class TestApp {
    public static void main(String[] args) {
        String putanjaUlaz = "DATA/podaci_zad_1.txt";
        String putanjaIzvjestaj = "RJESENJA/izvjestaj.txt";
        String putanjaGreske = "RJESENJA/greske.txt";

        ObradaUzoraka obrada = new ObradaUzoraka(putanjaUlaz, putanjaIzvjestaj, putanjaGreske);
        obrada.pokreniProces();

    }
}