package zad_2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UcitajListener implements ActionListener {
    private MainFrame mainFrame;

    public UcitajListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<AktivnostUcenja> aktivnosti = AUX_RW.ucitajAktivnosti("DATA/plan_ucenja.txt");
        mainFrame.setAktivnosti(aktivnosti);
    }
}
