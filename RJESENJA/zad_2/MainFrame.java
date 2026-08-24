package zad_2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private ArrayList<AktivnostUcenja> aktivnosti;
    private FormPanel formPanel;
    private ViewPanel viewPanel;

    public MainFrame() {
        setTitle("Plan Učenja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        aktivnosti = new ArrayList<>();

        // Kreiranja panela
        formPanel = new FormPanel();
        viewPanel = new ViewPanel();

        // Postavljanje layouta
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));
        contentPane.add(formPanel);
        contentPane.add(viewPanel);

        // Postavljanje listenera
        formPanel.getDodajButton().addActionListener(new FormPanelListener(formPanel, this));
        viewPanel.getSpremiButton().addActionListener(new SpremiListener(this));
        viewPanel.getUcitajButton().addActionListener(new UcitajListener(this));

        // Početni prikaz
        osvjeziPrikaz();
    }

    public void dodajAktivnost(AktivnostUcenja aktivnost) {
        aktivnosti.add(aktivnost);
        osvjeziPrikaz();
    }

    public void setAktivnosti(ArrayList<AktivnostUcenja> nove) {
        aktivnosti.clear();
        aktivnosti.addAll(nove);
        osvjeziPrikaz();
    }

    public ArrayList<AktivnostUcenja> getAktivnosti() {
        return aktivnosti;
    }

    private void osvjeziPrikaz() {
        viewPanel.osvjeziPrikaz(aktivnosti);
    }
}
