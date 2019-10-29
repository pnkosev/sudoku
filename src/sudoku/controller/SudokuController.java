package sudoku.controller;

import sudoku.view.GrilleView;

import javax.swing.*;
import java.util.Random;

public class SudokuController {
    JFrame frame;


    public SudokuController(JFrame frame) {
        this.frame = frame;
    }

    public void newGrille() {
        GrilleView view = new GrilleView(frame);
        view.setGame(getRandomData());
    }

    public int[][] getRandomData() {
        int [][] data = new int[9][9];
        Random rand = new Random();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                data[y][x] = rand.nextInt(9) + 1;
            }
        }

        return data;
    }
}
