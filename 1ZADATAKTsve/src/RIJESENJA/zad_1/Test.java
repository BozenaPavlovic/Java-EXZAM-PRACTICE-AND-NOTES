package RIJESENJA.zad_1;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        UzorakKontrola u1 = new UzorakKontrola(
                "U-001", "Krv", "Lab A", 5, 50, "DA");

        UzorakKontrola u2 = new UzorakKontrola(
                "U-002", "Voda", "Lab B", 10, 55, "DA");

        UzorakKontrola u3 = new UzorakKontrola(
                "U-003", "Tlo", "Lab C", 20, 90, "NE");

        System.out.println(u1);
        System.out.println("Kritičan: " + u1.jeKriticanZapis());

        System.out.println(u2);
        System.out.println("Kritičan: " + u2.jeKriticanZapis());

        System.out.println(u3);
        System.out.println("Kritičan: " + u3.jeKriticanZapis());


        ArrayList<UzorakKontrola> uzorci =
                UcitavanjePodataka.ucitajPodatke("1ZADATAKTsve/src/DATA/podaci_zad_1.txt");

        System.out.println("Učitano uzoraka: " + uzorci.size());

        for (UzorakKontrola uzorak : uzorci) {
            System.out.println(uzorak);
            System.out.println("Kritičan zapis: " + uzorak.jeKriticanZapis());
        }

    }
}
