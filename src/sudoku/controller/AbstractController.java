package sudoku.controller;

import javax.swing.JFrame;

abstract public class AbstractController {
    protected JFrame frame;

    public AbstractController(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
