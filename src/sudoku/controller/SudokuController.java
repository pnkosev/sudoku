package sudoku.controller;

import sudoku.service.GenerateurGrilleJoueur;
import sudoku.service.GenerateurGrilleSolution;
import sudoku.view.GrilleView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;



public class SudokuController {
		private JFrame frame;
		private GrilleView view;
		private GenerateurGrilleSolution generateurGrilleSolution;
		private GenerateurGrilleJoueur generateurGrilleJoueur;
		private int[][] grilleSolution;
		private int[][] grilleJoueur;
		private int[][] grilleJoueurInitiale = new int[9][9];
		
		

	public SudokuController(JFrame frame) {
		this.frame = frame;
		this.generateurGrilleSolution = new GenerateurGrilleSolution();
	}

	public void newGrille(String niveauDifficulte) {
		this.grilleSolution = this.generateurGrilleSolution.getGrilleSolution();
		this.generateurGrilleJoueur = new GenerateurGrilleJoueur(grilleSolution, niveauDifficulte);

		this.grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();
		
		for (int x = 0; x < 9; x++) {
			grilleJoueurInitiale[x] = Arrays.copyOf(grilleJoueur[x], grilleJoueur[x].length);
//			for (int y = 0; y < 9; y++) {	
//			
//				grilleJoueurInitiale[x][y] = grilleJoueur[x][y];
//			}
		}
		//this.grilleJoueurInitiale = Arrays.copyOf(grilleJoueur, grilleJoueur.length);
		//System.arraycopy(grilleJoueur, 0, grilleJoueurInitiale, 0, grilleJoueur.length);
		System.out.println("grille initiale : " +Arrays.deepToString(grilleJoueurInitiale));
		
		this.view = new GrilleView(frame, grilleSolution,grilleJoueur);




		this.view.setGame(grilleJoueur);

		// test validation grille
		//estGrilleValide();
	}
	public void save(String niveauDifficulte ){
		String msg = "Souhaitez-vous sauvegarder \nvotre progression avant de quitter?";
		UIManager.put("OptionPane.noButtonText", "NON");
		UIManager.put("OptionPane.yesButtonText", "OUI");
		UIManager.put("OptionPane.okButtonText", "Quitter");

		int reponse = JOptionPane.showConfirmDialog(frame,
				msg,
				"Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE); 

		if(reponse == JOptionPane.YES_OPTION ){
			ArrayList<Object> listeGrillesToSave = new ArrayList<Object>();		
			listeGrillesToSave.add(grilleSolution);
			listeGrillesToSave.add(grilleJoueur);			
			listeGrillesToSave.add(niveauDifficulte);
			listeGrillesToSave.add(grilleJoueurInitiale);
			listeGrillesToSave.add(view.getSecondes());
			//listeGrillesToSave.add(this.view.getGrilleJoueurInitiale());
			try
			{ 
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("savedGame.ser"));			
				output.writeObject(listeGrillesToSave);			
				output.close();
			}

			catch(IOException ex) 
			{ 
				System.out.println("IOException is caught"); 
			} 	    	    		    	
			JOptionPane.showMessageDialog(frame, "Sauvegardé !");	    	  
		}
		System.exit(0);		

	}  

	public Boolean isThereASave() {
			File f = new File("savedGame.ser");
			if(f.exists() && !f.isDirectory())
			{ 
				return true;
			}else {
				return false;
			}
	}

	public void lireSauvegarde() {
		try
		{ 
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("savedGame.ser"));
			ArrayList<Object> grilles = (ArrayList<Object>)input.readObject();       
			this.grilleSolution = (int[][]) grilles.get(0);
			this.grilleJoueur = (int[][]) grilles.get(1);
			this.grilleJoueurInitiale = (int[][]) grilles.get(3);
			System.out.println(Arrays.deepToString(grilleSolution));
			System.out.println(Arrays.deepToString(grilleJoueur));
			System.out.println(Arrays.deepToString(grilleJoueurInitiale));
			
			//niveau
			System.out.println(grilles.get(2));			
			this.view = new GrilleView(frame, grilleSolution,grilleJoueur);
			this.view.setSecondes((int) grilles.get(4));
			this.view.setGameSaved(grilleJoueurInitiale, grilleJoueur);             
			input.close();

		} 
		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught"); 
		} catch (ClassNotFoundException e) {

			System.out.println("ClassNotFoundException is caught"); 
		}

	}
	//    public boolean estCaseValide() {
	//		
	//	}
}
