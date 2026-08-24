package SwingGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormPanel extends JPanel {
    private JTextField tfNaziv;
    private JTextField tfTema;
    private JComboBox<TipAktivnosti> cbTip;
    private JTextField tfTrajanje;
    private JTextArea taNapomena;
    private List<AktivnostUcenja> aktivnosti;
    private JTextArea taPrikaz;

    public FormPanel(List<AktivnostUcenja> aktivnosti, JTextArea taPrikaz) {
        this.aktivnosti = aktivnosti;
        this.taPrikaz = taPrikaz;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Unos nove aktivnosti"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        JLabel lbNaziv = new JLabel("Naziv aktivnosti:*");
        lbNaziv.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lbNaziv, gbc);

        tfNaziv = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        add(tfNaziv, gbc);
        row++;

        JLabel lbTema = new JLabel("Tema / Kolegij:*");
        lbTema.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lbTema, gbc);

        tfTema = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row;
        add(tfTema, gbc);
        row++;

        JLabel lbTip = new JLabel("Tip aktivnosti:");
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lbTip, gbc);

        cbTip = new JComboBox<>(TipAktivnosti.values());
        gbc.gridx = 1;
        gbc.gridy = row;
        add(cbTip, gbc);
        row++;

        JLabel lbTrajanje = new JLabel("Trajanje (min):*");
        lbTrajanje.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lbTrajanje, gbc);

        tfTrajanje = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = row;
        add(tfTrajanje, gbc);
        row++;

        JLabel lbNapomena = new JLabel("Napomena:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridheight = 2;
        add(lbNapomena, gbc);

        taNapomena = new JTextArea(3, 20);
        taNapomena.setLineWrap(true);
        JScrollPane spNapomena = new JScrollPane(taNapomena);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridheight = 2;
        add(spNapomena, gbc);
        row += 2;

        JLabel lbObavezno = new JLabel("* obavezno polje");
        lbObavezno.setForeground(Color.RED);
        lbObavezno.setFont(new Font("Arial", Font.ITALIC, 10));
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lbObavezno, gbc);
        row++;

        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = row;

        JButton btnDodaj = new JButton("Dodaj aktivnost");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dodajAktivnost();
            }
        });
        add(btnDodaj, gbc);
        row++;

        JPanel panelGumbi = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnSpremi = new JButton("Spremi u TXT");
        btnSpremi.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                spremiTXT();
            }
        });
        panelGumbi.add(btnSpremi);

        JButton btnUcitaj = new JButton("Učitaj TXT");
        btnUcitaj.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ucitajTXT();
            }
        });
        panelGumbi.add(btnUcitaj);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        add(panelGumbi, gbc);
    }

    private void dodajAktivnost() {
        String naziv = tfNaziv.getText().trim();
        String tema = tfTema.getText().trim();
        String trajanjeStr = tfTrajanje.getText().trim();
        String napomena = taNapomena.getText().trim();

        if (naziv.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Naziv aktivnosti je obavezan podatak!",
                    "Greška validacije", JOptionPane.ERROR_MESSAGE);
            tfNaziv.requestFocus();
            return;
        }

        if (tema.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tema/Kolegij je obavezan podatak!",
                    "Greška validacije", JOptionPane.ERROR_MESSAGE);
            tfTema.requestFocus();
            return;
        }

        if (trajanjeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Trajanje je obavezan podatak!",
                    "Greška validacije", JOptionPane.ERROR_MESSAGE);
            tfTrajanje.requestFocus();
            return;
        }

        try {
            int trajanje = Integer.parseInt(trajanjeStr);

            if (trajanje <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Trajanje mora biti pozitivan broj!",
                        "Greška validacije", JOptionPane.ERROR_MESSAGE);
                tfTrajanje.requestFocus();
                tfTrajanje.selectAll();
                return;
            }

            TipAktivnosti tip = (TipAktivnosti) cbTip.getSelectedItem();
            AktivnostUcenja aktivnost = new AktivnostUcenja(naziv, tema, tip, trajanje, napomena);
            aktivnosti.add(aktivnost);

            osvjeziPrikaz();

            tfNaziv.setText("");
            tfTema.setText("");
            tfTrajanje.setText("");
            taNapomena.setText("");

            tfNaziv.requestFocus();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Trajanje mora biti cijeli broj!",
                    "Greška validacije", JOptionPane.ERROR_MESSAGE);
            tfTrajanje.requestFocus();
            tfTrajanje.selectAll();
        }
    }

    private void spremiTXT() {
        if (aktivnosti.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nema aktivnosti za spremanje!",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        AUX_RW.spremiTXT("DATA/plan_ucenja.txt", aktivnosti);
        JOptionPane.showMessageDialog(this,
                "Aktivnosti spremljene u DATA/plan_ucenja.txt",
                "Spremljeno", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ucitajTXT() {
        List<AktivnostUcenja> ucitane = AUX_RW.ucitajTXT("DATA/plan_ucenja.txt");

        if (ucitane.isEmpty()) {
            int odgovor = JOptionPane.showConfirmDialog(this,
                    "Datoteka je prazna ili ne postoji. Nastaviti?",
                    "Upozorenje", JOptionPane.YES_NO_OPTION);
            if (odgovor == JOptionPane.YES_OPTION) {
                aktivnosti.clear();
                osvjeziPrikaz();
            }
            return;
        }

        aktivnosti.clear();
        aktivnosti.addAll(ucitane);
        osvjeziPrikaz();

        JOptionPane.showMessageDialog(this,
                "Učitano " + ucitane.size() + " aktivnosti",
                "Učitano", JOptionPane.INFORMATION_MESSAGE);
    }

    private void osvjeziPrikaz() {
        StringBuilder sb = new StringBuilder();
        if (aktivnosti.isEmpty()) {
            sb.append("Nema unesenih aktivnosti.\n");
        } else {
            for (int i = 0; i < aktivnosti.size(); i++) {
                sb.append((i + 1) + ". " + aktivnosti.get(i).toString() + "\n");
            }
        }
        taPrikaz.setText(sb.toString());
    }
}