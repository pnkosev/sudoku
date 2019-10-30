//package sudoku;
//
//public class Test {
//	GenerateurGrilleSolution generateurGrilleSolution = new GenerateurGrilleSolution();
//	int[][] grilleSolution = generateurGrilleSolution.getGrilleSolution();
//	
//	for (int ligne = 0; ligne < grilleSolution.length; ligne++) {
//		for (int col = 0; col < grilleSolution[ligne].length; col++) {
//			System.out.print(grilleSolution[ligne][col] + " ");
//		}
//		System.out.println();
//	}
//	
//	System.out.println();
//	
//	GenerateurGrilleJoueur generateurGrilleJoueur = new GenerateurGrilleJoueur();
//	
//	String niveauDifficulte = "debutant";
//	
//	boolean estGrilleJoueurGeneree = generateurGrilleJoueur.creeGrilleJoueur(grilleSolution, niveauDifficulte);
//	
//	while (!estGrilleJoueurGeneree) {
//		estGrilleJoueurGeneree = generateurGrilleJoueur.creeGrilleJoueur(grilleSolution, niveauDifficulte);
//	}
//	
//	int[][] grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();
//	
//	for (int ligne = 0; ligne < grilleJoueur.length; ligne++) {
//		for (int col = 0; col < grilleJoueur[ligne].length; col++) {
//			System.out.print(grilleJoueur[ligne][col] + " ");
//		}
//		System.out.println();
//	}
//}
