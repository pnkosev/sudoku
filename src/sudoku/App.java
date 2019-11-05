package sudoku;

import sudoku.controller.HelpController;
import sudoku.controller.SudokuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        main.setSize(600, 600);
        //main.pack();
        main.setLocationRelativeTo(null);
        sudokuCtl = new SudokuController(main);
        if(!sudokuCtl.isThereASave()) {
        	sudokuCtl.newGrille(niveauDifficulteString);
        }else {
        	sudokuCtl.lireSauvegarde();        	
        	System.out.println("lecture de la sauvegarde");
        }
        main.setIconImage(new ImageIcon("icon.png").getImage());
       
        main.setVisible(true);
        
//        main.addWindowListener(new WindowAdapter() {
//    		public void windowClosing(WindowEvent e) {
//    			String msg = "Voulez-vous sauvegarder la partie avant de quitter?";
//    			int reponse = JOptionPane.showConfirmDialog(main,
//    	        		msg,
//    	                "Confirmation",
//    	                JOptionPane.YES_NO_OPTION,
//    	                JOptionPane.QUESTION_MESSAGE);
//    	        
//    	        if(reponse == JOptionPane.YES_OPTION ){
//    				
//    				System.out.println("reponse = "+reponse);
//    			}
//    		}
//    	});
    }

    public JMenuBar createMenuBar() {
        JMenu menu;
        JMenuItem menuItem;
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
        menuItem =new JMenuItem("Débutant", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> {
        	niveauDifficulteString = "debutant";
        	sudokuCtl.newGrille(niveauDifficulteString);
        });
        menu.add(menuItem);
        menuItem =new JMenuItem("Intermédiaire", KeyEvent.VK_I);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> {
        	niveauDifficulteString ="intermediaire";
        	sudokuCtl.newGrille(niveauDifficulteString);
        
        });
                
        menu.add(menuItem);
        menuItem =new JMenuItem("Expert", KeyEvent.VK_E);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(event -> {
        	niveauDifficulteString="expert";
        	sudokuCtl.newGrille(niveauDifficulteString);	
        });
        menu.add(menuItem);
        
        // add to menu bar
        menuBar.add(menu);

        // 2nd menu and items
        HelpController ctl = new HelpController(main);
        menu = new JMenu("Aide");
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
        // Use System Look and Feel

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            new App();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
