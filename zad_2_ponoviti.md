Pogledajmo sve relevantne datoteke:

Perfect! Sada ću vam detaljno objasniti MVC arhitekturu u zad_2:

---

# **MVC ARHITEKTURA - Zad_2**

## **1. Što je MVC?**

**MVC = Model-View-Controller**

Arhitektura koja dijeli aplikaciju na tri dijela:

```
┌──────────────────────────────────────────────────────┐
│                                                      │
│  ┌────────────┐    ┌────────────┐    ┌────────────┐ │
│  │   MODEL    │    │   VIEW     │    │ CONTROLLER │ │
│  │            │    │            │    │            │ │
│  │ Podatci    │◄──►│ Prikaz     │◄──►│ Logika     │ │
│  │ Logika     │    │ UI         │    │ Akcije     │ │
│  │ Stanje     │    │            │    │            │ │
│  └────────────┘    └────────────┘    └────────────┘ │
│        ▲                                     ▲       │
│        └─────────────────────────────────────┘       │
│           Komunikacija kroz Event Listeners          │
└──────────────────────────────────────────────────────┘
```

---

## **2. Gdje su M, V, C u vašem kodu?**

### **MODEL - `TerminRadionice.java`**

```java
final class TerminRadionice {  // ← MODEL
    private final String naziv;
    private final String voditelj;
    private final TipTermina tipTermina;
    private final int brojMjesta;
    private final String napomena;
    
    // Samo podatci - NEMA GUI koda!
    // Nema JButton, JTextField, itd.
    // Može biti korišten bilo gdje (web, API, CLI)
}
```

**Uloga:**
- Predstavlja "Termin radionice"
- Čuva podatke
- Immutable (ne može se mijenjati)
- Nezavisan od GUI-ja

**Primjer:**
```java
TerminRadionice termin = new TerminRadionice(
    "Java Programmiranje",
    "Marko Marković",
    TipTermina.RADIONICA,
    20,
    "Donesi laptop"
);
```

---

### **VIEW - `FormPanel.java` i `ViewPanel.java`**

#### **FormPanel - INPUT VIEW**

```java
final class FormPanel extends JPanel {  // ← VIEW (INPUT)
    private JTextField txtNaziv;        // ← GUI komponente
    private JTextField txtVoditelj;
    private JComboBox<TipTermina> cbTipTermina;
    private JButton btnDodaj;
    private JButton btnSpremi;
    
    // SAMO prikaz - NEMA poslovne logike!
}
```

**Uloga:**
- Prikazuje formu korisnika
- Prikuplja input
- Emitira eventu kroz listener

**NE ZNA:**
- Gdje se spremeaju termini
- Kako se validiraju
- Što se dogodi nakon klika na gumb

---

#### **ViewPanel - OUTPUT VIEW**

```java
final class ViewPanel extends JPanel {  // ← VIEW (OUTPUT)
    private JTextArea txtArea;          // ← GUI komponenta
    
    void refreshDisplay(List<TerminRadionice> termini) {
        // Samo prikazuje podatke koji se proslijeđuju
        txtArea.setText(...);
    }
}
```

**Uloga:**
- Prikazuje popis termina
- Nije znati gdje dolaze podaci
- Samo prikazuje što joj se da

---

### **CONTROLLER - `MainFrame.java`**

```java
final class MainFrame extends JFrame implements FormPanelListener {  // ← CONTROLLER
    private List<TerminRadionice> termini;  // ← MODEL (u memoriji)
    private FormPanel formPanel;            // ← VIEW
    private ViewPanel viewPanel;            // ← VIEW
    
    @Override
    public void onTerminAdded(FormPanelEvent event) {
        // LOGIKA - kreira model iz event-a
        TerminRadionice termin = new TerminRadionice(
            event.getNaziv(),           // Uzima iz VIEW
            event.getVoditelj(),
            event.getTipTermina(),
            event.getBrojMjesta(),
            event.getNapomena()
        );
        
        termini.add(termin);            // Sprema u MODEL
        viewPanel.refreshDisplay(termini);  // Osvježava VIEW
    }
}
```

**Uloga:**
- Rukuje akcijama korisnika
- Poziva VIEW da prikazuje
- Čuva podatke u MODEL
- Rukuje primanja iz VIEW-a i slanja u MODEL

---

## **3. Tok Podataka - MVC Komunikacija**

### **Scenarij: Korisnik dodaje novi termin**

```
1. KORISNIK AKCIJA
   └─ Klikne "Dodaj termin" u FormPanel-u
   
2. VIEW PRIKUPLJA
   └─ FormPanel.handleAddTermin() čita tekstualne polje
   
3. VIEW EMITIRA EVENT (preko Listener-a)
   └─ listener.onTerminAdded(event)
   
4. CONTROLLER PRIMA
   └─ MainFrame.onTerminAdded(event)
      └─ Ekstrahira podatke iz event-a
      └─ Kreira TerminRadionice objekt (MODEL)
      └─ Dodaje u kolekciju (MODEL)
      
5. CONTROLLER OSVJEŽAVA VIEW
   └─ viewPanel.refreshDisplay(termini)
   
6. VIEW PRIKAZUJE
   └─ ViewPanel prikazuje sve termine u tekstualnom polju
```

**Dijagram:**

```
USER CLICKS "Dodaj termin"
        ↓
FormPanel (VIEW)
        ↓
emit FormPanelEvent
        ↓
MainFrame.onTerminAdded() (CONTROLLER)
        ↓
Create TerminRadionice (MODEL)
        ↓
Add to termini list (MODEL)
        ↓
viewPanel.refreshDisplay(termini) (VIEW)
        ↓
USER VIDI nova listu
```

---

## **4. Specifične Komponente**

### **FormPanelListener - Event Handler Interface**

```java
interface FormPanelListener {  // ← Komunikacijski most View-Controller
    void onTerminAdded(FormPanelEvent event);
    void onSaveTermini();
    void onLoadTermini();
}
```

**Razlog za interfejs:**
- FormPanel ne zna za MainFrame
- FormPanel ne zna za logiku
- FormPanel samo emitira "Nešto se dogodilo"
- MainFrame sluša i rukuje

```java
// FormPanel.setupListeners():
btnDodaj.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        handleAddTermin();
        if (listener != null) {
            listener.onTerminAdded(event);  // ← Emitira event
        }
    }
});

// MainFrame:
formPanel.setListener(this);  // ← MainFrame je listener
```

---

### **FormPanelEvent - Event Data Transfer**

```java
// FormPanelEvent.java
class FormPanelEvent {
    private String naziv;
    private String voditelj;
    private TipTermina tipTermina;
    // itd.
    
    // Getters...
}

// Korištenje:
FormPanelEvent event = new FormPanelEvent(naziv, voditelj, tip, broj, napomena);
listener.onTerminAdded(event);
```

**Razlog:**
- Prenosi podatke iz FormPanel-a
- Strukturiran tip umjesto pojedinačnih parametara
- Lakše za proširenje

---

## **5. Nepreklapajuće Odgovornosti**

| Komponenta | TREBATE raditi | NE TREBATE raditi |
|-----------|---|---|
| **MODEL (TerminRadionice)** | Čuvati podatke | Znati za GUI |
| | | Znati kako se učitava |
| | | Znati kako se prikazuje |
| **VIEW (FormPanel)** | Prikazati formu | Poslovnu logiku |
| | Prikupiti input | Čuvati podatke |
| | Emitirati eventu | Odlučiti što učiniti |
| **VIEW (ViewPanel)** | Prikazati listu | Zašto se lista promijenila |
| | Osvježiti prikaz | Kako se kreira termin |
| **CONTROLLER (MainFrame)** | Rukovati akcijama | Crtati GUI |
| | Ažurirati MODEL | Znati detaljno kako VIEW radi |
| | Osvježavati VIEW | Direktno pristupati polima |

---

## **6. Primjena Principa**

### **Loose Coupling - Slabo Vezane Komponente**

```java
// FormPanel ne zna tko ga sluša
final class FormPanel extends JPanel {
    private FormPanelListener listener;  // ← Zna samo interfejs
    
    // NE zna:
    // - Tko je listener
    // - MainFrame, App, Test klase
    // - Što će se dogoditi nakon onTerminAdded
}

// MainFrame može biti bilo što što implementira interfejs
class MainFrame implements FormPanelListener {
    @Override
    public void onTerminAdded(FormPanelEvent event) {
        // Moja logika
    }
}

// Čak i Test klasa može biti listener!
class TestListener implements FormPanelListener {
    @Override
    public void onTerminAdded(FormPanelEvent event) {
        // Test logika
    }
}
```

**Prednost:**
- FormPanel se ne mijenja ako se MainFrame mijenja
- Lako je dodati nove VIEW-e
- Lako je testirati

---

### **High Cohesion - Snažne Jedinice**

```java
// MainFrame je jedino mjesto gdje se skupljaju sve tri komponente
final class MainFrame extends JFrame implements FormPanelListener {
    private List<TerminRadionice> termini;  // ← MODEL
    private FormPanel formPanel;            // ← VIEW
    private ViewPanel viewPanel;            // ← VIEW
    
    // Sve zajedno koordinirane
}
```

---

## **7. Tok Spreman → Učitaj**

### **Spremi**

```
User klikne "Spremi u TXT"
    ↓
FormPanel emitira event
    ↓
MainFrame.onSaveTermini()
    ↓
AUX_RW.saveTerminiToFile(termini)  // Sprema MODEL u datoteku
    ↓
JOptionPane prikazuje poruku
```

### **Učitaj**

```
User klikne "Učitaj TXT"
    ↓
FormPanel emitira event
    ↓
MainFrame.onLoadTermini()
    ↓
AUX_RW.loadTerminiFromFile()  // Čita iz datoteke
    ↓
Ažurira termini list (MODEL)
    ↓
viewPanel.refreshDisplay(termini)  // Osvježava VIEW
    ↓
Prikazuje sve učitane termine
```

---

## **8. Očekivana Pitanja Profesora**

### **P1: Objasnite MVC arhitekturu u zadatku 2**

**Odgovor:**
```
Model je TerminRadionice - čuva podatke o terminu.

View su FormPanel (za unos) i ViewPanel (za prikaz) 
- samo za GUI, bez poslovne logike.

Controller je MainFrame - rukuje akcijama korisnika, 
ažurira model i osvježava view.

Komunikacija je kroz FormPanelListener interfejs - 
FormPanel emitira eventu, MainFrame ju hvata i rukuje.
```

---

### **P2: Zašto ste koristili Listener interfejs?**

**Odgovor:**
```
FormPanel ne trebate znati tko ga sluša. 
Korištenjem interfeja, FormPanel je nezavisan.

Ako trebam drugačiji controller, samo trebam 
implementirati FormPanelListener. FormPanel se 
ne mijenja - loose coupling.
```

---

### **P3: Što je Model u vašem kodu?**

**Odgovor:**
```
Model je List<TerminRadionice> u MainFrame-u. 
TerminRadionice je entitet koji predstavlja termin.

Isto bi bilo i List<String> ili File - 
gdje god se čuvaju podaci.
```

---

### **P4: Gdje se poslovana logika?**

**Odgovor:**
```
U MainFrame (controlleru) - obrada FormPanelEvent-a, 
kreira TerminRadionice objekt, osvježava VIEW.

Nikada u FormPanel-u ili ViewPanel-u jer su to samo 
prikazi - View ne trebate znati poslovnu logiku.
```

---

### **P5: Što bi se dogodilo da nema MVC?**

**Odgovor:**
```
Sve bi bilo u jednoj klasi:

class BigMess extends JFrame {
    JTextField txt;
    JTextArea area;
    JButton btn;
    List<TerminRadionice> termini;
    
    void handleClick() {
        // Validacija
        // Kreiranje objekta
        // Osvježavanje prikaza
        // Pisanje u datoteku
        // Sve zajedno!
    }
}

Problemi:
- Teško za održavanje
- Teško za testiranje
- Teško za dodavanje novih VIEW-a
- Sve je međusobno vezano
```

---

## **9. Diagram - Cijeli Tok**

```
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│                    APLIKACIJA (App.main)                    │
│                           ↓                                  │
│                   MainFrame (CONTROLLER)                    │
│           ┌──────────────────┬──────────────────┐           │
│           │                  │                  │           │
│      FormPanel          ViewPanel          TerminRadionice  │
│      (VIEW)             (VIEW)             (MODEL)          │
│       │                  │                  │              │
│   [Input Form]       [Display Area]      [Data Object]      │
│       │                  │                  │              │
│   User Types        Prikazuje             Čuva             │
│   User Clicks       listu                 podatke           │
│       │                  │                  │              │
│       └──────► Event ─────────────────────►│               │
│         Listener           Controller       │               │
│            │               Updates          │               │
│            └◄─────────────────────────────┘                │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

---

## **Zaključak - MVC u Vašem Kodu**

✅ **Model** = `TerminRadionice` + `List<TerminRadionice>`  
✅ **View** = `FormPanel` + `ViewPanel`  
✅ **Controller** = `MainFrame`  
✅ **Event System** = `FormPanelListener` + `FormPanelEvent`  

**Prednosti:**
- 🔀 Loose coupling - komponente su neovisne
- 🔄 Reusable - View-e se mogu ponovno koristiti
- 🧪 Testable - svaka komponenta se testira odvojeno
- 📖 Maintainable - jasna struktura i odgovornosti

Odličan primjer MVC dizajna! 💪
