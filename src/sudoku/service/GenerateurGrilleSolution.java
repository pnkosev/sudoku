package sudoku.service;

import java.util.HashSet;
import java.util.Random;

public class GenerateurGrilleSolution {
	int[][] grilleSolution;
	Random chiffreAleatoire;
	HashSet<Integer> uniqueSetChiffres;
	HashSet<Integer> chiffresTestes;

	public GenerateurGrilleSolution() {
		this.grilleSolution = new int[9][9];
		this.chiffreAleatoire = new Random();
		this.uniqueSetChiffres = new HashSet<Integer>();
		this.chiffresTestes = new HashSet<Integer>();
		
		boolean estGenere = generateur();
		
		while (!estGenere) {
			estGenere = generateur();
		}
	}

	private boolean generateur() {
		int compteur = 1;
		
		for (int ligne = 0; ligne < 9; ligne++) {
			this.uniqueSetChiffres.clear();
			this.chiffresTestes.clear();
			
			for (int col = 0; col < 9; col++) {
				
				int chiffre = generateurChiffreAleatoire();
				
				while (this.uniqueSetChiffres.contains(chiffre)) {
					chiffre = generateurChiffreAleatoire();
				}
				
				while (!estLigneOk(ligne, chiffre) || !estColOk(col, chiffre) || !estCarreOk(ligne, col, chiffre)) {
					
					this.chiffresTestes.add(chiffre);
					chiffre = generateurChiffreAleatoire();
					
					if (this.chiffresTestes.size() == 9) {
						compteur++;
						
						if (compteur == 100) {
							return false;
						}
						
						col = 0;
						
						for (int i = 0; i < 9; i++) {
							grilleSolution[ligne][i] = 0;
						}
						
						this.uniqueSetChiffres.clear();
						this.chiffresTestes.clear();
						chiffre = generateurChiffreAleatoire();
					}
				}
				
				grilleSolution[ligne][col] = chiffre;
				this.uniqueSetChiffres.add(chiffre);
				this.chiffresTestes.add(chiffre);
			}
		}
		
		return true;
	}

	private boolean estLigneOk(int ligne, int chiffre) {
		for (int col = 0; col < 9; col++) {
			if (grilleSolution[ligne][col] == chiffre) {
				return false;
			}
		}
		return true;
	}

	private boolean estColOk(int col, int chiffre) {
		for (int ligne = 0; ligne < 9; ligne++) {
			if (grilleSolution[ligne][col] == chiffre) {
				return false;
			}
		}
		return true;
	}

	private boolean estCarreOk(int ligne, int col, int chiffre) {
		if (ligne < 3) {
			ligne = 0;
		} else if (ligne < 6) {
			ligne = 3;
		} else {
			ligne = 6;
		}
		
		if (col < 3) {
			col = 0;
		} else if (col < 6) {
			col = 3;
		} else {
			col = 6;
		}
		for (int i = ligne; i < ligne + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if (grilleSolution[i][j] == chiffre) {
					return false;
				}
			}
		}
		return true;
	}
	
	private int generateurChiffreAleatoire() {
		return this.chiffreAleatoire.nextInt(9) + 1;
	}
	
	public int[][] getGrilleSolution() {
		return this.grilleSolution;
	}
}
