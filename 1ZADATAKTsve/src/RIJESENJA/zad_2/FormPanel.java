package RIJESENJA.zad_2;

import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {
    private JTextField nazivField;
    private JTextField temaField;
    private JComboBox<TipAktivnosti> tipComboBox;
    private JTextField trajanjeField;
    private JTextArea napomenaArea;
    private JButton dodajButton;

    public FormPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Naziv aktivnosti
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Naziv aktivnosti:"), gbc);

        gbc.gridx = 1;
        nazivField = new JTextField(20);
        add(nazivField, gbc);

        // Tema/Kolegij
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tema/Kolegij:"), gbc);

        gbc.gridx = 1;
        temaField = new JTextField(20);
        add(temaField, gbc);

        // Tip aktivnosti
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tip aktivnosti:"), gbc);

        gbc.gridx = 1;
        tipComboBox = new JComboBox<>(TipAktivnosti.values());
        add(tipComboBox, gbc);

        // Trajanje (minute)
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Trajanje (minute):"), gbc);

        gbc.gridx = 1;
        trajanjeField = new JTextField(20);
        add(trajanjeField, gbc);

        // Napomena
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Napomena:"), gbc);

        gbc.gridx = 1;
        napomenaArea = new JTextArea(3, 20);
        napomenaArea.setLineWrap(true);
        napomenaArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(napomenaArea);
        add(scrollPane, gbc);

        // Dodaj aktivnost button
        gbc.gridx = 1;
        gbc.gridy = 5;
        dodajButton = new JButton("Dodaj aktivnost");
        add(dodajButton, gbc);
    }

    public JButton getDodajButton() {
        return dodajButton;
    }

    public String getNaziv() {
        return nazivField.getText().trim();
    }

    public String getTema() {
        return temaField.getText().trim();
    }

    public TipAktivnosti getTip() {
        return (TipAktivnosti) tipComboBox.getSelectedItem();
    }

    public String getTrajanje() {
        return trajanjeField.getText().trim();
    }

    public String getNapomena() {
        return napomenaArea.getText().trim();
    }

    public void ocistiPolja() {
        nazivField.setText("");
        temaField.setText("");
        tipComboBox.setSelectedIndex(0);
        trajanjeField.setText("");
        napomenaArea.setText("");
    }

    public void prikaziGresku(String poruka) {
        JOptionPane.showMessageDialog(this, poruka, "Greška validacije", JOptionPane.ERROR_MESSAGE);
    }
}
