package sudoku.view;

import javax.swing.*;

public class AboutView {

    private String msg = "Sudoku 2019\n La dream team: Bulles";

    public AboutView(JFrame frame) {
        JOptionPane.showMessageDialog(frame, msg);
    }
}
