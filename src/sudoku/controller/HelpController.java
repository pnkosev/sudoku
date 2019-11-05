package sudoku.controller;

import sudoku.view.AboutView;
import sudoku.view.RulesView;


import javax.swing.*;

public class HelpController {
    JFrame frame;

    public HelpController(JFrame frame) {
        this.frame = frame;
    }

    public void rules() {
        new RulesView(frame);
    }

    public void about(){
        new AboutView(frame);
    }
   
}
