package sudoku.service;

import java.util.HashSet;
import java.util.Random;

public class GenerateurGrilleJoueur {
	private int[][] grilleJoueur;
	private Random chiffreAleatoire;
	HashSet<Integer> colsTestes;
	private int nombreChiffresCol;
	
	public GenerateurGrilleJoueur() {
		this.chiffreAleatoire = new Random();
		this.colsTestes = new HashSet<Integer>();
	}
	
	public boolean creeGrilleJoueur(int[][] grilleSolution, String niveau) {
		switch (niveau) {
		case "debutant":
			nombreChiffresCol = 4;
			break;
		case "intermediaire":
			nombreChiffresCol = 3;
			break;
		case "expert":
			nombreChiffresCol = 2;
			break;
		default:
			nombreChiffresCol = 3;
		}
		
		this.grilleJoueur = new int[9][9];
		
		int niveauDif;
		
		for (int ligne = 0; ligne < 9; ligne++) {
			this.colsTestes.clear();
			
			niveauDif = this.nombreChiffresCol;
			
			while (niveauDif-- > 0) {
				int col = generateurChiffreAleatoire();
				remplissageCol(grilleSolution, ligne, col);
			}
		}
		
		return true;
	}
	
	private boolean remplissageCol(int[][] grilleSolution, int ligne, int col) {
		while (grilleJoueur[ligne][col] != 0 || !estColOk(col)) {
			col = generateurChiffreAleatoire();
			if (!this.colsTestes.contains(col)) {
				this.colsTestes.add(col);
			}
			if (this.colsTestes.size() == 9) {
				return false;
			}
		}
		this.colsTestes.add(col);
		
		grilleJoueur[ligne][col] = grilleSolution[ligne][col];
		
		return true;
	}

	private boolean estColOk(int col) {
		int compteur = 0;
		for (int i = 0; i < 9; i++) {
			if (grilleJoueur[i][col] != 0) {
				compteur++;
			}
		}
		return compteur < nombreChiffresCol;
	}
	
	private int generateurChiffreAleatoire() {
		return this.chiffreAleatoire.nextInt(9);
	}
	
	public int[][] getGrilleJoueur() {
		return this.grilleJoueur;
	}
}
