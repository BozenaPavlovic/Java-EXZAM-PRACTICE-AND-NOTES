package zad_2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SpremiListener implements ActionListener {
    private MainFrame mainFrame;

    public SpremiListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<AktivnostUcenja> aktivnosti = mainFrame.getAktivnosti();
        AUX_RW.upisiAktivnosti(aktivnosti, "DATA/plan_ucenja.txt");
    }
}
