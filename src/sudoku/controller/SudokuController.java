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

    public void newGrille() {
        this.view = new GrilleView(frame);
        
        this.grilleSolution = this.generateurGrilleSolution.getGrilleSolution();
    	String niveauDifficulte = "expert";
    	
    	this.generateurGrilleJoueur = new GenerateurGrilleJoueur(grilleSolution, niveauDifficulte);
    	
    	this.grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();
        
        this.view.setGame(grilleJoueur);
        
        // test validation grille
        estGrilleValide();
    }

    public boolean estGrilleValide() {
		boolean estValide = true;    	
    	
		for (int ligne = 0; ligne < grilleJoueur.length; ligne++) {
			for (int col = 0; col < grilleJoueur[ligne].length; col++) {
				if (grilleJoueur[ligne][col] != grilleSolution[ligne][col]) {
					this.view.mettreEnErreur(ligne, col);
					estValide = false;
				} else {
					this.view.mettreEnValide(ligne, col);
				}
			}
		}
		
    	return estValide;
	}

//    public boolean estCaseValide() {
//		
//	}
}
