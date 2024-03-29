package sudoku.controller;

import sudoku.service.GenerateurGrilleJoueur;
import sudoku.service.GenerateurGrilleSolution;
import sudoku.service.HallOfFame;
import sudoku.view.GrilleView;
import sudoku.view.HallOfFameView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayDeque;
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
	private int[][] grilleJoueurInitiale = new int[9][9];
	private List<int[][]> historiqueGrilles;
	private ArrayDeque<int[][]> forwardGrilles;
	private boolean isGameSaved;

	public SudokuController(JFrame frame) {
		super(frame);
		this.generateurGrilleSolution = new GenerateurGrilleSolution();
	}

	public String getNiveauDifficulte() {
		return niveauDifficulte;
	}

	public void setNiveauDifficulte(String niveauDifficulte) {
		this.niveauDifficulte = niveauDifficulte;
	}

	public void newGrille(String niveauDifficulte) {
		this.niveauDifficulte = niveauDifficulte;
		this.historiqueGrilles = new ArrayList<int[][]>();
		this.forwardGrilles = new ArrayDeque<int[][]>();
		this.grilleInitiale = new int[9][9];

		this.grilleSolution = this.generateurGrilleSolution.getGrilleSolution();
		this.generateurGrilleJoueur = new GenerateurGrilleJoueur(grilleSolution, niveauDifficulte);
		this.grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();

		for (int x = 0; x < 9; x++) {
			grilleJoueurInitiale[x] = Arrays.copyOf(grilleJoueur[x], grilleJoueur[x].length);
			grilleInitiale[x] = Arrays.copyOf(grilleJoueur[x], grilleJoueur[x].length);
		}

		this.view = new GrilleView(this);
		this.view.setGame(grilleJoueur);
		this.view.setIsBackEnabled(false);
		this.view.setIsForwardEnabled(false);
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
	 * Ajouter le chiffre choisi dans la grille de l'utilisatuer. Si l'aide est
	 * activée afficher visuellement le statut du chiffre (valide/erreur)
	 *
	 * @param number
	 * @param line
	 * @param column
	 * @param helpState
	 */
	public void setUserNumber(int number, int line, int column, boolean helpState) {
		if (this.historiqueGrilles.size() == 0) {
			historiqueGrilles.add(copyMatrix(this.grilleInitiale));
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

	public void displayHallOfFame(int secondes) {
		HallOfFame hof = new HallOfFame();

		int index = hof.verifTemps(niveauDifficulte, secondes);
		if (index != -1) {

			String nom = (String) JOptionPane.showInputDialog(getFrame(), "Bravo! Veuillez entrer votre nom: ",
					"Hall of Fame", JOptionPane.PLAIN_MESSAGE);

			hof.modifFichier(nom, secondes, index);
			new HallOfFameView(hof.getListeHOF());
		}
	}

	public void save(String niveauDifficulte) {
		String msg = "Souhaitez-vous sauvegarder \nvotre progression avant de quitter?";
		UIManager.put("OptionPane.noButtonText", "NON");
		UIManager.put("OptionPane.yesButtonText", "OUI");
		UIManager.put("OptionPane.okButtonText", "Quitter");

		int reponse = JOptionPane.showConfirmDialog(frame, msg, "Confirmation", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (reponse == JOptionPane.YES_OPTION) {
			ArrayList<Object> listeGrillesToSave = new ArrayList<Object>();
			listeGrillesToSave.add(grilleSolution);
			listeGrillesToSave.add(grilleJoueur);
			listeGrillesToSave.add(niveauDifficulte);
			listeGrillesToSave.add(grilleJoueurInitiale);
			listeGrillesToSave.add(view.getSecondes());
			// listeGrillesToSave.add(this.view.getGrilleJoueurInitiale());
			try {
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("savedGame.ser"));
				output.writeObject(listeGrillesToSave);
				output.close();
			}

			catch (IOException ex) {
				System.out.println("IOException is caught");
			}
			JOptionPane.showMessageDialog(frame, "Sauvegardé !");
		}
		System.exit(0);

	}

	public Boolean isThereASave() {
		File f = new File("savedGame.ser");
		
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public void lireSauvegarde() {
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("savedGame.ser"));
			ArrayList<Object> grilles = (ArrayList<Object>) input.readObject();
			this.grilleSolution = (int[][]) grilles.get(0);
			this.grilleJoueur = (int[][]) grilles.get(1);
			this.grilleJoueurInitiale = (int[][]) grilles.get(3);
			this.grilleInitiale = copyMatrix((int[][]) grilles.get(1));
			setNiveauDifficulte((String) grilles.get(2));

			// niveau
			this.view = new GrilleView(this);
			this.forwardGrilles = new ArrayDeque<int[][]>();
			this.historiqueGrilles = new ArrayList<int[][]>();
			this.view.setSecondes((int) grilles.get(4));
			this.view.setGameSaved(grilleJoueurInitiale, grilleJoueur);
			input.close();

		} catch (IOException ex) {
			System.out.println("IOException is caught");
		} catch (ClassNotFoundException e) {

			System.out.println("ClassNotFoundException is caught");
		}

	}

	public void goBack() {
		if (historiqueGrilles.size() != 0) {
			

			int[][] ancienneGrille = historiqueGrilles.remove(historiqueGrilles.size() - 1);
			
			if (historiqueGrilles.size() == 0) {
				this.view.setIsBackEnabled(false);
			}

			forwardGrilles.push(copyMatrix(grilleJoueur));
			this.view.setIsForwardEnabled(true);

			setGrilleJoueur(ancienneGrille);

			if (!this.isGameSaved) {
				this.view.setGameBackAndForth(this.grilleInitiale, ancienneGrille);
			} else {
				this.view.setGameBackAndForth(this.grilleInitiale, this.grilleJoueurInitiale, ancienneGrille);
			}
		} else {
			this.view.setIsBackEnabled(false);
		}
	}

	public void goForward() {
		if (forwardGrilles.size() != 0) {
			int[][] forwardGrille = forwardGrilles.pop();
			
			if (forwardGrilles.size() == 0) {
				this.view.setIsForwardEnabled(false);
				this.view.setIsBackEnabled(true);
			}

			historiqueGrilles.add(copyMatrix(grilleJoueur));

			setGrilleJoueur(forwardGrille);

			if (!this.isGameSaved) {
				this.view.setGameBackAndForth(this.grilleInitiale, forwardGrille);
			} else {
				this.view.setGameBackAndForth(this.grilleInitiale, this.grilleJoueurInitiale, forwardGrille);
			}
		} else {
			this.view.setIsForwardEnabled(false);
			this.view.setIsBackEnabled(true);
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

	public void setIsGameSaved(boolean isSaved) {
		this.isGameSaved = isSaved;
	}
}
