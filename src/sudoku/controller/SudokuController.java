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

import javax.swing.*;

public class SudokuController extends AbstractController {

    private GrilleView view;
    private GenerateurGrilleSolution generateurGrilleSolution;
    private GenerateurGrilleJoueur generateurGrilleJoueur;
    private int[][] grilleSolution;
    private int[][] grilleJoueur;
    private String niveauDifficulte;
    private int[][] grilleJoueurInitiale = new int[9][9];

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
        this.grilleSolution = this.generateurGrilleSolution.getGrilleSolution();
        this.generateurGrilleJoueur = new GenerateurGrilleJoueur(grilleSolution, niveauDifficulte);
        this.grilleJoueur = generateurGrilleJoueur.getGrilleJoueur();
        for (int x = 0; x < 9; x++) {
            grilleJoueurInitiale[x] = Arrays.copyOf(grilleJoueur[x], grilleJoueur[x].length);
        }
        System.out.println("grille initiale : " +Arrays.deepToString(grilleJoueurInitiale));

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
            setNiveauDifficulte((String) grilles.get(2));
            System.out.println(Arrays.deepToString(grilleSolution));
            System.out.println(Arrays.deepToString(grilleJoueur));
            System.out.println(Arrays.deepToString(grilleJoueurInitiale));

            //niveau
            System.out.println(grilles.get(2));
            this.view = new GrilleView(this);
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
}
