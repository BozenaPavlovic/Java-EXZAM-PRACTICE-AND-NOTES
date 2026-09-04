# **Kompletna Lista Mogućih Pitanja Profesora - Zad_1**

Pogledajmo sve datoteke još jednom i identifikujemo što bi moglo biti pitano:

---

## **DOMAĆA ZADAĆA - 25+ Mogućih Pitanja Profesora**

### **GRUPA 1: ARHITEKTURA I DIZAJN (❌ Već obrađeno)**

**P1:** Objasnite arhitekturu zadatka 1
- Odgovor: Slojevita arhitektura sa jasnom separacijom odgovornosti

**P2:** Što je Single Responsibility Principle?
- Odgovor: Svaka klasa ima jednu odgovornost

**P3:** Zašto su sve klase `final`?
- Odgovor: Sprječavanje neželjenog nasljeđivanja i polimorfnih grešaka

---

### **GRUPA 2: ENUMI (❌ Već obrađeno)**

**P4:** Što je ENUM i zašto se koristi?

**P5:** Je li `Enum` klasa apstraktna?

---

### **GRUPA 3: IMMUTABILITY - 🔴 NOVO!**

**P6: Što znači "immutable" i je li PovratGradje immutable?"**

```java
final class PovratGradje {
    private final String inventarni_broj;      // ← final!
    private final String naslov;               // ← final!
    private final String korisnik;             // ← final!
    private final int dani_kasnjenja;          // ← final!
    private final int broj_ostecenja;          // ← final!
    
    // Nema settera - ne mogu se mijenjati!
}
```

**Odgovor:**
```
Da, PovratGradje je immutable jer:
1. Sve varijable su private final - ne mogu se promijeniti nakon inicijalizacije
2. Nema settera - nema metoda za promjenu vrijednosti
3. Konstruktor je jedini način da se postave vrijednosti
4. Getteri samo vraćaju vrijednosti, ne mogu ih promijeniti

Immutable objekti su sigurniji jer:
- Thread-safe su automatski
- Ne trebate se brinuti o bočnim efektima
- Lakše je razumijevati kod jer se objekti ne mijenjaju

Primjer greške (ako ne bi bili final):
pg.dani_kasnjenja = 999;  // ❌ Sada je računica kriva!
```

---

### **GRUPA 4: PRIVATNI KONSTRUKTOR - 🔴 NOVO!**

**P7: Što je `private` konstruktor i gdje ga vidjeli?**

```java
final class FileReader {
    private FileReader() {  // ← Privatni konstruktor!
    }
    
    static DataContainer readAndValidate() throws IOException {
        // Samo static metode
    }
}
```

**Odgovor:**
```
Privatni konstruktor sprječava instanciranje klase. 
FileReader ima samo static metode - ne trebate instances.

Bez privatnog konstruktora:
FileReader fr = new FileReader();  // ❌ Nema smisla! 
fr.readAndValidate();               // Trebate instances za ništa

Sa privatnim konstruktorom:
FileReader fr = new FileReader();  // ❌ COMPILE ERROR!

Trebate koristiti:
FileReader.readAndValidate();      // ✅ Direktno static metodu
```

**Gdje se koristi:**
```java
final class FileReader { private FileReader() {} }
final class ErrorWriter { private ErrorWriter() {} }
final class ReportWriter { private ReportWriter() {} }
final class LineFormatValidator { private LineFormatValidator() {} }
```

---

### **GRUPA 5: INNER CLASSES (UGNIJEŽĐENE KLASE) - 🔴 NOVO!**

**P8: Što je DataContainer i zašto je unutar FileReader klase?**

```java
final class FileReader {
    
    static class DataContainer {  // ← Inner class!
        private final List<PovratGradje> validRecords;
        private final List<GreskaZapisa> errors;
        
        DataContainer(List<PovratGradje> validRecords, List<GreskaZapisa> errors) {
            // ...
        }
    }
    
    static DataContainer readAndValidate() throws IOException {
        // ...
        return new DataContainer(validRecords, errors);  // ← Koristi inner class
    }
}
```

**Odgovor:**
```
DataContainer je inner (ugniježđena) statička klasa.

Razlozi:
1. Logički je vezan samo za FileReader
2. Koristi se samo kao povratna vrijednost readAndValidate()
3. Sprječava zagađenje namespace-a sa klasama koje se ne trebaju drugdje
4. Jasno je da je DataContainer samo za FileReader

Bez inner class:
- Trebali bi dva fajla (FileReader.java, DataContainer.java)
- DataContainer bi bio dostupan cijelom paketu
- Manje je jasno da se koristi samo za FileReader

Sa inner class:
- Sve je logički grupirano
- DataContainer je dostupan samo kroz FileReader
```

**Korištenje:**
```java
FileReader.DataContainer data = FileReader.readAndValidate();
List<PovratGradje> valid = data.getValidRecords();
List<GreskaZapisa> errors = data.getErrors();
```

---

### **GRUPA 6: DEFENSIVE COPYING - 🔴 NOVO!**

**P9: Što je defensive copying i gdje se koristi u vašem kodu?**

```java
// U DataContainer:
DataContainer(List<PovratGradje> validRecords, List<GreskaZapisa> errors) {
    // ❌ LOŠE - referenca na istu listu:
    // this.validRecords = validRecords;
    
    // ✅ DOBRO - stvarna kopija:
    this.validRecords = new ArrayList<>(validRecords);  // Defensive copy!
    this.errors = new ArrayList<>(errors);             // Defensive copy!
}

List<PovratGradje> getValidRecords() {
    // ❌ LOŠE - vraćate referencu:
    // return validRecords;
    
    // ✅ DOBRO - vraćate kopiju:
    return new ArrayList<>(validRecords);  // Defensive copy!
}
```

**Odgovor:**
```
Defensive copying znači pravljenje kopije umjesto dijeljenja reference.

Problem bez defensive copying:

List<PovratGradje> original = Arrays.asList(pg1, pg2, pg3);
FileReader.DataContainer data = new DataContainer(original);

// Netko drugi mijenja original listu:
original.clear();  // ❌ Sada je i data.validRecords prazan!

Rješenje sa defensive copying:

List<PovratGradje> original = Arrays.asList(pg1, pg2, pg3);
FileReader.DataContainer data = new DataContainer(original);

original.clear();  // OK - data.validRecords je nepromjenjen!

Isto za getters:
List<PovratGradje> valid1 = data.getValidRecords();
List<PovratGradje> valid2 = data.getValidRecords();

valid1.clear();  // OK - valid2 je nepromjenjen!
```

---

### **GRUPA 7: TRY-WITH-RESOURCES - 🔴 NOVO!**

**P10: Što je try-with-resources i zašto se koristi?**

```java
// ❌ LOŠE - trebali bi zatvoriti BufferedReader:
BufferedReader br = new BufferedReader(new java.io.FileReader(INPUT_FILE));
String line;
while ((line = br.readLine()) != null) {
    // ...
}
br.close();  // Trebali bi zapamtiti!

// ✅ DOBRO - try-with-resources automatski zatvara:
try (BufferedReader br = new BufferedReader(new java.io.FileReader(INPUT_FILE))) {
    String line;
    while ((line = br.readLine()) != null) {
        // ...
    }
}  // ← Automatski se zatvara, čak i ako baci exception!
```

**Odgovor:**
```
Try-with-resources (Java 7+) automatski zatvara resurse 
koji implementiraju AutoCloseable interfejs.

Prednosti:
1. Automatski close() - ne trebate zapamtiti
2. Čak i ako se baci exception, resursi se zatvaraju
3. Čit je kod
4. Sprječava memory leakove

Sve što implementira AutoCloseable:
- BufferedReader
- BufferedWriter
- FileReader
- FileWriter
- Socket
- Database connections
- itd.
```

---

### **GRUPA 8: NULL CHECKS - 🔴 NOVO!**

**P11: Zašto ima toliko null checksa u vašem kodu?**

```java
// U FileReader.DataContainer:
if (validRecords == null) {
    throw new IllegalArgumentException("validRecords ne smije biti null");
}
if (errors == null) {
    throw new IllegalArgumentException("errors ne smije biti null");
}

// U App.java:
if (data == null) {
    System.err.println("Greška: DataContainer je null");
    return;
}
```

**Odgovor:**
```
Null checks sprječavaju NullPointerException greške kasnije.

Problem bez null checksa:

FileReader.DataContainer data = FileReader.readAndValidate();
// data je null? Tko zna...

List<PovratGradje> records = data.getValidRecords();  
// ❌ NullPointerException!

Rješenje sa null checksa:

if (data == null) {
    System.err.println("Greška");
    return;  // Sigurno rukovanje greškom
}
```

**Best practice:**
```
- Proverite na granicama (gdje dolaze podaci od van)
- Bacite exception što prije ako nešto nije OK
- Sprječite propagaciju null vrijednosti
```

---

### **GRUPA 9: STRING PARSING - 🔴 NOVO!**

**P12: Objasnite `line.split(";")` u LineFormatValidator**

```java
String line = "INV001;Monitor;Marko;5;1";
String[] parts = line.split(";");  // ← Što se dogodi?

// parts[0] = "INV001"
// parts[1] = "Monitor"
// parts[2] = "Marko"
// parts[3] = "5"
// parts[4] = "1"

if (parts.length != EXPECTED_FIELDS) {  // parts.length = 5
    GreskaZapisa greska = new GreskaZapisa(lineNumber, line, 
        "Očekivano " + EXPECTED_FIELDS + " polja, pronađeno " + parts.length);
}
```

**Odgovor:**
```
split(";") dijeli string na dijelove koristeći ";" kao delimiter.

Primjer:
"INV001;Monitor;Marko;5;1".split(";")
→ Array: ["INV001", "Monitor", "Marko", "5", "1"]

parts.length = 5

Ako ima drugačiji broj polja - greška!

Primjer greške:
"INV001;Monitor;Marko;5"  (nedostaje jedno polje)
→ split(";") → ["INV001", "Monitor", "Marko", "5"]
→ parts.length = 4
→ Bacamo grešku: "Očekivano 5 polja, pronađeno 4"
```

---

### **GRUPA 10: EXCEPTION HANDLING - 🔴 NOVO!**

**P13: Zašto se koriste `throws IOException` i `try-catch`?**

```java
// FileReader:
static DataContainer readAndValidate() throws IOException {  // ← throws!
    // ...
    if (!fileExists(INPUT_FILE)) {
        throw new IOException("Datoteka nije pronađena");  // ← throw!
    }
    
    try (BufferedReader br = ...) {
        // ...
    } catch (IOException e) {
        throw new IOException("Greška pri čitanju: " + e.getMessage(), e);  // ← wrap!
    }
}

// App:
try {
    FileReader.DataContainer data = FileReader.readAndValidate();
    // ...
} catch (IOException e) {  // ← catch!
    System.err.println("Greška pri čitanju podataka: " + e.getMessage());
    e.printStackTrace();
}
```

**Odgovor:**
```
1. throws - delegate-uje grešku na pozivajući kod
2. throw - baca grešku
3. try-catch - hvata i rukuje greškom

Pattern koji se koristi:
- FileReader baca IOException (delegira se)
- App hvata IOException (finalnog rukuje)

Razlozi:
- FileReader ne zna kako da rukuje greskom
- App zna - ispis na stderr, nastavak programa
- Slojevit exception handling
```

---

### **GRUPA 11: BUSINESS LOGIC - 🔴 NOVO!**

**P14: Objasnite logiku u `izracunajNaknadu()`**

```java
double izracunajNaknadu() {
    double naknada = 0.0;
    naknada += dani_kasnjenja * 0.50;      // 0.50 EUR po danu
    naknada += broj_ostecenja * 3.00;      // 3.00 EUR po oštećenju
    return naknada;
}

// Primjer:
// dani_kasnjenja = 10, broj_ostecenja = 2
// naknada = 10 * 0.50 + 2 * 3.00 = 5 + 6 = 11.00 EUR
```

**P15: Objasnite logiku u `odrediStatus()`**

```java
StatusGradje odrediStatus() {
    if (broj_ostecenja > 0) {           // Prioritet 1: Oštećenje
        return StatusGradje.OSTECENO;
    }
    if (dani_kasnjenja > 0) {           // Prioritet 2: Kašnjenje
        return StatusGradje.KASNJENJE;
    }
    return StatusGradje.UREDNO;         // Prioritet 3: Sve je OK
}
```

**P16: Objasnite logiku u `zahtijevaKontakt()`**

```java
boolean zahtijevaKontakt() {
    return dani_kasnjenja > 30 || broj_ostecenja >= 2;
    // True ako: više od 30 dana kašnjenja ILI 2+ oštećenja
}
```

---

### **GRUPA 12: COLLECTIONS - 🔴 NOVO!**

**P17: Zašto ste koristili `ArrayList` umjesto `LinkedList`?**

```java
List<PovratGradje> validRecords = new ArrayList<>();
List<GreskaZapisa> errors = new ArrayList<>();
```

**Odgovor:**
```
ArrayList je bolji za ovaj slučaj jer:

ArrayList:
- Brz pristup po indeksu: O(1)
- Sporije add/remove: O(n)
- Manji memory overhead

LinkedList:
- Spora pristup po indeksu: O(n)
- Brz add/remove: O(1)
- Veći memory overhead

U našem kodu:
1. Čitamo linije i dodajemo u listu (malo add operacija)
2. Iteriramo kroz listu za ispis (brz pristup trebao)
3. ArrayList je idealljen
```

---

### **GRUPA 13: FORMAT STRINGOVA - 🔴 NOVO!**

**P18: Što je `String.format("%.2f", naknada)`?**

```java
double naknada = 11.5;
String.format("%.2f", naknada)  // → "11.50"

double naknada2 = 11.555;
String.format("%.2f", naknada2)  // → "11.56" (zaokruženo)

// Korištenje u toString():
return ... + String.format("%.2f", izracunajNaknadu()) + " EUR";
```

**Odgovor:**
```
String.format() formira string prema formatu.

%.2f znači:
% - početak formata
.2 - dvije znamenke nakon točke
f - floating-point broj

Primjeri:
"%.2f" → Dvije decimale
"%.3f" → Tri decimale
"%d" → Cijeli broj (integer)
"%s" → String
"%05d" → Broj sa vodećim nulama (00123)
```

---

### **GRUPA 14: OVERRIDING - 🔴 NOVO!**

**P19: Zašto ste overrideali `toString()` metodu?**

```java
@Override
public String toString() {
    return inventarni_broj + " " + naslov + " - " + korisnik + " -> " + 
           odrediStatus() + " - " + String.format("%.2f", izracunajNaknadu()) + " EUR";
}
```

**Odgovor:**
```
toString() se koristi kada se objekt pretvori u string.

Bez override:
PovratGradje pg = new PovratGradje(...);
System.out.println(pg);  // → zad_1.PovratGradje@1a86f9f1 (beskorisno!)

Sa override:
System.out.println(pg);  // → INV001 Monitor - Marko -> UREDNO - 11.50 EUR

toString() se koristi:
- System.out.println(objekt)
- "String " + objekt
- U log porukama
- U debug informacijama
```

---

### **GRUPA 15: GETTERS - 🔴 NOVO!**

**P20: Trebate li sve te getters ili su nepotrebni?**

```java
String getInventarni_broj() { return inventarni_broj; }
String getNaslov() { return naslov; }
String getKorisnik() { return korisnik; }
int getDani_kasnjenja() { return dani_kasnjenja; }
int getBroj_ostecenja() { return broj_ostecenja; }
```

**Odgovor:**
```
Sve se koriste u ReportWriter:

pg.getInventarni_broj()   // ← Koristi se
pg.getNaslov()            // ← Koristi se
pg.getKorisnik()          // ← Koristi se
pg.odrediStatus()         // ← Koristi se
pg.izracunajNaknadu()     // ← Koristi se
pg.zahtijevaKontakt()     // ← Koristi se

Pa čak i ako se ne koriste trenutno - trebali bi biti jer:
1. Drugi kod može trebati pristup vrijednostima
2. Encapsulation - zaštita od direktnog pristupa
3. Fleksibilnost - ako trebate logiku u getteru kasnije
```

---

### **GRUPA 16: REGEX PITANJA - 🔴 NOVO!**

**P21: Trebate li regex za validaciju umjesto split()?**

```java
// Trenutno:
String[] parts = line.split(";");
if (parts.length != EXPECTED_FIELDS) { }

// Sa regex-om:
Pattern pattern = Pattern.compile("^([^;]+);([^;]+);([^;]+);(\\d+);(\\d+)$");
Matcher matcher = pattern.matcher(line);
if (matcher.matches()) { }
```

**Odgovor:**
```
Za ove podatke - NE, split() je dovoljno.

Regex bi trebao ako:
- Format je kompleksan (emails, telefonski brojevi)
- Trebali bi detaljni checks među tokenima
- Trebali bi special characters handling

U našem slučaju:
- Format je jednostavan (5 polja odvojena sa ";")
- split() je čit i razumljiv
- Dodatni checks su za svaki token posebno

split() je bolji jer je:
- Čitljiviji
- Brži
- Lakši za održavanje
```

---

### **GRUPA 17: PERFORMANCE - 🔴 NOVO!**

**P22: Kako bi optimizirali kod za veliku datoteku (1GB)?**

```java
// Trenutno:
List<PovratGradje> validRecords = new ArrayList<>();  // Sve u memoriji!

// Za veliku datoteku trebali bi:
// 1. Stream processing (Java 8 Streams)
// 2. Batch processing (svakih 1000 redaka)
// 3. Database umjesto ArrayList
// 4. Parallel processing
```

**Odgovor:**
```
Za 1GB datoteke trebali bi:

1. Java Streams:
Files.lines(Paths.get(INPUT_FILE))
    .map(LineFormatValidator::validate)
    .filter(ValidationResult::isValid)
    .forEach(result -> writeToDatabase(result));

2. Batch processing:
- Učitaj 1000 redaka
- Obrada
- Pisanje
- Ponavljaj

3. Database umjesto ArrayList

4. Parallel streams - više threadova

Trenutni kod je OK za male datoteke (< 100MB).
```

---

### **GRUPA 18: THREAD SAFETY - 🔴 NOVO!**

**P23: Je li vašу kod thread-safe?**

```java
// Nije thread-safe jer:
static class DataContainer {
    private List<PovratGradje> validRecords;  // Shared list
}

// Ako dva thread-a pristupaju istovremeno:
Thread1.add(pg1);
Thread2.add(pg2);
// ❌ Race condition! Mogućnost data corruption
```

**Odgovor:**
```
Nije, ali je OK jer:
1. Aplikacija je single-threaded (App.main)
2. Sve je sekvencijalno - čitaj, obrada, pisanje
3. Nema konkurentnog pristupa

Ako bi trebao multi-threading:
List<PovratGradje> validRecords = Collections.synchronizedList(new ArrayList<>());
ili
List<PovratGradje> validRecords = new CopyOnWriteArrayList<>();
```

---

### **GRUPA 19: TESTING - 🔴 NOVO!**

**P24: Kako biste testirali ovaj kod?**

```java
@Test
void testValidLine() {
    String line = "INV001;Monitor;Marko;5;1";
    LineFormatValidator.ValidationResult result = 
        LineFormatValidator.validate(line, 1);
    
    assertTrue(result.isValid());
    assertEquals(result.getPovratGradje().getInventarni_broj(), "INV001");
}

@Test
void testInvalidLine() {
    String line = "INV001;Monitor;Marko";  // Nedostaju polja
    LineFormatValidator.ValidationResult result = 
        LineFormatValidator.validate(line, 1);
    
    assertFalse(result.isValid());
    assertNotNull(result.getGreska());
}

@Test
void testCalculateRefund() {
    PovratGradje pg = new PovratGradje("INV001", "Monitor", "Marko", 10, 2);
    double expected = 10 * 0.50 + 2 * 3.00;  // 11.00
    assertEquals(pg.izracunajNaknadu(), expected);
}
```

---

### **GRUPA 20: ALTERNATIVNI DIZAJN - 🔴 NOVO!**

**P25: Trebate li validation exception umjesto GreskaZapisa?**

```java
// Trenutno - vraća GreskaZapisa:
LineFormatValidator.ValidationResult result = 
    LineFormatValidator.validate(line, lineNumber);

if (!result.isValid()) {
    errors.add(result.getGreska());
}

// Alternativa - baca exception:
try {
    PovratGradje pg = LineFormatValidator.validate(line);
} catch (ValidationException e) {
    errors.add(new GreskaZapisa(...));
}
```

**Odgovor:**
```
Vaš pristup je bolji jer:
1. Validacijske greške NISU iznimne (exception) situacije
2. Očekuje se da će neke linije biti nevaljane
3. Exception se trebaju bacati za neočekivane situacije

Trenutni pristup:
- GreskaZapisa je očekivani rezultat validacije
- Lakše je rukovanje (ako válido ili nije)
- Lakše je testiranje
- Exception se koriste samo za prrave greške (I/O, null, itd)
```

---

## **SAŽETAK - Što memorirati:**

```
✅ SIGURNO ĆE PITATI:
1. Arhitektura (SRP)
2. ENUM
3. final modifier
4. Immutability
5. Try-with-resources

🟡 VRLO VJEROVATNO ĆE PITATI:
6. Private constructor
7. Inner classes (DataContainer)
8. Defensive copying
9. Null checks
10. String parsing (split)

🟢 MOGU PITATI:
11. Exception handling
12. Collections (ArrayList vs LinkedList)
13. toString() override
14. Business logic (izracunajNaknadu, zahtijevaKontakt)
15. Testing (unit tests)
```

**Pripremi se za pitanja u grupi ✅ i 🟡 - oni su najčešće!** 📚
