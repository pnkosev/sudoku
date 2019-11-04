package sudoku.controller;

import sudoku.service.GenerateurGrilleJoueur;
import sudoku.service.GenerateurGrilleSolution;
import sudoku.service.HallOfFame;
import sudoku.view.GrilleView;
import sudoku.view.HallOfFameView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class SudokuController extends AbstractController {

    private GrilleView view;
    private GenerateurGrilleSolution generateurGrilleSolution;
    private GenerateurGrilleJoueur generateurGrilleJoueur;
    private int[][] grilleSolution;
    private int[][] grilleJoueur;
    private int[][] grilleInitiale;
    private String niveauDifficulte;
    List<int[][]> historiqueGrilles;
    ArrayDeque<int[][]> forwardGrilles;

    public SudokuController(JFrame frame) {
        super(frame);
        this.generateurGrilleSolution = new GenerateurGrilleSolution();
    }

    public void newGrille(String niveauDifficulte) {
        this.niveauDifficulte = niveauDifficulte;
        this.historiqueGrilles = new ArrayList<int[][]>();
        this.forwardGrilles = new ArrayDeque<int[][]>();
        
        this.grilleSolution = this.generateurGrilleSolution.getGrilleSolution();
        this.generateurGrilleJoueur = new GenerateurGrilleJoueur(grilleSolution, niveauDifficulte);
        this.grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();
        
        this.grilleInitiale = new int[9][9];
        for (int i = 0; i < grilleJoueur.length; i++) {
			grilleInitiale[i] = Arrays.copyOf(grilleJoueur[i], grilleJoueur[i].length);
		}
        
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

    /**
     * Valider la grille du jeu
     *
     * @param secondes le temps du jeu en secondes
     */
    public void validateGrid(Integer secondes) {
        if (estGrillePleine()) {
            boolean isValid = true;
            for (int ligne = 0; ligne < grilleJoueur.length; ligne++) {
                for (int col = 0; col < grilleJoueur[ligne].length; col++) {
                    if (!isValidNumber(grilleJoueur[ligne][col], ligne, col)) {
                        view.mettreEnErreur(ligne, col);
                        isValid = false;
                    } else {
                        view.mettreEnValide(ligne, col);
                    }
                }
            }
            if (isValid) {
                displayHallOfFame(secondes);
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
    	if (historiqueGrilles.size() == 0) {
    		historiqueGrilles.add(this.grilleInitiale);
    	} else {
    		historiqueGrilles.add(copyMatrix(this.grilleJoueur));
    	}
    	forwardGrilles.clear();
    	
        grilleJoueur[line][column] = number;
        if (helpState) {
            if (!isValidNumber(number, line, column)) {
                view.mettreEnErreur(line, column);
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

    public  void displayHallOfFame(int secondes) {
        HallOfFame hof = new HallOfFame();

        int index = hof.verifTemps(niveauDifficulte, secondes);
        if (index != -1) {

            String nom = (String) JOptionPane.showInputDialog(
                    getFrame(),
                    "Bravo! Veuillez entrer votre nom: ",
                    "Hall of Fame",
                    JOptionPane.PLAIN_MESSAGE
            );

            hof.modifFichier(nom, secondes, index);
            new HallOfFameView(hof.getListeHOF());
        }
    }

	public void goBack() {
		if (historiqueGrilles.size() != 0) {
			int[][] ancienneGrille = historiqueGrilles.remove(historiqueGrilles.size() - 1);
			
			forwardGrilles.push(copyMatrix(grilleJoueur));

			setGrilleJoueur(ancienneGrille);
			
			this.view.setGame(this.grilleInitiale, ancienneGrille);
		}
	}

	public void goForward() {
		if (forwardGrilles.size() != 0) {
			int[][] forwardGrille = forwardGrilles.pop();
			
			historiqueGrilles.add(copyMatrix(grilleJoueur));
			
			setGrilleJoueur(forwardGrille);
			
			this.view.setGame(this.grilleInitiale, forwardGrille);
		}
	}
	
	
	// copier une matrice
	private int[][] copyMatrix(int[][] originalMatrix) {
		int[][] newMatrix = new int[originalMatrix.length][originalMatrix[1].length];
		
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = Arrays.copyOf(originalMatrix[i], originalMatrix[i].length);
		}
		
		return newMatrix;
	}
	
	
	// mettre a jour la grilleJoueur
	private void setGrilleJoueur(int[][] grilleVise) {
		for (int i = 0; i < grilleJoueur.length; i++) {
			this.grilleJoueur[i] = Arrays.copyOf(grilleVise[i], grilleVise[i].length);
		}
	}
}
