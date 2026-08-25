# Java-EXZAM-PRACTICE-AND-NOTES
# Kljucne Stavke Java OOP i Pravila Strukture Koda

## 1. **Enkapsulacija (Encapsulation)**

### Privatne vs. Javne Varijable

```java
// LOSE - direktan pristup varijablama
public class Osoba {
    public String ime;
    public int godine;
    public double placa;
}

// DOBRO - enkapsulacija
public class Osoba {
    private String ime;        // Privatna - skrivena od vanjskog svijeta
    private int godine;
    private double placa;
    
    // Javni getteri - kontrolisani pristup citanju
    public String getIme() {
        return ime;
    }
    
    // Javni setteri - kontrolirana promjena vrijednosti
    public void setIme(String novoIme) {
        if (novoIme != null && !novoIme.isEmpty()) {
            this.ime = novoIme;  // Validacija!
        }
    }
    
    public void setPlaca(double novaPlaca) {
        if (novaPlaca >= 0) {    // Sprjecavanje negativne place
            this.placa = novaPlaca;
        }
    }
}
```

**KLJUCNI POJMOVI:**

- **PRIVATE**: Modifikator pristupa - varijabla je dostupna SAMO u toj klasi
  - `private double placa;` - samo ta klasa moze pristupiti
  - Osigurava da nitko ne moze direktno promijeniti vrijednost

- **PUBLIC**: Modifikator pristupa - varijabla je dostupna svima
  - `public String ime;` - dostupna je svima, bez kontrole
  - OPASNO - bilo tko moze postaviti bilo koju vrijednost

- **Getter**: Javni metod koji KONTROLISANO vraca vrijednost privatne varijable
  - `public String getIme() { return ime; }` - samo citanje, nema promjene
  - Ako trebate dodatnu logiku (transformaciju podataka) - radite je tu

- **Setter**: Javni metod koji KONTROLISANO postavlja vrijednost privatne varijable
  - `public void setIme(String novoIme)` - moze validirati prije nego postavi
  - Sprjecava nevaljane vrijednosti (npr. null, negativni brojevi)

### Zasto je ovo vazno?

| Scenario | Privatna | Javna |
|----------|----------|-------|
| **Validacija** | Mozete provjeriti vrijednost prije nego sto je postavite | Nema kontrole - bilo tko moze postaviti bilo sto |
| **Promjena interna** | Promijenite logiku bez utjecaja na vanjski kod | Svaka promjena pravi problem za sve koji koriste |
| **Sigurnost** | Zastita osjetljivih podataka | Otvoreno izlozeni podaci |
| **Fleksibilnost** | Mozete dodati logiku kasnije bez promjene interfejsa | Trebate promijeniti sve koji koriste tu varijablu |

---

## 2. **Nasljedivanje (Inheritance) - EXTENDS**

```java
// Bazna klasa - sadrzi zajednicke karakteristike
public class Zaposlenik {
    private String ime;
    private double baznaPlaca;
    
    public double izracunajPlaku() {
        return baznaPlaca;
    }
}

// Izvedena klasa - specificne karakteristike
public class Programer extends Zaposlenik {
    private String programskiJezik;
    
    @Override
    public double izracunajPlaku() {
        // Programeri imaju dodatak
        return super.izracunajPlaku() + 5000;
    }
}

public class Menadzer extends Zaposlenik {
    private int brojTimova;
    
    @Override
    public double izracunajPlaku() {
        // Menadzeri imaju drugaciji bonus
        return super.izracunajPlaku() + (1000 * brojTimova);
    }
}
```

**KLJUCNI POJMOVI:**

- **EXTENDS**: Kljucna rec - znaci "nasljeduje od" bazne klase
  - `public class Programer extends Zaposlenik`
  - Programer postaje **PODKLASA** od Zaposlenik
  - Programer AUTOMATSKI dobija sve varijable i metode iz Zaposlenik
  - Relacija: **"JE" odnos** - Programer JE vrsta Zaposlenika
  - Primer: Ako Zaposlenik ima `getIme()`, Programer ga ima bez da ga pise

- **EXTENDS ZNACI**: 
  - Nasljedi sve od Zaposlenika
  - Prosirei Zaposlenika sa novim svojstvima
  - Prepisite (prepravite) metode gdje trebate drugacije ponasanje

- **NASLJEDICANJE PRIMJER**:
```java
public class Zaposlenik {
    protected String ime;           // protected - dostupno nasljednicima
    protected double baznaPlaca;
    
    public void radi() {
        System.out.println(ime + " radi");
    }
}

public class Programer extends Zaposlenik {
    // Automatski ima: ime, baznaPlaca, radi()
    // Ali moze da prepiise radi():
    
    @Override
    public void radi() {
        System.out.println(ime + " programira");  // Koristim ime iz Zaposlenika!
    }
}

// Koriscenje:
Programer p = new Programer();
p.ime = "Ana";
p.radi();  // Ispisuje: "Ana programira"
```

---

## 3. **@Override - Anotacija**

```java
public class Zaposlenik {
    public double izracunajPlaku() {
        return 50000;
    }
}

public class Programer extends Zaposlenik {
    
    // LOSE - bez @Override (opasno!)
    public double izracunajPlaku() {
        return 60000;
    }
    
    // DOBRO - sa @Override (preporučeno!)
    @Override
    public double izracunajPlaku() {
        return 60000;
    }
}
```

**KLJUCNI POJAM - @Override:**

- **@Override**: Anotacija - oznacava da metod **PREPISUJE** metod iz bazne klase
  - To NIJE obavezno, ALI je JAKO PREPORUČENO jer:
    1. **Jasnost** - čitaoci znaju da je ovo prepis, ne novi metod
    2. **Sigurnost** - Java ce prijaviti grešku ako:
       - Nema tog metoda u baznoj klasi
       - Ime ili parametri se razlikuju
    3. **Održavanje** - ako bazna klasa promijeni metod, Java vas obavijesti

- **PRIMJER GREŠKE koja @Override hvata**:
```java
public class Zaposlenik {
    public double izracunajPlaku() { return 50000; }
}

public class Programer extends Zaposlenik {
    @Override
    public double izracunajPlackuu() {  // GRESNI NAZIV (plackuu umjesto plaku)
        return 60000;
    }
    // GREŠKA: Java javlja - "Ne postoji metod izracunajPlackuu u Zaposleniku"
}

// Bez @Override:
public class Programer extends Zaposlenik {
    public double izracunajPlackuu() {  // NECE BITI GRESKA!
        return 60000;
    }
    // PROBLEM: To je novi metod, NE prepis!
    // izracunajPlaku() i dalje dolazi iz Zaposlenika
}
```

---

## 4. **SUPER - Pristup Baznoj Klasi**

```java
public class Zaposlenik {
    private double baznaPlaca = 50000;
    
    public double izracunajPlaku() {
        return baznaPlaca;
    }
    
    public void prikaziInfos() {
        System.out.println("Zaposlenik je radnik");
    }
}

public class Programer extends Zaposlenik {
    @Override
    public double izracunajPlaku() {
        // super.izracunajPlaku() - poziva metod iz Zaposlenika
        double bazna = super.izracunajPlaku();  // 50000
        double bonus = 10000;
        return bazna + bonus;  // 60000
    }
    
    @Override
    public void prikaziInfos() {
        super.prikaziInfos();  // Ispisuje: "Zaposlenik je radnik"
        System.out.println("Programer je vrsta zaposlenika");
    }
}

public class SeniorProgramer extends Programer {
    private int godineIskustva;
    
    // Konstruktor - super() poziva konstruktor bazne klase
    public SeniorProgramer(String ime, double baznaPlaca, int godine) {
        super(ime, baznaPlaca);  // Poziva Programer konstruktor
        this.godineIskustva = godine;
    }
    
    @Override
    public double izracunajPlaku() {
        double placa = super.izracunajPlaku();  // Poziva Programer verziju (60000)
        double dodatniBonus = godineIskustva * 1000;
        return placa + dodatniBonus;
    }
}
```

**KLJUCNI POJAM - SUPER:**

- **SUPER**: Kljucna rec - pristupa varijablama i metodama iz bazne klase
  - `super.metod()` - poziva metod iz roditeljske klase
  - `super.varijabla` - pristupa varijabli iz roditeljske klase
  - `super(parametri)` - poziva konstruktor roditeljske klase

- **SUPER.METOD() - PRIMJENA**:
```java
// Bazna logika + dodatna logika
public class Programer extends Zaposlenik {
    @Override
    public double izracunajPlaku() {
        return super.izracunajPlaku() + 10000;  // Bazna placa + dodatak
    }
}
```

- **SUPER() KONSTRUKTOR - PRIMJENA**:
```java
public class Programer extends Zaposlenik {
    private String programskiJezik;
    
    public Programer(String ime, String jezik) {
        super(ime);  // Inicijalizira Zaposlenik dio
        this.programskiJezik = jezik;  // Inicijalizira Programer dio
    }
}
```

- **CHAIN KONSTRUKTORA**:
```java
SeniorProgramer sp = new SeniorProgramer("Ana", 50000, 8);
// Poziva: SeniorProgramer() -> super() -> Programer() -> super() -> Zaposlenik()
```

---

## 5. **Polimorfizam (Polymorphism)**

```java
public class HRServis {
    
    // Jedan metod - radi sa svim vrstama zaposlenika!
    public void procesuirajNagradu(Zaposlenik z) {
        double nagrada = z.izracunajPlaku() * 0.1;
        System.out.println("Nagrada: " + nagrada);
    }
}

// Koriscenje:
List<Zaposlenik> zaposleni = new ArrayList<>();
zaposleni.add(new Zaposlenik());
zaposleni.add(new Programer());
zaposleni.add(new Menadzer());

HRServis hr = new HRServis();
for (Zaposlenik z : zaposleni) {
    hr.procesuirajNagradu(z);  // Polimorfizam - isti metod, drugacije ponasanje
}
```

**KLJUCNI POJAM - POLIMORFIZAM:**

- **POLIMORFIZAM**: "Poly" = vise, "Morph" = oblik
  - Kapacitet objekta da se pojavi kao vise tipova istovremeno
  - Jedan metod `procesuirajNagradu(Zaposlenik z)` prihvata:
    - Direktne Zaposlenike
    - Programere (jer extends Zaposlenik)
    - Menadzere (jer extends Zaposlenik)

- **AUTOMATSKI @OVERRIDE POZIVA**:
  - Za Zaposlenika - koristi Zaposlenik verziju `izracunajPlaku()`
  - Za Programera - koristi Programer verziju `izracunajPlaku()`
  - Za Menadzer - koristi Menadzer verziju `izracunajPlaku()`
  - Java AUTOMATSKI bira pravu verziju prema tipu objekta

- **PRIMJER POLIMORFIZMA**:
```java
// Sve su Zaposlenik, ali razliciti tipovi
Zaposlenik z1 = new Zaposlenik();       // Obicna instanca
Zaposlenik z2 = new Programer();        // Programer kao Zaposlenik
Zaposlenik z3 = new Menadzer();         // Menadzer kao Zaposlenik

// Isti metod, razlicito ponasanje:
System.out.println(z1.izracunajPlaku());  // 50000 (Zaposlenik verzija)
System.out.println(z2.izracunajPlaku());  // 60000 (Programer verzija)
System.out.println(z3.izracunajPlaku());  // 55000 (Menadzer verzija)
```

---

## 6. **Apstrakcija (Abstraction) - ABSTRACT**

```java
// Apstraktna klasa - deifnira Šता mora biti, a ne KAKO
public abstract class Vozilo {
    private String marka;
    
    // Apstraktni metod - nema implementacije, samo deklaracija
    public abstract void pokreni();
    public abstract void zaustavi();
    
    // Konkretni metod - svi koriste istu implementaciju
    public final void prikaziMarku() {
        System.out.println("Marka: " + marka);
    }
}

public class Automobil extends Vozilo {
    @Override
    public void pokreni() {
        System.out.println("Automobil: pali se motor");
    }
    
    @Override
    public void zaustavi() {
        System.out.println("Automobil: pritiskam kocnice");
    }
}

public class Bicikl extends Vozilo {
    @Override
    public void pokreni() {
        System.out.println("Bicikl: pocinjam da pedalim");
    }
    
    @Override
    public void zaustavi() {
        System.out.println("Bicikl: pritiskam kocnice nogom");
    }
}
```

**KLJUCNI POJMOVI - APSTRAKCIJA:**

- **ABSTRACT KLASA**: Klasa koja se NE MOZE direktno instancirati
  - `public abstract class Vozilo { }` - NE MOZETE RADITI: `new Vozilo()`
  - Apstraktna klasa je samo SABLONA - blueprint
  - Trebate je nasljediti i implementirati sve apstraktne metode

- **APSTRAKTNI METOD**: Metod BEZ implementacije - samo deklaracija
  - `public abstract void pokreni();` - nema {} tela
  - Podklase MORAJU implementirati ovaj metod sa @Override
  - Sprjecava nepotpune implementacije

- **FINAL METOD**: Metod koji se NE MOZE prepraviti u podklasama
  - `public final void prikaziMarku()` - sve klase koriste ISTU verziju
  - Sprjecava gresko prepravljivanje kriticnih metoda
  - Ako trebam istu logiku u svim podklasama - koristim final

- **APSTRAKCIJA**: Skrivanje komplejnih detalja, izlaganje samo bitnog
  - Korisnik Automobila ne trebai znati kako tocno radi motor
  - Samo trebai znati: `automobil.pokreni()`

---

## 7. **INTERFEJSI (Interface) - IMPLEMENTS**

```java
// INTERFEJS - ugovor - klase MORAJU implementirati sve metode
public interface Zaposliv {
    double izracunajPlaku();
    String getIme();
    void prikaziInfos();
}

// Klasa implementira interfejs sa IMPLEMENTS
public class Programer implements Zaposliv {
    private String ime;
    private double baznaPlaca;
    
    @Override
    public double izracunajPlaku() {
        return baznaPlaca + 5000;
    }
    
    @Override
    public String getIme() {
        return ime;
    }
    
    @Override
    public void prikaziInfos() {
        System.out.println("Programer: " + ime);
    }
}

// Klasa moze implementirati vise interfejsa
public class RoboticniProgramer implements Zaposliv, Racunalac {
    // Mora implementirati sve metode iz Zaposliv i Racunalac
}
```

**KLJUCNI POJMOVI - INTERFEJSI:**

- **INTERFACE**: Ugovor - klase koje ga implementiraju MORAJU implementirati sve metode
  - `public interface Zaposliv { }` - samo DEKLARACIJE metoda
  - Nema varijabli (osim konstanti: public static final)
  - Sve metode su implicitno `public abstract`

- **IMPLEMENTS**: Kljucna rec - klasa potpisuje ugovor interfejsa
  - `public class Programer implements Zaposliv`
  - Programer MORA implementirati SVAKI metod iz Zaposliv
  - Java javlja grešku ako nesto nedostaje

- **VISE INTERFEJSA**: Klasa moze implementirati vise interfejsa odjednom
  - `public class X implements A, B, C` - mora implementirati sve
  - Nasljedivanje: samo jedan `extends`, ali vise `implements`

- **RAZLIKA: ABSTRACT KLASA vs INTERFACE**:

| Aspekt | Abstract Klasa | Interface |
|--------|---|---|
| **Nasljedivanje** | `extends` (samo jedan) | `implements` (moze vise) |
| **Varijable** | private, protected, public | samo public static final |
| **Metode** | mix - apstraktne i konkretne | samo apstraktne (Java 8+ default) |
| **Konstruktori** | mogu imati | NE MOGU imati |
| **Cilj** | "Šta JE nešto" | "Šta RADI nešto" |
| **Primer** | `abstract class Zivotinja` | `interface Plivac` |

---

## 8. **SOLID PRINCIPI - Vodic za Dobar Kod**

### S - Single Responsibility Principle (Princip Jedinstvene Odgovornosti)

```java
// LOSE - klasa radi previše
public class Zaposlenik {
    private String ime;
    private double placa;
    
    public void radiFizicki() { }              // Posao
    public void racunaPlaku() { }              // Racunovodstvo
    public void saljeEmail() { }               // Email
    public void cuva_u_bazi_podataka() { }    // Baza podataka
    // Previše razloga za promjenu!
}

// DOBRO - svaka klasa ima jednu odgovornost
public class Zaposlenik {
    private String ime;
    private double baznaPlaca;
    
    public void radi() { }
    public double getBaznaPlaca() { return baznaPlaca; }
}

public class KalkulatorPlace {
    public double izracunaj(Zaposlenik z) { 
        return z.getBaznaPlaca() + 5000;
    }
}

public class EmailServis {
    public void posalji(String email, String poruka) { 
        System.out.println("Email poslana");
    }
}

public class BazaServis {
    public void sacuva(Zaposlenik z) { 
        System.out.println("Zaposlenik sacuvan");
    }
}
```

**KLJUCNI POJAM - Single Responsibility:**
- Svaka klasa trebai ima samo JEDNU odgovornost
- Trebai biti samo JEDAN razlog za promjenu klase
- Rezultat: lakse testiranje, održavanje, manje greške

---

### O - Open/Closed Principle (Otvoreno za Prosirivanje, Zatvoreno za Promjenu)

```java
// LOSE - trebate mijenjati kod kad dodate novi tip
public class KalkulatorBonusa {
    public double izracunaj(String tip) {
        if (tip.equals("programer")) {
            return 5000;
        } else if (tip.equals("menadzer")) {
            return 10000;
        }
        return 0;
    }
}

// DOBRO - novi tipovi bez dodira postojeceg koda
public interface BonusStrategy {
    double izracunaj();
}

public class ProgrRamerBonusStrategy implements BonusStrategy {
    public double izracunaj() { return 5000; }
}

public class MenadzerBonusStrategy implements BonusStrategy {
    public double izracunaj() { return 10000; }
}

public class KalkulatorBonusa {
    public double izracunaj(BonusStrategy strategy) {
        return strategy.izracunaj();  // Radi sa bilo kojom strategijom
    }
}
```

**KLJUCNI POJAM - Open/Closed:**
- Klase trebai biti OTVORENE za prosirivanje (nove strategije)
- ALI ZATVORENE za promjenu (ne mijenjavate postojeci kod)
- Rezultat: novi kod ne kida postojeci kod

---

### L - Liskov Substitution Principle (Zamjenjivost)

```java
// LOSE - Kvadrat ne moze biti zamjena za Pravougaonik
public class Pravougaonik {
    protected int sirina;
    protected int visina;
    
    public void setSirina(int s) { this.sirina = s; }
    public void setVisina(int v) { this.visina = v; }
    public int povrsina() { return sirina * visina; }
}

public class Kvadrat extends Pravougaonik {
    @Override
    public void setSirina(int s) {
        super.setSirina(s);
        super.setVisina(s);  // PROBLEM - prepisuje logiku!
    }
}

// DOBRO - odvojeni tipovi
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

**KLJUCNI POJAM - Liskov Substitution:**
- Podklasa trebai biti zamjena za baznu klasu BEZ greške
- Ako trebam Pravougaonik, trebam moći koristiti bilo koju Pravougaonik
- Ako Kvadrat čini drugacije - ne trebai da extends Pravougaonik

---

### I - Interface Segregation Principle (Segregacija Interfejsa)

```java
// LOSE - veliki interfejs (client trebai implementirati sve)
public interface Vozilo {
    void pokreni();
    void zaustavi();
    void letetiNebom();      // Automobil to ne trebai!
    void plutatiUVodi();     // Bicikl to ne trebai!
}

public class Automobil implements Vozilo {
    // Trebam implementirati sve, cak i one koje ne trebam
    @Override
    public void letetiNebom() { 
        throw new UnsupportedOperationException(); 
    }
}

// DOBRO - mali, specificni interfejsi
public interface Motorno {
    void pokreni();
    void zaustavi();
}

public interface Letece {
    void letetiNebom();
}

public interface Plutace {
    void plutatiUVodi();
}

public class Automobil implements Motorno { }
public class Avion implements Motorno, Letece { }
public class Brodic implements Motorno, Plutace { }
```

**KLJUCNI POJAM - Interface Segregation:**
- Razbijte velike interfejse na male, specificne
- Klase implementiraju samo ono što im trebai
- Nema praznih throw UnsupportedOperationException()

---

### D - Dependency Inversion Principle (Inverzija Zavisnosti)

```java
// LOSE - zavisi od konkretne klase (ČVRSTA veza)
public class BankovniServis {
    private SqlBaza baza = new SqlBaza();  // PROBLEM - vezano za SQL
    
    public void sacuva(Zaposlenik z) {
        baza.upisi(z);
    }
    // Trebam promijeniti ako trebam drugaciju bazu
}

// DOBRO - zavisi od interfejsa (LABAVA veza)
public interface BazaServis {
    void upisi(Zaposlenik z);
}

public class SqlBaza implements BazaServis { }
public class NoSqlBaza implements BazaServis { }
public class MockBaza implements BazaServis { }

public class BankovniServis {
    private BazaServis baza;
    
    // Dependency Injection - proslijeđujete zavisnost
    public BankovniServis(BazaServis baza) {
        this.baza = baza;
    }
    
    public void sacuva(Zaposlenik z) {
        baza.upisi(z);  // Radi sa bilo kojom BazaServis
    }
}

// Koriscenje:
BankovniServis servis1 = new BankovniServis(new SqlBaza());
BankovniServis servis2 = new BankovniServis(new NoSqlBaza());
BankovniServis servis3 = new BankovniServis(new MockBaza());  // Za testiranje
```

**KLJUCNI POJAM - Dependency Inversion:**
- Klase trebai zavisiti od INTERFEJSA, ne od konkretnih klasa
- Umjesto: `private SqlBaza baza = new SqlBaza()`
- Radite: `private BazaServis baza` + konstruktor injection
- Rezultat: fleksibilan, testabilan kod

---

## 9. **Prakticni Primjer - Kompletan Sustav**

```java
// Apstraktna bazna klasa
public abstract class Zaposlenik {
    private String ime;
    private int godine;
    protected double baznaPlaca;  // protected - pristupljivo nasljednicima
    
    protected Zaposlenik(String ime, int godine, double baznaPlaca) {
        this.ime = ime;
        this.godine = godine;
        this.baznaPlaca = baznaPlaca;
    }
    
    public String getIme() { return ime; }
    public int getGodine() { return godine; }
    
    // Apstraktni metod - mora biti implementiran
    public abstract double izracunajPlaku();
    
    // Finalni metod - ne moze se prepraviti
    public final void prikaziInfos() {
        System.out.println("Zaposlenik: " + ime);
        System.out.println("godine: " + godine);
        System.out.println("Placa: " + izracunajPlaku());
    }
}

// Konkretna klasa
public class SeniorProgramer extends Zaposlenik {
    private String specijalizacija;
    private int godineIskustva;
    
    public SeniorProgramer(String ime, int godine, double baznaPlaca, 
                          String spec, int godineIskustva) {
        super(ime, godine, baznaPlaca);  // Poziva konstruktor bazne klase
        this.specijalizacija = spec;
        this.godineIskustva = godineIskustva;
    }
    
    @Override
    public double izracunajPlaku() {
        double bonus = (godineIskustva / 5) * 2000;  // +2000 za svakih 5 godina
        return baznaPlaca + bonus;
    }
    
    public String getSpecijalizacija() {
        return specijalizacija;
    }
}

// Koriscenje
public class HRServis {
    public static void main(String[] args) {
        List<Zaposlenik> zaposleni = new ArrayList<>();
        zaposleni.add(new SeniorProgramer("Ana", 30, 60000, "Backend", 8));
        zaposleni.add(new SeniorProgramer("Marko", 28, 55000, "Frontend", 5));
        
        for (Zaposlenik z : zaposleni) {
            z.prikaziInfos();  // Polimorfizam - ista verzija, drugacite place
            System.out.println("---");
        }
    }
}
```

**KLJUCNI POJMOVI U PRIMJERU:**
- **public abstract class**: Apstraktna klasa - ne moze se instancirati
- **protected**: Vidljivo nasljednicima (baznaPlaca)
- **@Override**: Pokazuje da SeniorProgramer implementira apstraktni metod
- **super()**: Poziva konstruktor roditeljske klase
- **super.baznaPlaca**: Pristupa varijabli iz roditeljske klase
- **Polimorfizam**: Ista `prikaziInfos()` poziva drugacite `izracunajPlaku()` verzije

---

## 10. **Brza Checklist za Kvalitetan OOP Kod**

- [ ] Sve karakteristike su **private** (osim ako nema valida razloga)
- [ ] Postoje **getteri/setteri** sa validacijom
- [ ] Svaka klasa ima **jednu odgovornost** (Single Responsibility)
- [ ] Koristim **interfejse** umjesto konkretnih klasa gdje je moguce
- [ ] Nema **hardkodiravnih vrijednosti** - sve je konfigurabilno
- [ ] Koristim **polimorfizam** - ne pišem `if-else` za tipove
- [ ] Kod je **fleksibilan** - lako se prosirivuje bez menjanja postojeceg
- [ ] Nema **dupliciranja koda** - DRY princip (Don't Repeat Yourself)
- [ ] Javni pristup je **minimalan** - samo ono što je neophodno
- [ ] Koristim **@Override** kada prepisuje metode
- [ ] Koristim **super** kada trebam baznu logiku + dodatnu logiku
- [ ] Koristim **abstract** za apstraktne klase i metode
- [ ] Koristim **interface** za ponasanja (sta nesto RADI)

---

## 11. **Brza Referenca - Access Modifiers**

| Sto | PRIVATE | DEFAULT | PROTECTED | PUBLIC |
|-----|---------|---------|-----------|--------|
| Interne varijable | DA | NE | NE | NE |
| Osjetljivi podaci | DA | NE | NE | NE |
| Pomocni metodi | DA | MOZDA | NE | NE |
| Zajednicke varijable bazne klase | NE | NE | DA | NE |
| Javni interfejs | NE | NE | NE | DA |
| Getteri/Setteri | NE | NE | NE | DA |
| Konstante | NE | NE | NE | DA |

---

## 12. **Brza Referenca - EXTENDS vs IMPLEMENTS**

| Aspekt | EXTENDS (Apstraktna klasa) | IMPLEMENTS (Interface) |
|--------|---|---|
| **Kljucna rec** | `extends` | `implements` |
| **Broj** | Samo jedan | Moze vise |
| **Varijable** | private, protected, public | samo public static final |
| **Metode** | mix - apstraktne i konkretne | samo apstraktne |
| **Konstruktori** | mogu imati | NE mogu imati |
| **Cilj** | "Sta JE nesto" | "Sta RADI nesto" |
| **Primer** | `class Programer extends Zaposlenik` | `class Programer implements Zaposliv` |

---

## 13. **Brza Referenca - @Override Upotreba**

```java
// LOSE - bez @Override
public class Programer extends Zaposlenik {
    public double izracunajPlaku() {  // Nije jasno da je ovo prepis!
        return 60000;
    }
}

// DOBRO - sa @Override
public class Programer extends Zaposlenik {
    @Override
    public double izracunajPlaku() {  // Jasno je da je ovo prepis!
        return 60000;
    }
}

// Razlog koristiti @Override:
// 1. Jasnost - čitaoci znaju da je ovo prepis iz bazne klase
// 2. Sigurnost - Java prijavlja grešku ako nema tog metoda u baznoj klasi
// 3. Održavanje - ako bazna klasa promijeni metod, Java vas obavijesti
// 4. Profesionalnost - standardi koda
```

---

## 14. **Brza Referenca - SUPER Upotreba**

```java
// 1. super.metod() - poziva metod iz bazne klase
public class Programer extends Zaposlenik {
    @Override
    public double izracunajPlaku() {
        double baznaPlaca = super.izracunajPlaku();  // Bazna logika
        return baznaPlaca + 5000;                     // + dodatak
    }
}

// 2. super(parametri) - poziva konstruktor bazne klase
public class Programer extends Zaposlenik {
    private String programskiJezik;
    
    public Programer(String ime, double placa, String jezik) {
        super(ime, placa);           // Inicijalizira Zaposlenik dio
        this.programskiJezik = jezik; // Inicijalizira Programer dio
    }
}

// 3. Lanac konstruktora
SeniorProgramer sp = new SeniorProgramer("Ana", 50000, 8);
// Redoslijed:
// 1. SeniorProgramer() konstruktor
// 2. super() -> Programer() konstruktor
// 3. super() -> Zaposlenik() konstruktor
```

---

## 15. **OOP Hijerarhija - Kako Radi**

```
                    Object (sve klase naslijede od Object)
                       |
                    Zaposlenik (abstraktna bazna klasa)
                   /    |      \
              Programer  Menadzer  Inzinjer
              /    \
        Junior  Senior

Primjer:
- Zaposlenik ima zajednicke karakteristike
- Programer, Menadzer, Inzinjer su specificne vrste Zaposlenika
- Junior, Senior su specificne vrste Programera
```

---

## Zakljucak - **BEST PRACTICES**

```
ENKAPSULACIJA
    |
    v
private varijable + getteri/setteri sa validacijom
    |
    v
NASLJEDICIVANJE
    |
    v
extends za zajednicke karakteristike
    |
    v
INTERFEJSI
    |
    v
implements za ponasanja (sta nesto RADI)
    |
    v
POLIMORFIZAM
    |
    v
isti metod, drugacito ponasanje ovisno o tipu
    |
    v
SOLID PRINCIPI
    |
    v
čitljiv, održavan, fleksibilan kod
```

---

**Kljucni zakljucak:** 
- Enkapsulacija (private + getteri/setteri) je TEMELJ
- SOLID principi su STRUKTURA za dobar kod
- @Override, extends, super su ALATI za proper nasljedivanje
- Polimorfizam je SNAGA OOP-a
- Interfejsi su FLEKSIBILNOST
- Abstract klase su SABLONA

**Rezultat:** Kod koji je lako razumevljiv, održavan, testabilan i fleksibilan! 🚀
