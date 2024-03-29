package sudoku;

import sudoku.controller.HelpController;
import sudoku.controller.SudokuController;
import sudoku.service.HallOfFame;
import sudoku.view.HallOfFameView;

import javax.swing.*;

//import org.graalvm.compiler.lir.StandardOp.ImplicitNullCheck;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Main class of program.
 */
public class App {

    private JFrame main;
    private SudokuController sudokuCtl;
    private String niveauDifficulteString ="intermediaire";

    public App() {
        main = new JFrame("Sudoku");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = (JPanel) main.getContentPane();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        main.setJMenuBar(createMenuBar());
        main.setSize(700, 600);
        //main.pack();
        main.setLocationRelativeTo(null);

        sudokuCtl = new SudokuController(main);
        
        if(!sudokuCtl.isThereASave()) {
        	sudokuCtl.newGrille(niveauDifficulteString);
        	sudokuCtl.setIsGameSaved(false);
        }else {
        	sudokuCtl.lireSauvegarde();
        	niveauDifficulteString = sudokuCtl.getNiveauDifficulte();
        	sudokuCtl.setIsGameSaved(true);
        }
        
        main.setJMenuBar(createMenuBar());
        main.setIconImage(new ImageIcon("icon.png").getImage());

        main.setVisible(true);
    }

    public JMenuBar createMenuBar() {
        JMenu menu;
        JMenuItem menuItem;
        JRadioButtonMenuItem menuRadioItem;
        // create menu bar
        JMenuBar menuBar = new JMenuBar();
        // 1st menu & items
        menu = new JMenu("Jeu");
        menuItem = new JMenuItem("Nouveau", KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> sudokuCtl.newGrille(niveauDifficulteString));
        menu.add(menuItem);

        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> sudokuCtl.save(niveauDifficulteString) );
        menu.add(menuItem);
        
        // add to menu bar
        menuBar.add(menu);
        
        // create niveau
        menu = new JMenu("Niveau");
        // ButtonGroup permet de gérer  la sélection d'un seul bouton radio
        ButtonGroup radioGroup =  new ButtonGroup();

        menuRadioItem = new JRadioButtonMenuItem("Débutant");
        menuRadioItem.setMnemonic(KeyEvent.VK_D);
        menuRadioItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_DOWN_MASK));

        menuRadioItem.addActionListener(event -> {
        	niveauDifficulteString = "debutant";
        	sudokuCtl.newGrille(niveauDifficulteString);
        });
        if(niveauDifficulteString.equals("debutant")) {
            menuRadioItem.setSelected(true);
        }
        radioGroup.add(menuRadioItem);
        menu.add(menuRadioItem);

        menuRadioItem =new JRadioButtonMenuItem("Intermédiaire");
        menuRadioItem.setMnemonic( KeyEvent.VK_I);
        menuRadioItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_DOWN_MASK));
        menuRadioItem.addActionListener(event -> {
        	niveauDifficulteString ="intermediaire";
        	sudokuCtl.newGrille(niveauDifficulteString);
        
        });

        if(niveauDifficulteString.equals("intermediaire")) {
            menuRadioItem.setSelected(true);
        }
        radioGroup.add(menuRadioItem);
        menu.add(menuRadioItem);

        menuRadioItem =new JRadioButtonMenuItem("Expert");
        menuRadioItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK));
        menuRadioItem.addActionListener(event -> {
        	niveauDifficulteString="expert";
        	sudokuCtl.newGrille(niveauDifficulteString);	
        });

        if(niveauDifficulteString.equals("expert")) {
            menuRadioItem.setSelected(true);
        }

        radioGroup.add(menuRadioItem);
        menu.add(menuRadioItem);
        
        // add to menu bar
        menuBar.add(menu);

        // 2nd menu and items
        HelpController ctl = new HelpController(main);
        menu = new JMenu("Aide");
        menuItem = new JMenuItem("Table des Scores", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> {
        	HallOfFame hOfFame = new HallOfFame();
        	new HallOfFameView(hOfFame.getListeHOF());
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Régles", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> ctl.rules());
        menu.add(menuItem);
        menuItem = new JMenuItem("A propos", KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> ctl.about());
        menu.add(menuItem);
        menuBar.add(menu);

        return menuBar;

    }

    /**
     * Main entry point of program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.invokeLater(App::new);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
