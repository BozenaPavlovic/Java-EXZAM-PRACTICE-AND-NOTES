package zad_2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPanelListener implements ActionListener {
    private FormPanel formPanel;
    private MainFrame mainFrame;

    public FormPanelListener(FormPanel formPanel, MainFrame mainFrame) {
        this.formPanel = formPanel;
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String naziv = formPanel.getNaziv();
        String tema = formPanel.getTema();
        TipAktivnosti tip = formPanel.getTip();
        String trajanjeStr = formPanel.getTrajanje();
        String napomena = formPanel.getNapomena();

        // Validacija
        if (naziv.isEmpty() || tema.isEmpty() || trajanjeStr.isEmpty()) {
            formPanel.prikaziGresku("Naziv, tema i trajanje ne smiju biti prazni!");
            return;
        }

        int trajanje;
        try {
            trajanje = Integer.parseInt(trajanjeStr);
        } catch (NumberFormatException ex) {
            formPanel.prikaziGresku("Trajanje mora biti pozitivan cijeli broj!");
            return;
        }

        if (trajanje <= 0) {
            formPanel.prikaziGresku("Trajanje mora biti pozitivan broj!");
            return;
        }

        // Stvaranje aktivnosti
        AktivnostUcenja aktivnost = new AktivnostUcenja(naziv, tema, tip, trajanje, napomena);

        // Dodavanje u MainFrame
        mainFrame.dodajAktivnost(aktivnost);

        // Očišćenje polja
        formPanel.ocistiPolja();
    }
}
