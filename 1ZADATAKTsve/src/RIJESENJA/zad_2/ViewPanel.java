package RIJESENJA.zad_2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewPanel extends JPanel {
    private JTextArea prikaz;
    private JScrollPane scrollPane;
    private JButton spremiButton;
    private JButton ucitajButton;

    public ViewPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));

        // Gornja panel s gumbima
        JPanel gumbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        spremiButton = new JButton("Spremi u TXT");
        ucitajButton = new JButton("Učitaj TXT");
        gumbPanel.add(spremiButton);
        gumbPanel.add(ucitajButton);
        add(gumbPanel, BorderLayout.NORTH);

        // Tekst area za prikaz
        prikaz = new JTextArea();
        prikaz.setEditable(false);
        prikaz.setFont(new Font("Monospaced", Font.PLAIN, 11));
        prikaz.setLineWrap(true);
        prikaz.setWrapStyleWord(true);
        scrollPane = new JScrollPane(prikaz);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JButton getSpremiButton() {
        return spremiButton;
    }

    public JButton getUcitajButton() {
        return ucitajButton;
    }

    public void osvjeziPrikaz(ArrayList<AktivnostUcenja> aktivnosti) {
        prikaz.setText("");
        if (aktivnosti.isEmpty()) {
            prikaz.setText("Nema aktivnosti.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== PLAN UČENJA ===").append("\n\n");
        for (int i = 0; i < aktivnosti.size(); i++) {
            AktivnostUcenja a = aktivnosti.get(i);
            sb.append((i + 1)).append(". ").append(a.toString()).append("\n");
        }
        prikaz.setText(sb.toString());
    }
}