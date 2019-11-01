package sudoku.controller;

import sudoku.service.GenerateurGrilleJoueur;
import sudoku.service.GenerateurGrilleSolution;
import sudoku.view.GrilleView;

import javax.swing.*;

public class SudokuController extends AbstractController {

    private GrilleView view;
    private GenerateurGrilleSolution generateurGrilleSolution;
    private GenerateurGrilleJoueur generateurGrilleJoueur;
    private int[][] grilleSolution;
    private int[][] grilleJoueur;

    public SudokuController(JFrame frame) {
        super(frame);
        this.generateurGrilleSolution = new GenerateurGrilleSolution();
    }

    public void newGrille(String niveauDifficulte) {
        this.grilleSolution = this.generateurGrilleSolution.getGrilleSolution();
        this.generateurGrilleJoueur = new GenerateurGrilleJoueur(grilleSolution, niveauDifficulte);
        this.grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();
        this.view = new GrilleView(this);
        this.view.setGame(grilleJoueur);
    }

    /**
     * Vérifier si le chiffre à la position [line][column] correspond est bon
     *
     * @param number
     * @param line
     * @param column
     * @return
     */
    private boolean isValidNumber(int number, int line, int column) {
        return grilleSolution[line][column] == number;

    }


    public void validateGrid() {
        if (estGrillePleine()) {

            for (int ligne = 0; ligne < grilleJoueur.length; ligne++) {
                for (int col = 0; col < grilleJoueur[ligne].length; col++) {
                    if (!isValidNumber(grilleJoueur[ligne][col], ligne, col)) {
                        view.mettreEnErreur(ligne, col);
                    } else {
                        view.mettreEnValide(ligne, col);
                    }
                }
            }
        }
    }

    /**
     * Ajouter le chiffre choisi dans la grille de l'utilisatuer.
     * Si l'aide est activée afficher visuellement le statut du chiffre (valide/erreur)
     *
     * @param number
     * @param line
     * @param column
     * @param helpState
     */
    public void setUserNumber(int number, int line, int column, boolean helpState) {
        grilleJoueur[line][column] = number;
        if (helpState) {
            if (!isValidNumber(number, line, column)) {
                view.mettreEnErreur(line, column);
            } else {
                view.mettreEnValide(line, column);
            }
        }
    }

    private boolean estGrillePleine() {

        for (int ligne = 0; ligne < grilleJoueur.length; ligne++) {
            for (int col = 0; col < grilleJoueur[ligne].length; col++) {
                if (grilleJoueur[ligne][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
