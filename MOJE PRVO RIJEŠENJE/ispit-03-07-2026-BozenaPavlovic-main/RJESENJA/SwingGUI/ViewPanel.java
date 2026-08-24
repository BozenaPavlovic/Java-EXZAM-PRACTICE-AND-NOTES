package SwingGUI;

import javax.swing.*;
import java.awt.*;

public class ViewPanel extends JPanel {
    private JTextArea taPrikaz;

    public ViewPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Pregled aktivnosti"));

        taPrikaz = new JTextArea(15, 40);
        taPrikaz.setEditable(false);
        taPrikaz.setText("Nema unesenih aktivnosti.\n");

        JScrollPane scrollPane = new JScrollPane(taPrikaz);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextArea getTextArea() {
        return taPrikaz;
    }
}