# Java-EXZAM-PRACTICE-AND-NOTES
# Ključne Stavke Java OOP i Pravila Strukture Koda

## 1. **Enkapsulacija (Encapsulation)**

### Privatne vs. Javne Varijable

```java
// ❌ LOŠE - direktan pristup varijablama
public class Osoba {
    public String ime;
    public int godine;
    public double plata;
}

// ✅ DOBRO - enkapsulacija
public class Osoba {
    private String ime;        // Privatna - skrivena od vanjskog svijeta
    private int godine;
    private double plata;
    
    // Javni getteri - kontrolisani pristup čitanju
    public String getIme() {
        return ime;
    }
    
    // Javni setteri - kontrolirana promjena vrijednosti
    public void setIme(String novoIme) {
        if (novoIme != null && !novoIme.isEmpty()) {
            this.ime = novoIme;  // Validacija!
        }
    }
    
    public void setPlata(double novaPlata) {
        if (novaPlata >= 0) {    // Sprječavanje negativne plate
            this.plata = novaPlata;
        }
    }
}
```

### Zašto je ovo važno?

| Scenario | Privatna | Javna |
|----------|----------|-------|
| **Validacija** | Možete provjeriti vrijednost prije nego što je setujete | Nema kontrole - bilo tko može postaviti bilo što |
| **Promjena interna** | Promijenite logiku bez utjecaja na vanjski kod | Svaka promjena pravi problem za sve koji koriste |
| **Sigurnost** | Zaštita osjetljivih podataka | Otvoreno izloženi podaci |

---

## 2. **Nasljeđivanje (Inheritance)**

```java
// Bazna klasa - sadrži zajedničke karakteristike
public class Zaposlenik {
    private String ime;
    private double baznaPlata;
    
    public double izracunajPlatu() {
        return baznaPlata;
    }
}

// Izvedene klase - specifične karakteristike
public class Programer extends Zaposlenik {
    private String programskiJezik;
    
    @Override
    public double izracunajPlatu() {
        // Programeri imaju dodatak
        return super.izracunajPlatu() + 5000;
    }
}

public class Menadzer extends Zaposlenik {
    private int brojTimova;
    
    @Override
    public double izracunajPlatu() {
        // Menadžeri imaju drugačiji bonus
        return super.izracunajPlatu() + (1000 * brojTimova);
    }
}
```

### Pravilo DRY (Don't Repeat Yourself)
- Zajedničke karakteristike → bazna klasa
- Specifične karakteristike → izvedene klase
- Izbjegavate dupliranje koda

---

## 3. **Polimorfizam (Polymorphism)**

```java
public class NagradiZaposlenike {
    
    // Jedan metod - radi sa svim vrstama zaposlenih!
    public void procesuirajNagradu(Zaposlenik z) {
        double nagrada = z.izracunajPlatu() * 0.1;
        System.out.println("Nagrada: " + nagrada);
    }
}

// Korištenje:
List<Zaposlenik> zaposleni = new ArrayList<>();
zaposleni.add(new Programer());
zaposleni.add(new Menadzer());
zaposleni.add(new Zaposlenik());

NagradiZaposlenike nagrade = new NagradiZaposlenike();
for (Zaposlenik z : zaposleni) {
    nagrade.procesuirajNagradu(z);  // Polimorfizam - isti metod, drugačije ponašanje
}
```

---

## 4. **Apstrakcija (Abstraction)**

```java
// Apstraktna klasa - definira što mora biti, a ne kako
public abstract class Vozilo {
    private String marka;
    private double cijena;
    
    // Apstraktni metod - svako vozilo mora ga implementirati
    public abstract void pokreni();
    public abstract void zaustavi();
    
    // Konkretni metod - može biti isti za sve
    public final void prikaziCijenu() {
        System.out.println("Cijena: " + cijena);
    }
}

public class Automobil extends Vozilo {
    @Override
    public void pokreni() {
        System.out.println("Automobil: palim motor...");
    }
    
    @Override
    public void zaustavi() {
        System.out.println("Automobil: pritiskam kočnice...");
    }
}

public class Bicikl extends Vozilo {
    @Override
    public void pokreni() {
        System.out.println("Bicikl: počinjem da pedalim...");
    }
    
    @Override
    public void zaustavi() {
        System.out.println("Bicikl: pritiskam kočnice nogom...");
    }
}
```

---

## 5. **Access Modifiers - Detaljno Objašnjenje**

```java
public class PrimerAccessModifiers {
    
    // PRIVATE - dostupno SAMO u ovoj klasi
    private String tajnaLozinka;
    
    // DEFAULT (bez modifikatora) - dostupno u istom paketu
    String paketnaVarijabla;
    
    // PROTECTED - dostupno u klasi, paketu I nasljednicima
    protected String zastitenaVarijabla;
    
    // PUBLIC - dostupno SVUDA
    public String javnaVarijabla;
}
```

### Kada koristiti što?

```java
public class BankovniRacun {
    
    // PRIVATE - tajne operacije, nitko ne smije direktno pristupiti
    private double stanje;
    private String PIN;
    
    private boolean validacijaTransakcije(double iznos) {
        return iznos > 0 && iznos <= stanje;
    }
    
    // PUBLIC - javni interfejs - što račun nudi
    public void uplata(double iznos) {
        if (iznos > 0) {
            stanje += iznos;
            System.out.println("Uplata uspješna");
        }
    }
    
    public void isplata(double iznos) {
        if (validacijaTransakcije(iznos)) {
            stanje -= iznos;
            System.out.println("Isplata uspješna");
        } else {
            System.out.println("Nema dovoljno sredstava");
        }
    }
    
    // PUBLIC GETTER - kontrolirani pristup čitanju
    public double vidiBankovniExtract() {
        return stanje;
    }
}
```

---

## 6. **SOLID Principi (Najvažniji za OOP)**

### S - Single Responsibility Principle
```java
// ❌ LOŠE - klasa radi previše
public class Zaposlenik {
    private String ime;
    
    public void radiFizicki() { }
    public void racunaPlatu() { }
    public void saljeEmail() { }
    public void cuva_u_bazu() { }
}

// ✅ DOBRO - svaka klasa ima jednu odgovornost
public class Zaposlenik {
    private String ime;
    public void radiFizicki() { }
}

public class KalkulatorPlace {
    public double izracunaj(Zaposlenik z) { }
}

public class EmailServis {
    public void posalji(String poruka) { }
}

public class BazaServis {
    public void sacuva(Zaposlenik z) { }
}
```

### O - Open/Closed Principle
```java
// ❌ LOŠE - zatvoreno za proširenje
public class KalkulatorBonusa {
    public double izracunaj(String tip) {
        if (tip.equals("programer")) {
            return 5000;
        } else if (tip.equals("menadzer")) {
            return 10000;
        }
        // Svaki put kad dodate novi tip - menjate ovu klasu!
        return 0;
    }
}

// ✅ DOBRO - otvoreno za proširenje
public interface BonusStrategy {
    double izracunaj();
}

public class ProgrRamerBonusStrategy implements BonusStrategy {
    public double izracunaj() {
        return 5000;
    }
}

public class MenadzerBonusStrategy implements BonusStrategy {
    public double izracunaj() {
        return 10000;
    }
}

// Nova klasa - nema zagađivanja postojećeg koda
public class KalkulatorBonusa {
    public double izracunaj(BonusStrategy strategy) {
        return strategy.izracunaj();
    }
}
```

### L - Liskov Substitution Principle
```java
// ❌ LOŠE - ne mogu zamjeniti
public class Pravougaonik {
    private int sirina;
    private int visina;
    
    public void setSirina(int s) { this.sirina = s; }
    public void setVisina(int v) { this.visina = v; }
    public int povrsina() { return sirina * visina; }
}

public class Kvadrat extends Pravougaonik {
    @Override
    public void setSirina(int s) {
        super.setSirina(s);
        super.setVisina(s);  // Prepisuje logiku - PROBLEM!
    }
}

// ✅ DOBRO - Liskovljev princip
public interface Oblik {
    int povrsina();
}

public class Pravougaonik implements Oblik {
    public int povrsina() { return sirina * visina; }
}

public class Kvadrat implements Oblik {
    public int povrsina() { return strana * strana; }
}
```

### I - Interface Segregation Principle
```java
// ❌ LOŠE - veliki interfejs
public interface Vozilo {
    void pokreni();
    void zaustavi();
    void letetiNebom();
    void plutatiUVodi();
}

// ✅ DOBRO - mali, specifični interfejsi
public interface Motorno {
    void pokreni();
    void zaustavi();
}

public interface Letece {
    void letetiNebom();
}

public class Avion implements Motorno, Letece { }
public class Automobil implements Motorno { }
```

### D - Dependency Inversion Principle
```java
// ❌ LOŠE - zavisi od konkretne klase
public class BankovniServis {
    private SqlBaza baza = new SqlBaza();
    
    public void saci(Zaposlenik z) {
        baza.upisi(z);  // Čvrsto povezan sa SQL-om!
    }
}

// ✅ DOBRO - zavisi od interfejsa
public interface BazaServis {
    void upisi(Zaposlenik z);
}

public class SqlBaza implements BazaServis {
    public void upisi(Zaposlenik z) { }
}

public class NoSqlBaza implements BazaServis {
    public void upisi(Zaposlenik z) { }
}

public class BankovniServis {
    private BazaServis baza;
    
    // Injection - prolazite zavisnost
    public BankovniServis(BazaServis baza) {
        this.baza = baza;
    }
    
    public void saci(Zaposlenik z) {
        baza.upisi(z);  // Fleksibilno!
    }
}
```

---

## 7. **Praktičan Primjer - Kompletan Sustav**

```java
// Apstraktna bazna klasa
public abstract class Zaposlenik {
    private String ime;
    private int godine;
    protected double baznaPlata;  // Protected - pristupljivo nasljednicima
    
    protected Zaposlenik(String ime, int godine, double baznaPlata) {
        this.ime = ime;
        this.godine = godine;
        this.baznaPlata = baznaPlata;
    }
    
    // Getteri
    public String getIme() { return ime; }
    public int getGodine() { return godine; }
    
    // Apstraktni metod - mora biti implementiran
    public abstract double izracunajPlatu();
    
    // Finalni metod - ne može se prepraviti
    public final void prikaziInfos() {
        System.out.println("Zaposlenik: " + ime);
        System.out.println("godine: " + godine);
        System.out.println("Plata: " + izracunajPlatu());
    }
}

// Konkretna klasa
public class SeniorProgramer extends Zaposlenik {
    private String specijalizacija;
    private int godinaIskustva;
    
    public SeniorProgramer(String ime, int godine, double baznaPlata, 
                          String spec, int godineIskustva) {
        super(ime, godine, baznaPlata);
        this.specijalizacija = spec;
        this.godinaIskustva = godineIskustva;
    }
    
    @Override
    public double izracunajPlatu() {
        double bonus = (godinaIskustva / 5) * 2000;  // +2000 za svakih 5 godina
        return baznaPlata + bonus;
    }
    
    public String getSpecijalizacija() {
        return specijalizacija;
    }
}

// Korištenje
public class HRServis {
    public static void main(String[] args) {
        List<Zaposlenik> zaposleni = new ArrayList<>();
        zaposleni.add(new SeniorProgramer("Ana", 30, 60000, "Backend", 8));
        zaposleni.add(new SeniorProgramer("Marko", 28, 55000, "Frontend", 5));
        
        for (Zaposlenik z : zaposleni) {
            z.prikaziInfos();  // Polimorfizam - isti metod, drugačite plate!
            System.out.println("---");
        }
    }
}
```

---

##**Brza Checklist za Kvalitetan OOP Kod:**

- [ ] Sve karakteristike su **private** (osim ako nema valida razloga)
- [ ] Postoje **getteri/setteri** sa validacijom
- [ ] Svaka klasa ima **jednu odgovornost** (Single Responsibility)
- [ ] Koristim **interfejse** umesto konkretnih klasa gdje je moguće
- [ ] Nema **hardkodiravnih vrijednosti** - sve je konfigurabilno
- [ ] Koristim **polimorfizam** - ne pišem `if-else` za tipove
- [ ] Kod je **fleksibilan** - lako se proširuje bez menjanja postojećeg
- [ ] Nema **dupliciranja koda** - DRY princip
- [ ] Javni pristup je **minimalan** - samo ono što je neophodno

---

Zaključak:

| Što | PRIVATE | PUBLIC |
|-----|---------|--------|
| Interne varijable | ✅ | ❌ |
| Osjetljivi podaci | ✅ | ❌ |
| Pomoćni metodi | ✅ | ❌ |
| Javni interfejs | ❌ | ✅ |
| Getteri/Setteri | ❌ | ✅ |
| Konstante | ❌ | ✅ |

---

**Ključni zaključak:** Enkapsulacija (privatne varijable + getteri/setteri) je temelj, a SOLID principi su struktura za održiv, fleksibilan kod koji se lako održava i proširuje! 🚀
