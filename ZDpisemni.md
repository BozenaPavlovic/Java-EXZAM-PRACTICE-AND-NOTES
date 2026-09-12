
# Java OOP — usmeni: zadaci + rješenja

## 1. Klasa i objekt

### Tipičan zadatak

> Napiši klasu `Student` koja ima ime i godinu studija. Kreiraj objekt klase.

### Rješenje

```java
class Student {
    String name;
    int year;
}
```

```java
Student s = new Student();

s.name = "Ana";
s.year = 2;
```

### Što je što?

```text
class Student     → definicija klase
Student s         → referenca na objekt
new Student()     → stvaranje objekta
s.name            → atribut objekta
```

### Profesor može pitati

**Što je klasa?**
Predložak prema kojem se stvaraju objekti.

**Što je objekt?**
Konkretna instanca klase.

**Što radi `new`?**
Stvara novi objekt.

---

# 2. Atributi, metode i konstruktori

### Zadatak

> Napiši klasu `Student` s privatnim imenom i godinom te konstruktor koji ih postavlja.

### Rješenje

```java
class Student {
    private String name;
    private int year;

    public Student(String name, int year) {
        this.name = name;
        this.year = year;
    }
}
```

Kreiranje:

```java
Student s = new Student("Ana", 2);
```

### Važno

```java
this.name = name;
```

Lijevo:

```text
this.name
```

→ atribut trenutnog objekta.

Desno:

```text
name
```

→ parametar konstruktora.

---

# 3. Metode

### Zadatak

> Dodaj metodu koja vraća ime studenta.

### Rješenje

```java
class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

Poziv:

```java
Student s = new Student("Ana");

String x = s.getName();
```

---

# 4. Učahurivanje / enkapsulacija

### Zadatak

> Napravi privatni atribut `age` kojemu se vrijednost može dohvatiti i promijeniti pomoću metoda.

### Rješenje

```java
class Person {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

Ne želimo:

```java
person.age = 20;
```

jer je `age`:

```java
private
```

nego:

```java
person.setAge(20);
```

### Profesor može pitati

**Zašto `private`?**

Da se atributu ne pristupa direktno izvana, nego kontrolirano preko metoda.

---

# 5. Preopterećenje metoda — overload

### Zadatak

> Napiši dvije metode `add`: jednu koja prima dva broja i jednu koja prima tri broja.

### Rješenje

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

Pozivi:

```java
add(2, 3);
add(2, 3, 4);
```

### Zapamti

**Overload = isto ime, različiti parametri.**

Ne može samo povratni tip biti drugačiji.

---

# 6. Preopterećenje konstruktora

### Zadatak

> Napravi tri konstruktora klase `Student`.

### Rješenje

```java
class Student {
    String name;
    int year;

    Student() {
        name = "Unknown";
        year = 1;
    }

    Student(String name) {
        this.name = name;
        year = 1;
    }

    Student(String name, int year) {
        this.name = name;
        this.year = year;
    }
}
```

Sada su mogući:

```java
Student a = new Student();
Student b = new Student("Ana");
Student c = new Student("Ana", 2);
```

---

# 7. Nasljeđivanje — `extends`

### Zadatak

> Napravi klasu `Animal` s metodom `eat()`. Napravi `Dog` koja nasljeđuje `Animal`.

### Rješenje

```java
class Animal {

    void eat() {
        System.out.println("Eating");
    }
}
```

```java
class Dog extends Animal {

    void bark() {
        System.out.println("Barking");
    }
}
```

Možeš:

```java
Dog d = new Dog();

d.eat();
d.bark();
```

Jer `Dog` nasljeđuje `eat()` iz `Animal`.

### Zapamti

```text
class Dog extends Animal
```

= **Dog je Animal**.

---

# 8. Override

### Zadatak

> `Animal` ima metodu `sound()`. Neka `Dog` ima svoju verziju te metode.

### Rješenje

```java
class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}
```

```java
class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Woof");
    }
}
```

`Dog` je **nadjačao** (`override`) metodu iz `Animal`.

---

# 9. Polimorfizam

### Zadatak

> Pokaži polimorfizam koristeći `Animal` i `Dog`.

### Rješenje

```java
Animal a = new Dog();

a.sound();
```

Iako je tip reference:

```text
Animal
```

stvarni objekt je:

```text
Dog
```

zato se izvršava:

```java
Dog.sound()
```

### Mentalna slika

```text
Animal a
   |
   ↓
 Dog objekt
```

Ovo je **polimorfizam**.

---

# 10. `super`

### Zadatak

> `Animal` ima konstruktor koji prima ime. `Dog` ga treba pozvati.

### Rješenje

```java
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }
}
```

```java
class Dog extends Animal {

    Dog(String name) {
        super(name);
    }
}
```

`super(name)` poziva konstruktor nadklase.

---

# 11. `static`

### Zadatak

> Napravi brojač koji broji koliko je objekata klase `Student` stvoreno.

### Rješenje

```java
class Student {

    static int count = 0;

    Student() {
        count++;
    }
}
```

```java
Student a = new Student();
Student b = new Student();
Student c = new Student();

System.out.println(Student.count);
```

Rezultat:

```text
3
```

### Zašto?

`static` pripada **klasi**, a ne pojedinom objektu.

---

# 12. `final`

### Zadatak

> Napravi konstantu `MAX` vrijednosti 100.

### Rješenje

```java
class Test {
    static final int MAX = 100;
}
```

Ne može:

```java
MAX = 200;
```

jer je `final`.

### `final` može biti i:

```java
final class A {
}
```

→ ne može se naslijediti.

```java
final void test() {
}
```

→ metoda se ne može overrideati.

---

# 13. Apstraktna klasa

### Zadatak

> Napravi apstraktnu klasu `Shape` s apstraktnom metodom `area()`. Napravi `Circle` koji je nasljeđuje.

### Rješenje

```java
abstract class Shape {

    abstract double area();
}
```

```java
class Circle extends Shape {

    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return radius * radius * Math.PI;
    }
}
```

Ne možeš:

```java
Shape s = new Shape();
```

jer je `Shape` apstraktna klasa.

Ali možeš:

```java
Shape s = new Circle(5);
```

### KLJUČNO

```text
abstract class
       ↓
    extends
```

---

# 14. Sučelje — `interface`

### Zadatak

> Napiši sučelje `ZeroSetter` koje ima metodu `setZero(int n)`.

### Rješenje

```java
interface ZeroSetter {
    void setZero(int n);
}
```

Klasa:

```java
class MyArray implements ZeroSetter {

    private int[] array;

    public MyArray(int[] array) {
        this.array = array;
    }

    @Override
    public void setZero(int n) {
        array[n] = 0;
    }
}
```

Ako imamo:

```text
array = [5, 8, 3, 9]
N = 4
n = 2
```

onda:

```java
array[n] = 0;
```

daje:

```text
[5, 8, 0, 9]
```

### Ovo moraš znati bez razmišljanja

```text
N = broj elemenata
n = indeks
```

Ako:

```text
N = 4
```

indeksi su:

```text
0  1  2  3
```

### I najvažnije:

```text
APSTRAKTNA KLASA
abstract class
      ↓
   extends

SUČELJE
interface
      ↓
  implements
```

---

# 15. `ArrayList`

### Zadatak

> Napravi listu imena i dodaj tri imena.

### Rješenje

```java
ArrayList<String> names = new ArrayList<>();

names.add("Ana");
names.add("Iva");
names.add("Marko");
```

Dohvat:

```java
String name = names.get(0);
```

Brisanje:

```java
names.remove(1);
```

Veličina:

```java
int n = names.size();
```

### Razlika

Array:

```java
array.length
```

ArrayList:

```java
list.size()
```

---

# 16. `HashMap`

### Zadatak

> Napravi mapu koja ime studenta povezuje s ocjenom.

### Rješenje

```java
HashMap<String, Integer> grades = new HashMap<>();

grades.put("Ana", 5);
grades.put("Iva", 4);
grades.put("Marko", 3);
```

Dohvat:

```java
int grade = grades.get("Ana");
```

Provjera:

```java
grades.containsKey("Ana");
```

Brisanje:

```java
grades.remove("Ana");
```

Mentalno:

```text
"Ana"   → 5
"Iva"   → 4
"Marko" → 3
```

---

# 17. Generics

### Zadatak

> Napravi generičku klasu `Box` koja može sadržavati bilo koji tip podatka.

### Rješenje

```java
class Box<T> {

    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
```

Korištenje:

```java
Box<String> b1 = new Box<>("Hello");
Box<Integer> b2 = new Box<>(10);
```

`T` se određuje prilikom korištenja klase.

---

# 18. Generička metoda

### Zadatak

> Napiši generičku metodu koja vraća prvi element niza.

### Rješenje

```java
static <T> T first(T[] array) {
    return array[0];
}
```

Korištenje:

```java
String[] names = {"Ana", "Iva"};

String name = first(names);
```

---

# 19. `try` / `catch`

### Zadatak

> Obradi iznimku koja nastaje dijeljenjem s nulom.

### Rješenje

```java
try {
    int x = 10 / 0;
}
catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
```

Struktura:

```text
try
 ↓
kod koji može izazvati iznimku

catch
 ↓
obrada iznimke
```

---

# 20. `throw`

### Zadatak

> Ako je dob korisnika negativna, baci `IllegalArgumentException`.

### Rješenje

```java
if (age < 0) {
    throw new IllegalArgumentException("Invalid age");
}
```

`throw` znači:

> **sada bacam iznimku.**

---

# 21. `throws`

### Zadatak

> Napiši metodu koja može baciti `IOException`.

### Rješenje

```java
void readFile() throws IOException {
    // ...
}
```

Razlika:

```text
throw
→ baca konkretnu iznimku

throws
→ deklarira da metoda može baciti iznimku
```

---

# 22. Pisanje u datoteku

### Zadatak

> Zapiši `"Hello"` u datoteku `test.txt`.

### Rješenje

```java
try {
    FileWriter writer = new FileWriter("test.txt");

    writer.write("Hello");

    writer.close();
}
catch (IOException e) {
    System.out.println("Error");
}
```

---

# 23. Čitanje datoteke

### Zadatak

> Pročitaj sve retke iz datoteke i ispiši ih.

### Rješenje

```java
try {
    Scanner scanner = new Scanner(new File("test.txt"));

    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.println(line);
    }

    scanner.close();
}
catch (FileNotFoundException e) {
    System.out.println("File not found");
}
```

---

# 24. Serijalizacija

### Zadatak

> Omogući da se objekt `Student` može serijalizirati.

### Rješenje

```java
class Student implements Serializable {

    private String name;
    private int year;
}
```

Spremanje objekta:

```java
ObjectOutputStream out =
    new ObjectOutputStream(
        new FileOutputStream("student.dat")
    );

out.writeObject(student);

out.close();
```

Čitanje:

```java
ObjectInputStream in =
    new ObjectInputStream(
        new FileInputStream("student.dat")
    );

Student student = (Student) in.readObject();

in.close();
```

Najvažnije za prepoznati:

```java
implements Serializable
```

---

# 25. Unutarnja klasa

### Zadatak

> Napravi klasu `Outer` koja sadrži unutarnju klasu `Inner`.

### Rješenje

```java
class Outer {

    private int x = 10;

    class Inner {

        void print() {
            System.out.println(x);
        }
    }
}
```

`Inner` je definirana **unutar `Outer`**.

---

# 26. Anonimna klasa

### Zadatak

> Napravi objekt sučelja `Animal` bez stvaranja imenovane klase.

### Rješenje

```java
interface Animal {
    void sound();
}
```

```java
Animal a = new Animal() {

    @Override
    public void sound() {
        System.out.println("Woof");
    }
};
```

Ovdje ne postoji:

```java
class Dog
```

nego se implementacija piše direktno prilikom stvaranja objekta.

---

# 27. Swing — prozor

### Zadatak

> Napravi prozor veličine 400 × 300 s gumbom "Click".

### Rješenje

```java
JFrame frame = new JFrame("My Window");

JButton button = new JButton("Click");

frame.add(button);

frame.setSize(400, 300);

frame.setDefaultCloseOperation(
    JFrame.EXIT_ON_CLOSE
);

frame.setVisible(true);
```

---

# 28. Swing — događaj

### Zadatak

> Kada korisnik klikne gumb, ispiši `"Clicked"`.

### Rješenje s anonimnom klasom

```java
button.addActionListener(
    new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked");
        }
    }
);
```

Logika:

```text
korisnik klikne
      ↓
   događaj
      ↓
 ActionListener
      ↓
actionPerformed()
```

Ako profesor dopušta lambde, može kraće:

```java
button.addActionListener(e -> {
    System.out.println("Clicked");
});
```

---

# 29. MVC

### Zadatak

> Napravi jednostavan model koji čuva broj i može ga povećati.

### Rješenje — Model

```java
class CounterModel {

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
```

Model je odgovoran za **podatke i logiku podataka**.

```text
MODEL
→ podaci
→ poslovna/logička pravila
```

View:

```text
→ prikazuje podatke
```

Controller:

```text
→ reagira na korisnikove akcije
```

---

# 30. UML → Java

### UML

```text
-------------------------
Student
-------------------------
- name : String
- year : int
-------------------------
+ Student(name, year)
+ getName() : String
+ setName(name) : void
-------------------------
```

### Rješenje

```java
class Student {

    private String name;
    private int year;

    public Student(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### UML oznake

```text
-  → private
+  → public
#  → protected
```

---

# 31. JavaDoc

### Zadatak

> Dokumentiraj metodu `setZero`.

### Rješenje

```java
/**
 * Sets the element at the given index to zero.
 *
 * @param n index of the element
 */
public void setZero(int n) {
    array[n] = 0;
}
```

Najvažnije:

```text
@param  → parametar
@return → povratna vrijednost
@throws → moguća iznimka
```

---

# 32. Paketi i import

### Zadatak

> Napravi klasu `Student` u paketu `model`.

### Rješenje

```java
package model;

public class Student {
}
```

Drugi paket:

```java
import model.Student;
```

---

# 33. Najvažnije kombinacije koje profesor može dati

Ovo bih ti **posebno učio**, jer jedan mali zadatak može kombinirati 4–5 tema.

### Kombinacija A — klasa + enkapsulacija + konstruktor

```java
class Student {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

---

### Kombinacija B — nasljeđivanje + override + polimorfizam

```java
class Animal {

    void sound() {
        System.out.println("Animal");
    }
}
```

```java
class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Woof");
    }
}
```

```java
Animal a = new Dog();
a.sound();
```

---

### Kombinacija C — interface + array

```java
interface ZeroSetter {
    void setZero(int n);
}
```

```java
class MyArray implements ZeroSetter {

    private int[] array;

    public MyArray(int[] array) {
        this.array = array;
    }

    @Override
    public void setZero(int n) {
        array[n] = 0;
    }
}
```

**Ovo je praktički tvoj konkretni tip pitanja.**

---

### Kombinacija D — abstract class + inheritance

```java
abstract class Shape {
    abstract double area();
}
```

```java
class Circle extends Shape {

    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return radius * radius * Math.PI;
    }
}
```

---

### Kombinacija E — Generic + ArrayList

```java
class Storage<T> {

    private ArrayList<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public T get(int index) {
        return items.get(index);
    }
}
```

---

Kad vidiš riječ u zadatku, odmah prevedi:

```text
"klasa"                  → class
"objekt"                 → new
"nasljeđuje"             → extends
"implementira sučelje"   → implements
"apstraktna klasa"       → abstract class
"sučelje"                → interface
"nadjačava"              → @Override
"zajedničko svim objektima" → static
"ne može se mijenjati"  → final
"privatni atribut"       → private
"generički tip"          → <T>
"obradi iznimku"         → try/catch
"baci iznimku"           → throw
"može baciti iznimku"    → throws
"lista"                  → ArrayList
"mapa ključ-vrijednost"  → HashMap
"slušaj klik"            → ActionListener
```


**To je upravo razlika na kojoj si se zeznula na prošlom usmenom.** Sad imaš konkretan kod za oba slučaja, pa ih možeš vježbati jedan pokraj drugoga dok ti razlika ne postane automatska.
