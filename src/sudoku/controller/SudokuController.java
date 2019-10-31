package sudoku.controller;

import sudoku.service.GenerateurGrilleJoueur;
import sudoku.service.GenerateurGrilleSolution;
import sudoku.view.GrilleView;

import javax.swing.*;

public class SudokuController {
	private JFrame frame;
    private GrilleView view;
    private GenerateurGrilleSolution generateurGrilleSolution;
    private GenerateurGrilleJoueur generateurGrilleJoueur;
    private int[][] grilleSolution;
    private int[][] grilleJoueur;

    public SudokuController(JFrame frame) {
        this.frame = frame;
        this.generateurGrilleSolution = new GenerateurGrilleSolution();
    }

    public void newGrille(String niveauDifficulte) {
    	this.grilleSolution = this.generateurGrilleSolution.getGrilleSolution();
    	this.generateurGrilleJoueur = new GenerateurGrilleJoueur(grilleSolution, niveauDifficulte);
    	
    	this.grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();
    	
        this.view = new GrilleView(frame, grilleSolution,grilleJoueur);
           	
    	
    	
        
        this.view.setGame(grilleJoueur);
        
        // test validation grille
        //estGrilleValide();
    }

   

//    public boolean estCaseValide() {
//		
//	}
}
