package SwingGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private List<AktivnostUcenja> aktivnosti;
    private ViewPanel viewPanel;
    private FormPanel formPanel;

    public MainFrame() {
        setTitle("Plan učenja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        aktivnosti = new ArrayList<>();

        viewPanel = new ViewPanel();
        formPanel = new FormPanel(aktivnosti, viewPanel.getTextArea());

        add(formPanel, BorderLayout.WEST);
        add(viewPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}