package sudoku.service;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class HallOfFame {
	File fichier = new File("src/sudoku/tableDesScores.txt");
	String fichierChemin = fichier.getAbsolutePath();
	List<String> lignes;
	
	public HallOfFame() {
		lireFichier();
	}
	
	public int verifTemps(String niveauDif, int secondesVerif) {
		for (int index = 0; index < lignes.size(); index++) {
			String [] tableauLigne = lignes.get(index).split(",");
			String niveau = tableauLigne[0];
			if (niveau.equals(niveauDif)) {
				int secondes =Integer.parseInt(tableauLigne[2]);
				if (secondesVerif < secondes) {
					return index;
				}
			}
		}
		
		return -1;
	}
	
	public void modifFichier(String surnom, int secondes, int index) {
		String[] ligne = lignes.get(index).split(",");
		
		ligne[1] = surnom;
		ligne[2] = String.valueOf(secondes);
		String nouvelleLigne = String.format("%s,%s,%s", ligne[0], ligne[1], ligne[2]);
		
		if (index % 3 == 0) {
			lignes.set(index + 2, lignes.get(index + 1));
			lignes.set(index + 1, lignes.get(index));
			lignes.set(index, nouvelleLigne);
		} else if (index % 3 == 1) {
			lignes.set(index + 1, lignes.get(index));
			lignes.set(index, nouvelleLigne);
		} else {
			lignes.set(index, nouvelleLigne);
		}
		
		Path path = FileSystems.getDefault().getPath(fichierChemin);
		
		String nouvelleListeString = ""; 
		for (String ligneString: lignes) {
			nouvelleListeString += ligneString + "\n";
		}
		
		try {
			Files.write(path, nouvelleListeString.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lireFichier();
	}
	
	private void lireFichier() {
		try {
			this.lignes = Files.readAllLines(FileSystems.getDefault().getPath(fichierChemin));
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public List<String> getListeHOF() {
		return this.lignes;
	}
}
