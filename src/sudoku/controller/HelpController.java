package sudoku.controller;

import sudoku.view.AboutView;
import sudoku.view.RulesView;

import javax.swing.*;

public class HelpController extends AbstractController{

    public HelpController(JFrame frame) {
        super(frame);
    }

    public void rules() {
        new RulesView(frame);
    }

    public void about(){
        new AboutView(frame);
    }
}
